package com.huaqing.property.base.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.huaqing.property.BR

abstract class BaseFragment<B : ViewDataBinding> : BaseInjectFragment() {

    private var mRootView: View? = null

    protected lateinit var mBinding: B


    protected var refreshTime: Long = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mRootView = LayoutInflater.from(context).inflate(layoutId, container, false)
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding = DataBindingUtil.bind(view)!!
        with(mBinding) {
            setVariable(BR.fragment, this@BaseFragment)
            setLifecycleOwner(this@BaseFragment)
        }
        initView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mRootView = null
        mBinding.unbind()
    }

    abstract val layoutId: Int
    abstract fun initView()
}
