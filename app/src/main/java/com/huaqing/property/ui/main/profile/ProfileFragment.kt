package com.huaqing.property.ui.main.profile

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.google.android.material.appbar.AppBarLayout
import com.huaqing.property.R
import com.huaqing.property.base.ui.fragment.BaseFragment
import com.huaqing.property.common.viewmodel.toolbar.ToolbarViewModel
import com.huaqing.property.databinding.FragmentProfileBinding
import com.huaqing.property.ext.livedata.toReactiveStream
import com.huaqing.property.ext.statusbar.StatusBarCompat
import com.huaqing.property.ui.main.repos.ReposViewModel
import com.huaqing.property.ui.main.repos.reposKodeinModule
import com.huaqing.property.utils.logger.loge
import com.uber.autodispose.autoDisposable
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    override val kodein = Kodein.lazy {
        extend(parentKodein)
        import(profilekodeinmodule)
    }

    override val layoutId: Int = R.layout.fragment_profile

    val viewModel: ProfileViewModel by instance()

    var state: State = State.EXPANDED

    val single: MutableLiveData<Boolean> = MutableLiveData()

    override fun initView() {

        single
            .toReactiveStream()
            .autoDisposable(scopeProvider)
            .subscribe()

    }

    fun addOnOffsetChanged(off: Int) {
        if (off == 0) {
            if (state != State.EXPANDED) {
                state = State.EXPANDED
            }
        } else if (Math.abs(off) >= mBinding.appBarLayout.getTotalScrollRange()) {
            if (state != State.COLLAPSED) {
                state = State.COLLAPSED
                single.postValue(true)
            }
        } else {
            if (state != State.INTERNEDIATE) {
                if (state == State.COLLAPSED) {
                    single.postValue(false)
                }
                state = State.INTERNEDIATE
            }
        }
    }
}

enum class State {
    EXPANDED,
    COLLAPSED,
    INTERNEDIATE
}