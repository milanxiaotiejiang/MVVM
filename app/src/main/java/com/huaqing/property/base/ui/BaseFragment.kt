package com.huaqing.property.base.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.huaqing.property.base.viewdelegate.IViewDelegate

abstract class BaseFragment<B : ViewDataBinding, VD : IViewDelegate> : BaseInjectFragment() {

    private lateinit var mRootView: View

    protected lateinit var mBinding: B

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mRootView = LayoutInflater.from(context).inflate(layoutId, container, false)
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = DataBindingUtil.bind(view)!!
        with(mBinding) {
            setLifecycleOwner(this@BaseFragment)
        }
        lifecycle.addObserver(viewDelegate)
        initView()
    }

    abstract val layoutId: Int
    abstract val viewDelegate: VD
    abstract fun initView()
}
