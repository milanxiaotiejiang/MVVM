package com.huaqing.property.ui.inform

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.huaqing.property.common.viewmodel.toolbar.ToolbarViewModel
import org.kodein.di.Kodein
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

const val INFORM_DETAIL_MODULE_TAG = "inform_detail_module_tag"

val informDetailKodeinModule = Kodein.Module(INFORM_DETAIL_MODULE_TAG) {

    bind<InformDetailViewModel>() with scoped<AppCompatActivity>(AndroidLifecycleScope).singleton {
        InformDetailViewModel.instance(context)
    }
    bind<ToolbarViewModel>() with scoped<AppCompatActivity>(AndroidLifecycleScope).singleton {
        ToolbarViewModel.instance()
    }
}