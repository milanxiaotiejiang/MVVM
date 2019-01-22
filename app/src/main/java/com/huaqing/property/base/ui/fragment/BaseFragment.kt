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

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        if (isVisibleToUser && isVisible) {
            //果这个Fragment的View视图没有被销毁的时候，只有当数据的时效性超过30min后再次切换到这个Fragment才会进行数据的加载。
            if (refreshTime == 0L || refreshTime != 0L && System.currentTimeMillis() - refreshTime > 30 * 60 * 1000) {
                initView()
            }
        }
        super.setUserVisibleHint(isVisibleToUser)
    }

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
        if (getUserVisibleHint()) {
            initView()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mRootView = null
        mBinding.unbind()
    }

    abstract val layoutId: Int
    abstract fun initView()
}
