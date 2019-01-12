package com.huaqing.property.ui.main.home

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.huaqing.property.ext.viewmodel.addLifecycle
import org.kodein.di.Kodein
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton


const val HOME_MODULE_TAG = "HOME_MODULE_TAG"

val homeKodeinModule = Kodein.Module(HOME_MODULE_TAG) {

    bind<HomeNavigator>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        HomeNavigator()
    }

    bind<HomeViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        ViewModelProviders.of(context.activity!!, HomeViewModelFactory(instance()))
            .get(HomeViewModel::class.java)
            .apply {
                addLifecycle(context)
            }
    }

    bind<HomeViewDelegate>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        HomeViewDelegate(instance(), instance())
    }

    bind<HomeRemoteDataSource>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        HomeRemoteDataSource(instance())
    }

    bind<HomeDataSourceRepository>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        HomeDataSourceRepository(instance())
    }

}