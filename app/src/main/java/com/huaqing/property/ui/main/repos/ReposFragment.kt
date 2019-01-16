package com.huaqing.property.ui.main.repos

import com.huaqing.property.R
import com.huaqing.property.base.ui.fragment.BaseFragment
import com.huaqing.property.common.viewmodel.toolbar.ToolbarViewModel
import com.huaqing.property.databinding.FragmentReposBinding
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class ReposFragment : BaseFragment<FragmentReposBinding>() {

    override val kodein = Kodein.lazy {
        extend(parentKodein)
        import(reposKodeinModule)
    }

    override val layoutId: Int = R.layout.fragment_repos

    val viewModel: ReposViewModel by instance()
    val toolbarViewModel: ToolbarViewModel by instance()

    override fun initView() {

        toolbarViewModel.toolbarName.postValue("repos")
    }
}