package com.huaqing.property.ui.main.profile

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import arrow.core.Option
import arrow.core.none
import arrow.core.toOption
import com.huaqing.property.R
import com.huaqing.property.base.viewmodel.BaseViewModel
import com.huaqing.property.common.manager.UserManager
import com.huaqing.property.common.viewmodel.loadings.CommonLoadingState
import com.huaqing.property.db.UserInfo
import com.huaqing.property.ext.arrow.whenNotNull

class ProfileViewModel(
    private val repo: ProfileDataSourceRepository
) : BaseViewModel() {

    val error: MutableLiveData<Option<Throwable>> = MutableLiveData()
    val loading: MutableLiveData<Boolean> = MutableLiveData()

    val loadingLayout: MutableLiveData<CommonLoadingState> = MutableLiveData()

    val toolbarUrl: MutableLiveData<Int> = MutableLiveData()

    val user: MutableLiveData<UserInfo> = MutableLiveData()
    val avatar: MutableLiveData<String> = MutableLiveData()
    val name: MutableLiveData<String> = MutableLiveData()

    init {
        this.toolbarUrl.value = R.mipmap.darkbg

        applyState(user = UserManager.INSTANCE.toOption())
    }

    private fun applyState(
        isLoading: Boolean = false,
        user: Option<UserInfo> = none(),
        error: Option<Throwable> = none()
    ) {
        this.loading.postValue(isLoading)
        this.error.postValue(error)

        user.whenNotNull {
            this.user.postValue(it)
            this.avatar.postValue(it.avatar)
            this.name.postValue(it.name)
        }

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


