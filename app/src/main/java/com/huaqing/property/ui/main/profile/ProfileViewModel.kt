package com.huaqing.property.ui.main.profile

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import com.huaqing.property.R
import com.huaqing.property.base.viewmodel.BaseViewModel
import com.huaqing.property.ext.livedata.toReactiveStream
import com.huaqing.property.model.Login
import com.huaqing.property.utils.logger.loge
import com.uber.autodispose.autoDisposable

class ProfileViewModel(
    private val repo: ProfileDataSourceRepository
) : BaseViewModel() {

    val toolbarUrl: MutableLiveData<Int> = MutableLiveData()

    init {
        this.toolbarUrl.value = R.mipmap.darkbg
    }

    companion object {
        fun instance(fragment: Fragment, repo: ProfileDataSourceRepository): ProfileViewModel =
            ViewModelProviders
                .of(fragment, ProfileViewModelFactory(repo))
                .get(ProfileViewModel::class.java)
    }
}


class ProfileViewModelFactory(private val repo: ProfileDataSourceRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        ProfileViewModel(repo) as T

}

