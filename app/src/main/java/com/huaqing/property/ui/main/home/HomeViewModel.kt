package com.huaqing.property.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.huaqing.property.base.viewmodel.BaseViewModel

class HomeViewModel(
    private val repo: HomeDataSourceRepository
) : BaseViewModel() {
}


class HomeViewModelFactory(private val repo: HomeDataSourceRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        HomeViewModel(repo) as T

}

