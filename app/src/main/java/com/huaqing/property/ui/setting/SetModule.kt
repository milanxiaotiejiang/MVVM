package com.huaqing.property.ui.setting

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.huaqing.property.common.viewmodel.toolbar.ToolbarViewModel
import org.kodein.di.Kodein
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

const val SET_MODULE_TAG = "SET_MODULE_TAG"

val setKodeinModule = Kodein.Module(SET_MODULE_TAG) {

    bind<SetViewModel>() with scoped<FragmentActivity>(AndroidLifecycleScope).singleton {
        SetViewModel.instance(
            activity = context,
            repo = instance()
        )
    }

    bind<ToolbarViewModel>() with scoped<FragmentActivity>(AndroidLifecycleScope).singleton {
        ToolbarViewModel.instance()
    }

    bind<SetLocalDataSource>() with scoped<FragmentActivity>(AndroidLifecycleScope).singleton {
        SetLocalDataSource(instance(), instance())
    }

    bind<SetDataSourceRepository>() with scoped<FragmentActivity>(AndroidLifecycleScope).singleton {
        SetDataSourceRepository(instance())
    }

}