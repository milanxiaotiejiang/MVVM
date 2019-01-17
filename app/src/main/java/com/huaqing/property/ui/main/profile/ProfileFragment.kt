package com.huaqing.property.ui.main.profile

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import com.huaqing.property.R
import com.huaqing.property.adapter.BaseDataBindingAdapter
import com.huaqing.property.adapter.OnItemClickListener
import com.huaqing.property.base.ui.fragment.BaseFragment
import com.huaqing.property.common.functional.Consumer
import com.huaqing.property.common.viewmodel.fabanimate.FabAnimateViewModel
import com.huaqing.property.common.viewmodel.loadings.CommonLoadingState
import com.huaqing.property.common.viewmodel.loadings.CommonLoadingViewModel
import com.huaqing.property.databinding.FragmentProfileBinding
import com.huaqing.property.databinding.ItemProfileLayoutBinding
import com.huaqing.property.ext.livedata.toReactiveStream
import com.huaqing.property.model.Profile
import com.huaqing.property.ui.login.LoginActivity
import com.huaqing.property.ui.setting.SettingActivity
import com.huaqing.property.utils.toast
import com.uber.autodispose.autoDisposable
import io.reactivex.Completable
import kotlinx.android.synthetic.main.fragment_profile.*
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    private val REQUESTCODE_SET = 100

    override val kodein = Kodein.lazy {
        extend(parentKodein)
        import(profilekodeinmodule)
    }

    override val layoutId: Int = R.layout.fragment_profile

    val viewModel: ProfileViewModel by instance()
    val fabViewModel: FabAnimateViewModel by instance()
    val loadingViewModel: CommonLoadingViewModel by instance()

    var state: State = State.EXPANDED

    val single: MutableLiveData<Boolean> = MutableLiveData()

    var adapter: BaseDataBindingAdapter<Profile, ItemProfileLayoutBinding> =
        BaseDataBindingAdapter(
            layoutId = R.layout.item_profile_layout,
            dataSource = {
                mutableListOf(
                    Profile(R.string.pro_my_work_order, R.mipmap.pro_my_work_order),
                    Profile(R.string.pro_internal_notification, R.mipmap.pro_internal_notification),
                    Profile(R.string.pro_attendance_wages, R.mipmap.pro_attendance_wages),
                    Profile(R.string.pro_attendance_card, R.mipmap.pro_attendance_card),
                    Profile(R.string.pro_property_maintenance, R.mipmap.pro_property_maintenance),
                    Profile(R.string.pro_security_patrol, R.mipmap.pro_security_patrol),
                    Profile(R.string.pro_cleaning_sanitation, R.mipmap.pro_cleaning_sanitation),
                    Profile(R.string.pro_set_up, R.mipmap.pro_set_up)
                )
            },
            bindBinding = {
                ItemProfileLayoutBinding.bind(it)
            },
            callback = { data, binding, _ ->
                binding.data = data
                binding.proEvent = object : Consumer<Int> {
                    override fun accept(t: Int) {
                        when (t) {
                            R.string.pro_internal_notification -> ""
                            R.string.pro_attendance_wages -> ""
                            R.string.pro_attendance_card -> ""
                            R.string.pro_property_maintenance -> ""
                            R.string.pro_security_patrol -> ""
                            R.string.pro_cleaning_sanitation -> ""
                            R.string.pro_set_up ->
                                activity?.let {
                                    SettingActivity.launch(
                                        it,
                                        this@ProfileFragment,
                                        REQUESTCODE_SET
                                    )
                                }
                        }
                    }

                }
            }
        )

    override fun initView() {

        Completable
            .mergeArray(
                fabViewModel.visibleState
                    .toReactiveStream()
                    .doOnNext { switchFabState(it) }
                    .ignoreElements(),
                viewModel.loadingLayout
                    .toReactiveStream()
                    .filter { it ->
                        it != CommonLoadingState.LOADING    // Refreshing state not used
                    }
                    .doOnNext { loadingViewModel.applyState(it) }
                    .ignoreElements(),
                single
                    .toReactiveStream()
                    .ignoreElements()
            )
            .autoDisposable(scopeProvider)
            .subscribe()

//        adapter.setOnItemClickListener(object : OnItemClickListener {
//                override fun onItemClick(view: View, position: Int) {
//                    when (position) {
//                        1 -> ""
//                        7 -> activity?.let { SettingActivity.launch(it, this@ProfileFragment, REQUESTCODE_SET) }
//                    }
//                }
//        })
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

    private fun switchFabState(show: Boolean) =
        when (show) {
            false -> ObjectAnimator.ofFloat(fab, "translationX", 250f, 0f)
            true -> ObjectAnimator.ofFloat(fab, "translationX", 0f, 250f)
        }.apply {
            duration = 300
            start()
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUESTCODE_SET ->
                when (resultCode) {
                    Activity.RESULT_OK -> logout()
                }
        }
    }

    fun logout() {
        activity?.let {
            LoginActivity.launch(it)
            it.finish()
        }

    }
}

enum class State {
    EXPANDED,
    COLLAPSED,
    INTERNEDIATE
}