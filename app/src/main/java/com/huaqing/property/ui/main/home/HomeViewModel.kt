package com.huaqing.property.ui.main.home

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import com.huaqing.property.base.viewmodel.BaseViewModel
import com.huaqing.property.base.viewstate.ViewState
import com.huaqing.property.ext.livedata.toReactiveStream
import com.huaqing.property.ext.paging.Paging
import com.huaqing.property.model.Login
import com.uber.autodispose.autoDisposable
import io.reactivex.Flowable
import org.kodein.di.generic.instance

class HomeViewModel(
    private val repo: HomeDataSourceRepository
) : BaseViewModel() {

    val refreshing: MutableLiveData<Boolean> = MutableLiveData()

    val pagedList = MutableLiveData<PagedList<Login>>()

    init {
        refreshing.toReactiveStream()
            .filter { it }
            .startWith(true)
            .doOnNext { initReceivedEvents() }
            .autoDisposable(this)
            .subscribe()
    }

    private fun initReceivedEvents() {
//        Paging
//            .buildReactiveStream(
//                dataSourceProvider = { pageIndex ->
//                    when (pageIndex) {
//                        1 -> queryReceivedEventsRefreshAction()
//                        else -> queryReceivedEventsAction(pageIndex)
//                    }.flatMap { state ->
//                        when (state) {
//                            is ViewState.Result -> Flowable.just(state.result)
//                            else -> Flowable.empty()
//                        }
//                    }
//                }
//            )
//            .doOnNext { pagedList.postValue(it) }
//            .autoDisposable(this)
//            .subscribe()

    }

//    private fun queryReceivedEventsRefreshAction(): Flowable<ViewState<List<Login>>> {
//    }
//
//    private fun queryReceivedEventsAction(pageIndex: Int): Flowable<ViewState<List<Login>>> {
//    }


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

