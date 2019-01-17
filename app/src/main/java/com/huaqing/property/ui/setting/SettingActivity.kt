package com.huaqing.property.ui.setting

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.huaqing.property.R
import com.huaqing.property.base.App
import com.huaqing.property.base.ui.activity.BaseActivity
import com.huaqing.property.common.Constants
import com.huaqing.property.common.helper.RxSchedulers
import com.huaqing.property.common.viewmodel.toolbar.ToolbarViewModel
import com.huaqing.property.databinding.ActivitySettingBinding
import com.huaqing.property.ext.jumpBrowser
import com.huaqing.property.ext.livedata.toReactiveStream
import com.uber.autodispose.autoDisposable
import io.reactivex.Completable
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class SettingActivity : BaseActivity<ActivitySettingBinding>() {

    override val kodein = Kodein.lazy {
        extend(parentKodein)
        import(setKodeinModule)
    }

    val viewModel: SetViewModel by instance()
    val toolbarViewModel: ToolbarViewModel by instance()

    override val layoutId = R.layout.activity_setting

    override fun initView() {
        toolbarViewModel.toolbarName.postValue(getString(R.string.pro_set_up))

        Completable
            .mergeArray(
                viewModel.canLogout
                    .toReactiveStream()
                    .doOnNext {
                        when (it) {
                            true -> {
                                setResult(Activity.RESULT_OK)
                                finish()
                            }
                        }
                    }
                    .ignoreElements()
            )
            .observeOn(RxSchedulers.ui)
            .autoDisposable(viewModel)
            .subscribe()
    }

    fun about() {
        App.INSTANCE.jumpBrowser(Constants.ABOUT_US_URL)
    }

    companion object {

        fun launch(activity: FragmentActivity, fragment: Fragment, requestCode: Int) =
            fragment.apply {
                this.startActivityForResult(Intent(activity, SettingActivity::class.java), requestCode)
            }
    }
}