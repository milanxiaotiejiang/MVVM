package com.huaqing.property.ui.inform

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.huaqing.property.base.viewmodel.BaseViewModel
import com.huaqing.property.common.helper.RxSchedulers
import com.huaqing.property.ext.livedata.toReactiveStream
import com.uber.autodispose.autoDisposable
import io.reactivex.Completable

class InformDetailViewModel : BaseViewModel() {

    val title: MutableLiveData<String> = MutableLiveData()
    val date: MutableLiveData<String> = MutableLiveData()
    val content: MutableLiveData<String> = MutableLiveData()

    init {

        Completable
            .mergeArray(
                title
                    .toReactiveStream()
                    .ignoreElements(),
                date
                    .toReactiveStream()
                    .ignoreElements(),
                content
                    .toReactiveStream()
                    .ignoreElements()
            )
            .observeOn(RxSchedulers.ui)
            .autoDisposable(this)
            .subscribe()

    }

    companion object {
        fun instance(activity: FragmentActivity): InformDetailViewModel =
            ViewModelProviders
                .of(activity, InformDetailViewModelFactory())
                .get(InformDetailViewModel::class.java)
    }
}

class InformDetailViewModelFactory() :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        InformDetailViewModel() as T

}