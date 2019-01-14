package com.huaqing.property.ui.login

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import arrow.core.Either
import arrow.core.Option
import arrow.core.none
import arrow.core.some
import com.huaqing.property.R.id.telephone
import com.huaqing.property.base.viewmodel.BaseViewModel
import com.huaqing.property.base.viewstate.ViewState
import com.huaqing.property.common.helper.RxSchedulers
import com.huaqing.property.common.loadings.CommonLoadingState
import com.huaqing.property.db.UserInfo
import com.huaqing.property.ext.arrow.whenNotNull
import com.huaqing.property.ext.lifecycle.bindLifecycle
import com.huaqing.property.ext.livedata.toFlowable
import com.huaqing.property.http.globalHandleError
import com.huaqing.property.model.Errors
import com.huaqing.property.model.MyInfoBean
import com.huaqing.property.utils.logger.log
import com.huaqing.property.utils.toast
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

    override fun onCreate(lifecycleOwner: LifecycleOwner) {
        super.onCreate(lifecycleOwner)

        autoLogin.toFlowable()
            .filter { it }
            .doOnNext {
                login()
            }
            .bindLifecycle(this)
            .subscribe()

        requestMyInfo.toFlowable()
            .filter { it }
            .doOnNext {
                if (it) {
                    myInfo()
                }
            }
            .bindLifecycle(this)
            .subscribe()

        error.toFlowable()
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
            .bindLifecycle(this)
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
            repo.prefsUser().firstOrError(),
            repo.prefsAutoLogin(),
            BiFunction { either: Either<Errors, String>, autoLogin: Boolean ->
                autoLogin to either
            })
            .bindLifecycle(this)
            .subscribe { pair ->
                pair.second.fold({ error ->
                    applyState(error = error.some())
                }, { entity ->
                    applyState(
                        username = entity.username.some() as Option<String>,
                        password = entity.password.some() as Option<String>,
                        autoLogin = pair.first
                    )
                })
            }

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
                .bindLifecycle(this)
                .subscribe { state ->
                    when (state) {
                        is ViewState.Refreshing -> applyState(loadingLayout = CommonLoadingState.LOADING)
                        is ViewState.Idle -> applyState()
                        is ViewState.Error -> {
                            when (state.error) {
                                is Errors.Error ->
                                    toast { state.error.errorMsg }
                            }
                            applyState(
                                loadingLayout = CommonLoadingState.ERROR,
                                error = state.error.some()
                            )
                        }
                        is ViewState.Result -> applyState(requestMyInfo = true)
                    }
                }
        }

    }

    private fun applyState(
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
//        repo.myInfo(this.username.value)
//            .compose(globalHandleError())
//            .map { either ->
//                either.fold({
//                    ViewState.error<MyInfoBean>(it)
//                }, {
//                    ViewState.result(it)
//                })
//            }
//            .startWith(ViewState.loading())
//            .startWith(ViewState.idle())
//            .onErrorReturn {
//                ViewState.error(it)
//            }
//            .observeOn(RxSchedulers.ui)
//            .bindLifecycle(this)
//            .subscribe { state ->
//                when (state) {
//                    is ViewState.Refreshing -> applyState(loadingLayout = CommonLoadingState.LOADING)
//                    is ViewState.Idle -> applyState()
//                    is ViewState.Error -> {
//                        when (state.error) {
//                            is Errors.Error ->
//                                toast { state.error.errorMsg }
//                        }
//                        applyState(
//                            loadingLayout = CommonLoadingState.ERROR,
//                            error = state.error.some()
//                        )
//                    }
//                    is ViewState.Result -> applyState(myInfo = state.result.some(), requestMyInfo = true)
//                }
//            }
    }


    private fun applyState(
        loadingLayout: CommonLoadingState = CommonLoadingState.IDLE,
        user: Option<UserInfo> = none(),
        error: Option<Throwable> = none(),
        requestMyInfo: Boolean = false
    ) {
        this.loadingLayout.postValue(loadingLayout)
        this.error.postValue(error)

        this.userInfo.postValue(user.orNull())

        this.requestMyInfo.postValue(requestMyInfo)
    }


}


class LoginViewModelFactory(private val repo: LoginDataSourceRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        LoginViewModel(repo) as T

}