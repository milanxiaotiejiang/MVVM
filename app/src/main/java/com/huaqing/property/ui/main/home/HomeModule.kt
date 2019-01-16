package com.huaqing.property.ui.main.home

import androidx.fragment.app.Fragment
import com.huaqing.property.common.viewmodel.fabanimate.FabAnimateViewModel
import com.huaqing.property.common.viewmodel.loadings.CommonLoadingViewModel
import com.huaqing.property.common.viewmodel.toolbar.ToolbarViewModel
import org.kodein.di.Kodein
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

const val HOME_MODULE_TAG = "HOME_MODULE_TAG"

val homeKodeinModule = Kodein.Module(HOME_MODULE_TAG) {

    bind<HomeViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        HomeViewModel.instance(
            fragment = context,
            repo = instance()
        )
    }

    bind<FabAnimateViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        FabAnimateViewModel.instance()
    }

    bind<CommonLoadingViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        CommonLoadingViewModel.instance()
    }

    bind<ToolbarViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        ToolbarViewModel.instance()
    }

    bind<HomeRemoteDataSource>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        HomeRemoteDataSource(instance())
    }

    bind<HomeDataSourceRepository>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        HomeDataSourceRepository(instance())
    }

}