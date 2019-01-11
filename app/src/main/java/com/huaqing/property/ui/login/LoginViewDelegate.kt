package com.huaqing.property.ui.login

import android.view.View
import com.huaqing.property.base.viewdelegate.BaseLoadingViewDelegate
import com.huaqing.property.common.loadings.CommonLoadingViewModel
import com.huaqing.property.ext.lifecycle.bindLifecycle
import com.huaqing.property.ext.livedata.toFlowable
import com.huaqing.property.utils.toast

class LoginViewDelegate(
    val viewModel: LoginViewModel,
    loadingViewModel: CommonLoadingViewModel
) : BaseLoadingViewDelegate(loadingViewModel) {

    init {
//        viewModel.userInfo
//            .toFlowable()
//            .doOnNext {
//                toast { "跳转" }
//            }
//            .bindLifecycle(viewModel)
//            .subscribe()
//
//        viewModel.loadingLayout
//            .toFlowable()
//            .doOnNext {
//                applyState(it)
//            }
//            .bindLifecycle(loadingViewModel)
//            .subscribe()
    }

    fun login(v: View) = viewModel.login()
}
