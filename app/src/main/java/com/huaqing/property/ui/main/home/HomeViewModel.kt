package com.huaqing.property.ui.main.home

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.huaqing.property.base.viewmodel.BaseViewModel
import com.huaqing.property.ext.livedata.toReactiveStream
import com.uber.autodispose.autoDisposable
import org.kodein.di.generic.instance

class HomeViewModel(
    private val repo: HomeDataSourceRepository
) : BaseViewModel() {

    val refreshing: MutableLiveData<Boolean> = MutableLiveData()


    init {
        refreshing.toReactiveStream()
            .filter { it }
            .startWith(true)
            .doOnNext { initReceivedEvents() }
            .autoDisposable(this)
            .subscribe()
    }

    private fun initReceivedEvents() {


    }

    companion object {
        fun instance(fragment: Fragment, repo: HomeDataSourceRepository): HomeViewModel =
            ViewModelProviders
                .of(fragment, HomeViewModelFactory(repo))
                .get(HomeViewModel::class.java)
    }
}


class HomeViewModelFactory(private val repo: HomeDataSourceRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        HomeViewModel(repo) as T

}

