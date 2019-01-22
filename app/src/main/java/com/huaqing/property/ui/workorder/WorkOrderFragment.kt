package com.huaqing.property.ui.workorder

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.huaqing.property.R
import com.huaqing.property.base.ui.fragment.BaseFragment
import com.huaqing.property.common.viewmodel.fabanimate.FabAnimateViewModel
import com.huaqing.property.common.viewmodel.toolbar.ToolbarViewModel
import com.huaqing.property.databinding.FragmentWorkBinding
import com.huaqing.property.ext.livedata.toReactiveStream
import com.huaqing.property.ui.inform.InformDetailActivity
import com.uber.autodispose.AutoDispose.autoDisposable
import com.uber.autodispose.autoDisposable
import io.reactivex.Completable
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class WorkOrderFragment : BaseFragment<FragmentWorkBinding>() {

    override val kodein: Kodein = Kodein.lazy {
        extend(parentKodein)
        import(workKodeinModule)
    }

    val viewModel: WorkViewModel by instance()
    val fabViewModel: FabAnimateViewModel by instance()
    val toolbarViewModel: ToolbarViewModel by instance()

    override val layoutId: Int = R.layout.fragment_work

    override fun initView() {

        val arg = arguments!!.getString(STATUS)
        viewModel.status.postValue(arg)

        Completable
            .mergeArray(
                viewModel.workData
                    .toReactiveStream()
                    .doOnNext {

                    }
                    .ignoreElements(),
                viewModel.notarizeData
                    .toReactiveStream()
                    .doOnNext {

                    }
                    .ignoreElements()
            )
            .autoDisposable(viewModel)
            .subscribe()
    }


    companion object {
        var STATUS: String = "status"

        fun instance(status: String): WorkOrderFragment {
            val fragment = WorkOrderFragment()
            val bundle = Bundle()
            bundle.putString(STATUS, status)
            fragment.arguments = bundle
            return fragment
        }
    }
}