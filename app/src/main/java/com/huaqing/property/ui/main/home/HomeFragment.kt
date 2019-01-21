package com.huaqing.property.ui.main.home

import com.huaqing.property.R
import com.huaqing.property.base.ui.fragment.BaseFragment
import com.huaqing.property.common.viewmodel.fabanimate.FabAnimateViewModel
import com.huaqing.property.common.viewmodel.toolbar.ToolbarViewModel
import com.huaqing.property.databinding.FragmentHomeBinding
import com.huaqing.property.ext.livedata.toReactiveStream
import com.huaqing.property.ui.inform.InformDetailActivity
import com.uber.autodispose.autoDisposable
import io.reactivex.Completable
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val kodein = Kodein.lazy {
        extend(parentKodein)
        import(homeKodeinModule)
    }

    override val layoutId: Int = R.layout.fragment_home

    val viewModel: HomeViewModel by instance()
    val fabViewModel: FabAnimateViewModel by instance()
    val toolbarViewModel: ToolbarViewModel by instance()

    override fun initView() {

        toolbarViewModel.toolbarName.postValue("消息")

        Completable
            .mergeArray(
                viewModel.messageData
                    .toReactiveStream()
                    .doOnNext {
                        if (it.type.isEmpty()) {
                            activity?.let { it1 ->
                                InformDetailActivity.launch(
                                    it1,
                                    this,
                                    it.title,
                                    it.content,
                                    it.createDate
                                )
                            }
                        } else {
//                            activity?.let { it1 ->
//                                WorkOrderActivity.launch(it1, this)
//                            }
                            activity?.let { it1 ->
                                InformDetailActivity.launch(
                                    it1,
                                    this,
                                    "title",
                                    "content",
                                    "createDate"
                                )
                            }
                        }
                    }
                    .ignoreElements()
            )
            .autoDisposable(viewModel)
            .subscribe()
    }
}