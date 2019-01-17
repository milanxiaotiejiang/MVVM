package com.huaqing.property.ui.login

import com.huaqing.property.R
import com.huaqing.property.base.ui.fragment.BaseFragment
import com.huaqing.property.common.viewmodel.loadings.CommonLoadingViewModel
import com.huaqing.property.databinding.FragmentLoginBinding
import com.huaqing.property.ext.livedata.toReactiveStream
import com.huaqing.property.ui.MainActivity
import com.uber.autodispose.AutoDispose.autoDisposable
import com.uber.autodispose.autoDisposable
import io.reactivex.Completable
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

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
        Completable
            .mergeArray(
                viewModel.userInfo
                    .toReactiveStream()
                    .doOnNext {
                        toMain()
                    }
                    .ignoreElements(),

                viewModel.loadingLayout
                    .toReactiveStream()
                    .doOnNext {
                        loadingViewModel.applyState(it)
                    }
                    .ignoreElements()
            )
            .autoDisposable(viewModel)
            .subscribe()
    }

    fun login() = viewModel.login()

    fun toMain() = MainActivity.launch(activity!!)
}