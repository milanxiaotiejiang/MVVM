package com.huaqing.property.ui.login

import com.huaqing.property.base.viewdelegate.BaseLoadingViewDelegate
import com.huaqing.property.common.loadings.CommonLoadingViewModel
import com.huaqing.property.ext.lifecycle.bindLifecycle
import com.huaqing.property.ext.livedata.toFlowable
import com.huaqing.property.utils.logger.log

class LoginViewDelegate(
    val viewModel: LoginViewModel,
    private val navigator: LoginNavigator,
    loadingViewModel: CommonLoadingViewModel
) : BaseLoadingViewDelegate(loadingViewModel) {


    init {

        viewModel.userInfo
            .toFlowable()
            .doOnNext {
                navigator.toMain()
            }
            .bindLifecycle(viewModel)
            .subscribe()

        viewModel.loadingLayout
            .toFlowable()
            .doOnNext {
                applyState(it)
            }
            .bindLifecycle(viewModel)
            .subscribe()
    }


    fun login() = viewModel.login()
}
