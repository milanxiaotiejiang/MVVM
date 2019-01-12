package com.huaqing.property.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.huaqing.property.base.viewmodel.BaseViewModel
import com.huaqing.property.ui.login.LoginViewModel

class MainViewModel : BaseViewModel()

class MainViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        MainViewModel() as T
}