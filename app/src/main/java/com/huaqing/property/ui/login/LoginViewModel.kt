package com.huaqing.property.ui.login

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import arrow.core.Either
import arrow.core.Option
import arrow.core.none
import arrow.core.some
import com.huaqing.property.base.viewmodel.BaseViewModel
import com.huaqing.property.base.viewstate.ViewState
import com.huaqing.property.common.helper.RxSchedulers
import com.huaqing.property.common.viewmodel.loadings.CommonLoadingState
import com.huaqing.property.db.UserInfo
import com.huaqing.property.ext.arrow.whenNotNull
import com.huaqing.property.ext.livedata.map
import com.huaqing.property.ext.livedata.toReactiveStream
import com.huaqing.property.http.globalHandleError
import com.huaqing.property.model.Errors
import com.huaqing.property.utils.logger.log
import com.huaqing.property.utils.toast
import com.uber.autodispose.autoDisposable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import retrofit2.HttpException

class LoginViewModel(
    private val repo: LoginDataSourceRepository
) : BaseViewModel() {

    val username: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()

    val loadingLayout: MutableLiveData<CommonLoadingState> = MutableLiveData()
    val error: MutableLiveData<Option<Throwable>> = MutableLiveData()

    val userInfo: MutableLiveData<UserInfo> = MutableLiveData()

    val requestMyInfo: MutableLiveData<Boolean> = MutableLiveData()

    private val autoLogin: MutableLiveData<Boolean> = MutableLiveData()

    init {
        autoLogin
            .toReactiveStream()
            .filter { it }
            .doOnNext {
                login()
            }
            .autoDisposable(this)
            .subscribe()

        requestMyInfo
            .toReactiveStream()
            .filter { it }
            .doOnNext {
                if (it) {
                    this.requestMyInfo.postValue(!it)
                    myInfo()
                }
            }
            .autoDisposable(this)
            .subscribe()

        error
            .map {
                log { "error " + it }
                it
            }
            .toReactiveStream()
            .map { errorOpt ->
                errorOpt.flatMap {
                    when (it) {
                        is Errors.EmptyInputError -> "username or password can't be null.".some()
                        is HttpException ->
                            when (it.code()) {
                                401 -> "username or password error.".some()
                                else -> "network error".some()
                            }
                        else -> none()
                    }
                }
            }
            .autoDisposable(this)
            .subscribe { errorMsg ->
                errorMsg.whenNotNull {
                    toast {
                        it
                    }
                }
            }

        initAutoLogin()
    }

    private fun initAutoLogin() =
        Single.zip(
            repo.findDefaultUserInfo(),
            repo.prefsAutoLogin(),
            BiFunction { either: Either<Errors, UserInfo>, autoLogin: Boolean ->
                autoLogin to either
            })
            .observeOn(RxSchedulers.ui)
            .autoDisposable(this)
            .subscribe({ pair ->
                pair.second.fold({
                    applyStateLogin(error = it.some())
                }, {
                    applyStateLogin(
                        username = it.username.some() as Option<String>,
                        password = it.password.some() as Option<String>,
                        autoLogin = pair.first
                    )
                })
            }, {
                applyStateLogin(error = it.some())
            })

    fun login() {
        when (username.value.isNullOrEmpty() || password.value.isNullOrEmpty()) {
            true ->
                toast { "请输入信息" }
            false -> repo
                .login(username.value!!, password.value!!)
                .compose(globalHandleError())
                .map { either ->
                    either.fold({
                        ViewState.error<UserInfo>(it)
                    }, {
                        ViewState.result(it)
                    })
                }
                .startWith(ViewState.loading())
                .startWith(ViewState.idle())
                .onErrorReturn {
                    ViewState.error(it)
                }
                .observeOn(RxSchedulers.ui)
                .autoDisposable(this)
                .subscribe { state ->
                    when (state) {
                        is ViewState.Refreshing -> applyStateLogin(loadingLayout = CommonLoadingState.LOADING)
                        is ViewState.Idle -> applyStateLogin()
                        is ViewState.Error -> {
                            when (state.error) {
                                is Errors.Error ->
                                    toast {
                                        state.error.errorMsg
                                    }
                            }
                            applyStateLogin(
                                loadingLayout = CommonLoadingState.ERROR,
                                error = state.error.some()
                            )
                        }
                        is ViewState.Result -> applyStateLogin(requestMyInfo = true)
                    }
                }
        }

    }

    private fun applyStateLogin(
        loadingLayout: CommonLoadingState = CommonLoadingState.IDLE,
        error: Option<Throwable> = none(),
        username: Option<String> = none(),
        password: Option<String> = none(),
        autoLogin: Boolean = false,
        requestMyInfo: Boolean = false
    ) {
        this.loadingLayout.postValue(loadingLayout)
        this.error.postValue(error)

        username.whenNotNull {
            this.username.value = it
        }
        password.whenNotNull {
            this.password.value = it
        }

        this.autoLogin.postValue(autoLogin)

        this.requestMyInfo.postValue(requestMyInfo)
    }

    fun myInfo() {
        repo.myInfo(username.value!!, password.value!!)
            .compose(globalHandleError())
            .map { either ->
                either.fold({
                    ViewState.error<UserInfo>(it)
                }, {
                    ViewState.result(it)
                })
            }
            .startWith(ViewState.loading())
            .startWith(ViewState.idle())
            .onErrorReturn {
                ViewState.error(it)
            }
            .observeOn(RxSchedulers.ui)
            .autoDisposable(this)
            .subscribe { state ->
                when (state) {
                    is ViewState.Refreshing -> applyStateMyInfo(loadingLayout = CommonLoadingState.LOADING)
                    is ViewState.Idle -> applyStateMyInfo()
                    is ViewState.Error -> {
                        when (state.error) {
                            is Errors.Error ->
                                toast { state.error.errorMsg }
                        }
                        applyStateMyInfo(
                            loadingLayout = CommonLoadingState.ERROR,
                            error = state.error.some()
                        )
                    }
                    is ViewState.Result -> applyStateMyInfo(user = state.result.some())
                }
            }
    }


    private fun applyStateMyInfo(
        loadingLayout: CommonLoadingState = CommonLoadingState.IDLE,
        user: Option<UserInfo> = none(),
        error: Option<Throwable> = none()
    ) {
        this.loadingLayout.postValue(loadingLayout)
        this.error.postValue(error)

        this.userInfo.postValue(user.orNull())
    }

    companion object {
        fun instance(fragment: Fragment, repo: LoginDataSourceRepository): LoginViewModel =
            ViewModelProviders
                .of(fragment, LoginViewModelFactory(repo))
                .get(LoginViewModel::class.java)
    }

}


class LoginViewModelFactory(private val repo: LoginDataSourceRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        LoginViewModel(repo) as T

}