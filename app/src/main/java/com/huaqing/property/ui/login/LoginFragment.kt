package com.huaqing.property.ui.login

import com.huaqing.property.base.ui.BaseFragment
import org.kodein.di.Kodein
import org.kodein.di.generic.instance
import com.huaqing.property.R
import com.huaqing.property.common.loadings.CommonLoadingViewModel
import com.huaqing.property.databinding.FragmentLoginBinding
import com.huaqing.property.ext.lifecycle.bindLifecycle
import com.huaqing.property.ext.livedata.toFlowable
import com.huaqing.property.ui.MainActivity

class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    override val kodein: Kodein
        get() = Kodein.lazy {
            extend(parentKodein)
            import(loginKodeinModule)
        }

    val viewModel: LoginViewModel by instance()

    val loadingViewModel: CommonLoadingViewModel by instance()

    override val layoutId = R.layout.fragment_login

    override fun initView() {

        viewModel.userInfo
            .toFlowable()
            .doOnNext {
                toMain()
            }
            .bindLifecycle(viewModel)
            .subscribe()

        viewModel.loadingLayout
            .toFlowable()
            .doOnNext {
                loadingViewModel.applyState(it)
            }
            .bindLifecycle(viewModel)
            .subscribe()
    }

    fun login() = viewModel.login()

    fun toMain() = MainActivity.launch(activity!!)
}