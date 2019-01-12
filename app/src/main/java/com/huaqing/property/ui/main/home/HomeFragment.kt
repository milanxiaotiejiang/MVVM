package com.huaqing.property.ui.main.home

import com.huaqing.property.R
import com.huaqing.property.base.ui.BaseFragment
import com.huaqing.property.databinding.FragmentHomeBinding
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewDelegate>() {

    override val kodein = Kodein.lazy {
        extend(parentKodein)
        import(homeKodeinModule)
    }

    override val layoutId: Int = R.layout.fragment_home

    override val viewDelegate: HomeViewDelegate by instance()

    override fun initView() {
        mBinding.delegate = viewDelegate
    }
}