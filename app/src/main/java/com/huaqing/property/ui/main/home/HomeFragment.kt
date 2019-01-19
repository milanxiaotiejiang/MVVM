package com.huaqing.property.ui.main.home

import com.huaqing.property.R
import com.huaqing.property.base.ui.fragment.BaseFragment
import com.huaqing.property.common.viewmodel.toolbar.ToolbarViewModel
import com.huaqing.property.databinding.FragmentHomeBinding
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val kodein = Kodein.lazy {
        extend(parentKodein)
        import(homeKodeinModule)
    }

    override val layoutId: Int = R.layout.fragment_home

    val viewModel: HomeViewModel by instance()
    val toolbarViewModel: ToolbarViewModel by instance()

    override fun initView() {

        toolbarViewModel.toolbarName.postValue("消息")
    }
}