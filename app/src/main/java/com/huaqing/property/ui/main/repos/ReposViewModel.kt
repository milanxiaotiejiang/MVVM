package com.huaqing.property.ui.main.repos

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import com.huaqing.property.base.viewmodel.BaseViewModel
import com.huaqing.property.ext.livedata.toReactiveStream
import com.huaqing.property.model.Login
import com.uber.autodispose.autoDisposable

class ReposViewModel(
    private val repo: ReposDataSourceRepository
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
        fun instance(fragment: Fragment, repo: ReposDataSourceRepository): ReposViewModel =
            ViewModelProviders
                .of(fragment, ReposViewModelFactory(repo))
                .get(ReposViewModel::class.java)
    }
}


class ReposViewModelFactory(private val repo: ReposDataSourceRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        ReposViewModel(repo) as T

}

