package com.huaqing.property.ui.login

import com.huaqing.property.R
import com.huaqing.property.base.ui.BaseActivity
import com.huaqing.property.databinding.ActivityLoginBinding
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewDelegate>() {

    override val kodein: Kodein = Kodein.lazy {
        extend(parentKodein)
        import(loginKodeinModule)
    }

    override val layoutId: Int
        get() = R.layout.activity_login

    override val viewDelegate: LoginViewDelegate by instance()

    override fun initView() {
        mBinding.delegate = viewDelegate
    }


}