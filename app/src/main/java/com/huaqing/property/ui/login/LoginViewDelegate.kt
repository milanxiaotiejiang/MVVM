package com.huaqing.property.ui.login

import android.view.View
import androidx.lifecycle.LifecycleOwner
import com.huaqing.property.base.viewdelegate.BaseLoadingViewDelegate
import com.huaqing.property.common.loadings.CommonLoadingViewModel
import com.huaqing.property.ext.autodispose.bindLifecycle
import com.huaqing.property.ext.lifecycle.bindLifecycle
import com.huaqing.property.ext.livedata.toFlowable
import com.huaqing.property.utils.toast
import io.reactivex.internal.operators.single.SingleInternalHelper.toFlowable

class LoginViewDelegate(
    val viewModel: LoginViewModel,
    private val navigator: LoginNavigator,
    loadingViewModel: CommonLoadingViewModel
) : BaseLoadingViewDelegate(loadingViewModel) {

    override fun onCreate(lifecycleOwner: LifecycleOwner) {
        super.onCreate(lifecycleOwner)
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

    fun login(v: View) = viewModel.login()
}
