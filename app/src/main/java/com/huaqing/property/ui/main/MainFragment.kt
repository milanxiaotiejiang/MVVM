package com.huaqing.property.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.huaqing.property.R
import com.huaqing.property.base.ui.BaseFragment
import com.huaqing.property.databinding.FragmentMainBinding
import org.kodein.di.Kodein
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

class MainFragment : BaseFragment<FragmentMainBinding, MainViewDelegate>() {

    override val kodein = Kodein.lazy {
        extend(parentKodein)
        import(mainKodeinModule)

        bind<FragmentManager>() with instance(childFragmentManager)
        bind<BottomNavigationView>() with instance(mBinding.navigation)
        bind<ViewPager>() with instance(mBinding.viewPager)
    }

    override val layoutId: Int = R.layout.fragment_main

    override val viewDelegate: MainViewDelegate by instance()

    override fun initView() {
        mBinding.delegate = viewDelegate
    }
}