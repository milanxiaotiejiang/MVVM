package com.huaqing.property.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.huaqing.property.base.viewmodel.BaseViewModel
import com.huaqing.property.ui.main.MainViewModel

class SplashViewModel : BaseViewModel()

class SplashViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        SplashViewModel() as T
}