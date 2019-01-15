package com.huaqing.property.base.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.huaqing.property.BR

abstract class BaseActivity<B : ViewDataBinding> : BaseInjectActivity() {

    protected lateinit var mBinding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, layoutId)
        with(mBinding) {
            setVariable(BR.activity, this@BaseActivity)
            setLifecycleOwner(this@BaseActivity)
        }
        initView()
    }


    override fun onDestroy() {
        super.onDestroy()
        mBinding.unbind()
    }

    abstract val layoutId: Int
    abstract fun initView()
}