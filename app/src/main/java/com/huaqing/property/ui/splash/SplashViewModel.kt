package com.huaqing.property.ui.splash

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.huaqing.property.base.viewmodel.BaseViewModel

class SplashViewModel : BaseViewModel() {
    companion object {
        fun instance(activity: FragmentActivity): SplashViewModel =
            ViewModelProviders
                .of(activity, SplashViewModelFactory())
                .get(SplashViewModel::class.java)
    }
}

class SplashViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        SplashViewModel() as T
}