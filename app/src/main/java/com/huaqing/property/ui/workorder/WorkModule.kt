package com.huaqing.property.ui.workorder

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

const val WORK_MODULE_TAG = "WORK_MODULE_TAG"

val workKodeinModule = Kodein.Module(WORK_MODULE_TAG) {

    bind<WorkViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        WorkViewModel.instance(
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

    bind<WorkRemoteDataSource>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        WorkRemoteDataSource(instance())
    }

    bind<WorkDataSourceRepository>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        WorkDataSourceRepository(instance())
    }

}