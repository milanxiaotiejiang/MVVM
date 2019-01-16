package com.huaqing.property.common.viewmodel.loadings

import androidx.lifecycle.MutableLiveData

interface ILoadingDelegate {
    fun loadingState(): MutableLiveData<CommonLoadingState>
    fun applyState(state: CommonLoadingState)
}

enum class CommonLoadingState {
    ERROR, EMPTY, LOADING, IDLE
}