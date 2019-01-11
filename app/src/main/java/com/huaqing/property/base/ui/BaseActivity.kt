package com.huaqing.property.base.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.huaqing.property.base.viewdelegate.IViewDelegate

abstract class BaseActivity<B : ViewDataBinding, VD : IViewDelegate> : BaseInjectActivity() {

    protected lateinit var mBinding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, layoutId)
        with(mBinding) {
            setLifecycleOwner(this@BaseActivity)
        }
        lifecycle.addObserver(viewDelegate)
        initView()
    }

    abstract val layoutId: Int
    abstract val viewDelegate: VD
    abstract fun initView()
}