package com.huaqing.property.common.viewmodel.toolbar

import androidx.lifecycle.MutableLiveData
import com.huaqing.property.base.viewmodel.BaseViewModel

class ToolbarViewModel : BaseViewModel(), IToolbarDelegate {
    val toolbarName: MutableLiveData<String> = MutableLiveData()

    companion object {
        fun instance() = ToolbarViewModel()
    }
}