package com.huaqing.property.common.loadings

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.huaqing.property.base.viewmodel.BaseViewModel
import com.huaqing.property.ext.viewmodel.addLifecycle

@Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
class CommonLoadingViewModel private constructor() : BaseViewModel(), ILoadingDelegate {

    private val state: MutableLiveData<CommonLoadingState> = MutableLiveData()

    override fun loadingState(): MutableLiveData<CommonLoadingState> = state

    override fun applyState(state: CommonLoadingState) {
        this.state.postValue(state)
    }

    companion object {

        fun instance(lifecycleOwner: LifecycleOwner) =
            CommonLoadingViewModel().apply {
                addLifecycle(lifecycleOwner)
            }
    }
}