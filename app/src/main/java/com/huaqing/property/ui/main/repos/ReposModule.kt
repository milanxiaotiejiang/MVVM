package com.huaqing.property.ui.main.repos

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


const val REPOS_MODULE_TAG = "REPOS_MODULE_TAG"

val reposKodeinModule = Kodein.Module(REPOS_MODULE_TAG) {

    bind<ReposViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        ReposViewModel.instance(
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

    bind<ReposRemoteDataSource>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        ReposRemoteDataSource(instance())
    }

    bind<ReposDataSourceRepository>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        ReposDataSourceRepository(instance())
    }

}