package com.huaqing.property.ui.main.repos

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.huaqing.property.R
import com.huaqing.property.adapter.BaseDataBindingAdapter
import com.huaqing.property.adapter.BaseIndexableAdapter
import com.huaqing.property.base.App
import com.huaqing.property.base.ui.fragment.BaseFragment
import com.huaqing.property.common.functional.Consumer
import com.huaqing.property.common.viewmodel.fabanimate.FabAnimateViewModel
import com.huaqing.property.common.viewmodel.toolbar.ToolbarViewModel
import com.huaqing.property.databinding.FragmentReposBinding
import com.huaqing.property.databinding.ItemAddressLayoutBinding
import com.huaqing.property.ext.jumpPhone
import com.huaqing.property.ext.livedata.toReactiveStream
import com.huaqing.property.model.InfoData
import com.huaqing.property.utils.PhoneUtils
import com.huaqing.property.utils.toast
import com.tbruyelle.rxpermissions2.RxPermissions
import com.uber.autodispose.autoDisposable
import io.reactivex.Completable
import kotlinx.android.synthetic.main.fragment_repos.*
import me.yokeyword.indexablerv.IndexableAdapter
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class ReposFragment : BaseFragment<FragmentReposBinding>() {

    override val kodein = Kodein.lazy {
        extend(parentKodein)
        import(reposKodeinModule)
    }

    override val layoutId: Int = R.layout.fragment_repos

    val viewModel: ReposViewModel by instance()
    val fabViewModel: FabAnimateViewModel by instance()
    val toolbarViewModel: ToolbarViewModel by instance()

    override fun initView() {

        toolbarViewModel.toolbarName.postValue("通讯录")

        indexableLayout.setLayoutManager(LinearLayoutManager(indexableLayout.context))
        indexableLayout.setOverlayStyle_Center()
        indexableLayout.setAdapter(adapter)
//        adapter.setOnItemContentClickListener(object : IndexableAdapter.OnItemContentClickListener<InfoData> {
//            override fun onItemClick(v: View?, originalPosition: Int, currentPosition: Int, entity: InfoData?) {
//                if (originalPosition >= 0) {
//                    toast { "选中Header:" + entity!!.name + "  当前位置:" + currentPosition }
//                } else {
//                    toast { "选中Header:" + entity!!.name + "  当前位置:" + currentPosition }
//                }
//            }
//        })

        Completable
            .mergeArray(
                viewModel.events
                    .toReactiveStream()
                    .doOnNext {
                        adapter.setDatas(it)
                    }
                    .ignoreElements()
            )
            .autoDisposable(viewModel)
            .subscribe()
    }


    var adapter: BaseIndexableAdapter<InfoData, ItemAddressLayoutBinding> =
        BaseIndexableAdapter(
            layoutId = R.layout.item_address_layout,
            bindBinding = {
                ItemAddressLayoutBinding.bind(it)
            },
            callback = { data, binding ->
                binding.data = data
                binding.addressEvent = object : Consumer<String> {
                    override fun accept(t: String) {
                        when (t.isEmpty()) {
                            false -> callPhone(t)
                            true -> toast { getString(R.string.no_photo) }
                        }
                    }
                }
            }
        )

    private fun callPhone(phone: String) {
        when (PhoneUtils.isMobileNO(phone)) {
            true -> {
                RxPermissions(this)
                    .requestEach(
                        Manifest.permission.CALL_PHONE
                    )
                    .subscribe({
                        if (it.granted) {
                            App.INSTANCE.jumpPhone(phone)
                        }
                    })
            }

            false -> toast {
                getString(R.string.mobile_phone_number_incorrect)
            }
        }

    }

}