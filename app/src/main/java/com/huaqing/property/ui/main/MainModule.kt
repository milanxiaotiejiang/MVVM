package com.huaqing.property.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.huaqing.property.ext.viewmodel.addLifecycle
import com.huaqing.property.ui.main.home.HomeFragment
import com.huaqing.property.ui.main.profile.ProfileFragment
import com.huaqing.property.ui.main.repos.ReposFragment
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

    bind<MainNavigator>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        MainNavigator()
    }

    bind<MainViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        ViewModelProviders.of(context, MainViewModelFactory())
            .get(MainViewModel::class.java)
            .apply {
                addLifecycle(context)
            }
    }

//    bind<FragmentManager>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
//        (context as MainFragment).childFragmentManager
//    }
//
//    bind<BottomNavigationView>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
//        (context as MainFragment).navigation
//    }
//
//    bind<ViewPager>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
//        (context as MainFragment).viewPager
//    }

    bind<List<Fragment>>(MAIN_LIST_FRAGMENT) with scoped<Fragment>(AndroidLifecycleScope).singleton {
        listOf<Fragment>(instance<HomeFragment>(), instance<ReposFragment>(), instance<ProfileFragment>())
    }

    bind<MainViewDelegate>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        MainViewDelegate(instance(), instance(), instance(MAIN_LIST_FRAGMENT), instance(), instance(), instance())
    }
}