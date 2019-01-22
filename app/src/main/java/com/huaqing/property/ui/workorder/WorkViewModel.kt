package com.huaqing.property.ui.workorder

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import arrow.core.Option
import arrow.core.none
import arrow.core.some
import com.huaqing.property.R
import com.huaqing.property.adapter.BaseDataBindingAdapter
import com.huaqing.property.base.viewmodel.BaseViewModel
import com.huaqing.property.base.viewstate.ViewState
import com.huaqing.property.common.functional.Consumer
import com.huaqing.property.common.helper.RxSchedulers
import com.huaqing.property.common.viewmodel.loadings.CommonLoadingState
import com.huaqing.property.databinding.ItemWorkLayoutBinding
import com.huaqing.property.ext.livedata.toReactiveStream
import com.huaqing.property.model.WorkOrderData
import com.uber.autodispose.autoDisposable
import io.reactivex.Completable

class WorkViewModel(
    private val repo: WorkDataSourceRepository
) : BaseViewModel() {

    val refreshing: MutableLiveData<Boolean> = MutableLiveData()

    val events = MutableLiveData<List<WorkOrderData>>()

    val loadingLayout: MutableLiveData<CommonLoadingState> = MutableLiveData()

    val error: MutableLiveData<Option<Throwable>> = MutableLiveData()

    val workData: MutableLiveData<WorkOrderData> = MutableLiveData()
    val notarizeData: MutableLiveData<WorkOrderData> = MutableLiveData()

    val status: MutableLiveData<String> = MutableLiveData()

    init {
        Completable
            .mergeArray(
                refreshing.toReactiveStream()
                    .filter { it }
                    .startWith(true)
                    .doOnNext {
                        if (!status.value.isNullOrEmpty()) {
                            initReceivedEvents(status.value!!)
                        }
                    }
                    .ignoreElements(),
                events.toReactiveStream()
                    .doOnNext {
                        adapter.notifyDataSetChanged()
                    }
                    .ignoreElements(),
                status.toReactiveStream()
                    .doOnNext {
                        initReceivedEvents(it)
                    }
                    .ignoreElements()
            )
            .autoDisposable(this)
            .subscribe()
    }

    private fun initReceivedEvents(status: String) {
        repo.workOrderList(status)
            .map { either ->
                either.fold({
                    ViewState.error<List<WorkOrderData>>(it)
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
        events: Option<List<WorkOrderData>> = none(),
        error: Option<Throwable> = none()
    ) {
        this.loadingLayout.postValue(loadingLayout)
        this.error.postValue(error)

        this.events.postValue(events.orNull())
    }


    var adapter: BaseDataBindingAdapter<WorkOrderData, ItemWorkLayoutBinding> =
        BaseDataBindingAdapter(
            layoutId = R.layout.item_work_layout,
            dataSource = {
                events.value
            } as () -> List<WorkOrderData>,
            bindBinding = {
                ItemWorkLayoutBinding.bind(it)
            },
            callback = { data, binding, _ ->
                binding.data = data
                binding.workEvent = object : Consumer<WorkOrderData> {
                    override fun accept(t: WorkOrderData) {
                        workData.postValue(t)
                    }
                }
                binding.notarizeEvent = object : Consumer<WorkOrderData> {
                    override fun accept(t: WorkOrderData) {
                        notarizeData.postValue(t)
                    }
                }
            }
        )

    companion object {
        fun instance(fragment: Fragment, repo: WorkDataSourceRepository): WorkViewModel =
            ViewModelProviders
                .of(fragment, WorkViewModelFactory(repo))
                .get(WorkViewModel::class.java)
    }
}


class WorkViewModelFactory(private val repo: WorkDataSourceRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        WorkViewModel(repo) as T

}

