package com.huaqing.property.ui.main

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.huaqing.property.base.viewmodel.BaseViewModel

class MainViewModel : BaseViewModel() {
    companion object {
        fun instance(fragment: Fragment): MainViewModel =
            ViewModelProviders
                .of(fragment, MainViewModelFactory())
                .get(MainViewModel::class.java)
    }
}

class MainViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        MainViewModel() as T
}