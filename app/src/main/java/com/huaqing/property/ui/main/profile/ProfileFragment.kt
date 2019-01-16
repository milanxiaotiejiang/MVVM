package com.huaqing.property.ui.main.profile

import androidx.fragment.app.Fragment
import com.huaqing.property.R
import com.huaqing.property.base.ui.fragment.BaseFragment
import com.huaqing.property.common.viewmodel.toolbar.ToolbarViewModel
import com.huaqing.property.databinding.FragmentProfileBinding
import com.huaqing.property.ext.statusbar.StatusBarCompat
import com.huaqing.property.ui.main.repos.ReposViewModel
import com.huaqing.property.ui.main.repos.reposKodeinModule
import com.huaqing.property.utils.logger.loge
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    override val kodein = Kodein.lazy {
        extend(parentKodein)
        import(profilekodeinmodule)
    }

    override val layoutId: Int = R.layout.fragment_profile

    val viewModel: ProfileViewModel by instance()

    override fun initView() {


    }

    fun addOnOffsetChanged(off: Int) {
        loge {
            "$off"
        }
    }
}