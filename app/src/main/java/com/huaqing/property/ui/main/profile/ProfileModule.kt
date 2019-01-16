package com.huaqing.property.ui.main.profile

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

const val PROFILE_MODULE_TAG = "PROFILE_MODULE_TAG"

val profilekodeinmodule = Kodein.Module(PROFILE_MODULE_TAG) {

    bind<ProfileViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        ProfileViewModel.instance(
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

    bind<ProfileRemoteDataSource>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        ProfileRemoteDataSource(instance())
    }

    bind<ProfileDataSourceRepository>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        ProfileDataSourceRepository(instance())
    }

}