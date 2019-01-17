package com.huaqing.property.ui.setting

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.huaqing.property.base.viewmodel.BaseViewModel
import com.uber.autodispose.autoDisposable

class SetViewModel(
    private val repo: SetDataSourceRepository
) : BaseViewModel() {

    val canLogout: MutableLiveData<Boolean> = MutableLiveData()

    fun logout() {
        repo.logout()
            .doOnNext {
                canLogout.postValue(true)
            }
            .autoDisposable(this)
            .subscribe()
    }

    companion object {
        fun instance(activity: FragmentActivity, repo: SetDataSourceRepository): SetViewModel =
            ViewModelProviders
                .of(activity, SetViewModelFactory(repo))
                .get(SetViewModel::class.java)
    }

}


class SetViewModelFactory(private val repo: SetDataSourceRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        SetViewModel(repo) as T

}