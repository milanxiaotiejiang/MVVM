package com.huaqing.property.ui.main.home

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import arrow.core.Either
import arrow.core.Option
import arrow.core.none
import arrow.core.some
import com.huaqing.property.R
import com.huaqing.property.adapter.BaseDataBindingAdapter
import com.huaqing.property.base.viewmodel.BaseViewModel
import com.huaqing.property.base.viewstate.ViewState
import com.huaqing.property.common.helper.RxSchedulers
import com.huaqing.property.common.viewmodel.loadings.CommonLoadingState
import com.huaqing.property.ext.livedata.toReactiveStream
import com.huaqing.property.ext.paging.Paging
import com.huaqing.property.http.globalHandleError
import com.huaqing.property.model.Errors
import com.huaqing.property.model.InfoData
import com.huaqing.property.model.Login
import com.huaqing.property.model.MessageData
import com.uber.autodispose.autoDisposable
import io.reactivex.Completable
import io.reactivex.Flowable
import org.kodein.di.generic.instance

class HomeViewModel(
    private val repo: HomeDataSourceRepository
) : BaseViewModel() {

    val refreshing: MutableLiveData<Boolean> = MutableLiveData()

    val events = MutableLiveData<List<MessageData>>()

    val loadingLayout: MutableLiveData<CommonLoadingState> = MutableLiveData()

    val error: MutableLiveData<Option<Throwable>> = MutableLiveData()


    init {
        Completable
            .mergeArray(
                refreshing.toReactiveStream()
                    .filter { it }
                    .startWith(true)
                    .doOnNext { initReceivedEvents() }
                    .ignoreElements(),
                events.toReactiveStream()
                    .doOnNext { adapter.notifyDataSetChanged() }
                    .ignoreElements()
            )
            .autoDisposable(this)
            .subscribe()
    }

    private fun initReceivedEvents() {
        repo.messageList()
            .map { either ->
                either.fold({
                    ViewState.error<List<InfoData>>(it)
                }, {
                    ViewState.result(it)
                })
            }
            .startWith(ViewState.loading())
            .startWith(ViewState.idle())
            .onErrorReturn {
                ViewState.error(it)
            }
            .observeOn(RxSchedulers.ui)
            .doOnNext { state ->
                when (state) {
                    is ViewState.Refreshing -> applyState()
                    is ViewState.Idle -> applyState()
                    is ViewState.Error -> applyState(
                        loadingLayout = CommonLoadingState.ERROR,
                        error = state.error.some()
                    )
                    is ViewState.Result -> applyState(
                        events = state.result.some()
                    )
                }
            }
            .doFinally { refreshing.postValue(false) }
            .autoDisposable(this)
            .subscribe()
    }


    private fun applyState(
        loadingLayout: CommonLoadingState = CommonLoadingState.IDLE,
        events: Option<List<MessageData>> = none(),
        error: Option<Throwable> = none()
    ) {
        this.loadingLayout.postValue(loadingLayout)
        this.error.postValue(error)

        this.events.postValue(events.orNull())
    }


//    var adapter: BaseDataBindingAdapter<InfoData, ItemAddressLayoutBinding> =
//        BaseDataBindingAdapter(
//            layoutId = R.layout.item_address_layout,
//            dataSource = {
//                events.value
//            } as () -> List<InfoData>,
//            bindBinding = {
//                ItemAddressLayoutBinding.bind(it)
//            },
//            callback = { data, binding, _ ->
//                binding.data = data
//                binding.addressEvent = object : Consumer<Int> {
//                    override fun accept(t: Int) {
//                        when (t) {
//                        }
//                    }
//
//                }
//            }
//        )

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

