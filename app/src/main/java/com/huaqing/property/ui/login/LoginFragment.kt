package com.huaqing.property.ui.login

import com.huaqing.property.base.ui.BaseFragment
import org.kodein.di.Kodein
import org.kodein.di.generic.instance
import com.huaqing.property.R
import com.huaqing.property.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewDelegate>() {

    override val kodein: Kodein
        get() = Kodein.lazy {
            extend(parentKodein)
            import(loginKodeinModule)
        }

    override val layoutId = R.layout.fragment_login

    override val viewDelegate: LoginViewDelegate by instance()

    override fun initView() {
        mBinding.delegate = viewDelegate
    }

}