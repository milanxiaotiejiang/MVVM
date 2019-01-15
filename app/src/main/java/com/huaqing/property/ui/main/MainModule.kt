package com.huaqing.property.ui.main

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.huaqing.property.ext.viewmodel.addLifecycle
import com.huaqing.property.ui.main.home.HomeFragment
import com.huaqing.property.ui.main.profile.ProfileFragment
import com.huaqing.property.ui.main.repos.ReposFragment
import kotlinx.android.synthetic.main.fragment_main.*
import org.kodein.di.Kodein
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton


const val MAIN_MODULE_TAG = "MAIN_MODULE_TAG"

const val MAIN_LIST_FRAGMENT = "MAIN_LIST_FRAGMENT"

val mainKodeinModule = Kodein.Module(MAIN_MODULE_TAG) {

    bind<HomeFragment>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        HomeFragment()
    }

    bind<ReposFragment>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        ReposFragment()
    }

    bind<ProfileFragment>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        ProfileFragment()
    }

    bind<MainViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        ViewModelProviders.of(context, MainViewModelFactory())
            .get(MainViewModel::class.java)
            .apply {
                addLifecycle(context)
            }
    }

    bind<BottomNavigationView>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        (context as MainFragment).navigation
    }

    bind<androidx.viewpager.widget.ViewPager>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        (context as MainFragment).viewPager
    }

    bind<List<Fragment>>(MAIN_LIST_FRAGMENT) with scoped<Fragment>(AndroidLifecycleScope).singleton {
        listOf<Fragment>(instance<HomeFragment>(), instance<ReposFragment>(), instance<ProfileFragment>())
    }
}