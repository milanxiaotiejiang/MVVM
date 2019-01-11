package com.huaqing.property.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import arrow.core.Option
import arrow.core.none
import arrow.core.some
import com.huaqing.property.base.viewmodel.BaseViewModel
import com.huaqing.property.base.viewstate.ViewState
import com.huaqing.property.common.helper.RxSchedulers
import com.huaqing.property.common.loadings.CommonLoadingState
import com.huaqing.property.db.UserInfo
import com.huaqing.property.ext.arrow.whenNotNull
import com.huaqing.property.ext.lifecycle.bindLifecycle
import com.huaqing.property.http.globalHandleError
import com.huaqing.property.model.Errors
import com.huaqing.property.utils.toast

class LoginViewModel(private val repo: LoginDataSourceRepository) : BaseViewModel() {

    val telephone: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()

    val loadingLayout: MutableLiveData<CommonLoadingState> = MutableLiveData()
    val error: MutableLiveData<Option<Throwable>> = MutableLiveData()

    val userInfo: MutableLiveData<UserInfo> = MutableLiveData()

    private val autoLogin: MutableLiveData<Boolean> = MutableLiveData()

    fun login() {
        when (telephone.value.isNullOrEmpty() || password.value.isNullOrEmpty()) {
            true -> toast { "请输入信息" }
            false -> repo
                .login(telephone.value!!, password.value!!)
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
                        is ViewState.Result -> applyState(user = state.result.some())
                    }
                }
        }

    }

    private fun applyState(
        loadingLayout: CommonLoadingState = CommonLoadingState.IDLE,
        user: Option<UserInfo> = none(),
        error: Option<Throwable> = none(),
        telephone: Option<String> = none(),
        password: Option<String> = none(),
        autoLogin: Boolean = false
    ) {
        this.loadingLayout.postValue(loadingLayout)
        this.error.postValue(error)

        this.userInfo.postValue(user.orNull())

        telephone.whenNotNull {
            this.telephone.value = it
        }
        password.whenNotNull {
            this.password.value = it
        }

        this.autoLogin.postValue(autoLogin)
    }

}


class LoginViewModelFactory(private val repo: LoginDataSourceRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        LoginViewModel(repo) as T

}