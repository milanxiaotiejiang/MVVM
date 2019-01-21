package com.huaqing.property.ui.workorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.huaqing.property.R
import com.huaqing.property.base.ui.activity.BaseActivity
import com.huaqing.property.common.viewmodel.toolbar.ToolbarViewModel
import com.huaqing.property.databinding.ActivityWorkOrderBinding
import kotlinx.android.synthetic.main.activity_work_order.*
import kotlinx.android.synthetic.main.toolbar.*
import org.kodein.di.Kodein
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

class WorkOrderActivity : BaseActivity<ActivityWorkOrderBinding>() {


    override val kodein = Kodein.lazy {
        extend(parentKodein)
        bind<ToolbarViewModel>() with scoped<AppCompatActivity>(AndroidLifecycleScope).singleton {
            ToolbarViewModel.instance()
        }
        bind<FragmentManager>() with instance(supportFragmentManager)
    }

    val toolbarViewModel: ToolbarViewModel by instance()

    override val layoutId: Int = R.layout.activity_work_order

    override fun initView() {
        toolbarViewModel.setSupportBar(this, tool_bar, "我的工单")
    }


    fun initFragments(): List<Fragment> =
        listOf(
            WorkOrderFragment.instance("1"),
            WorkOrderFragment.instance("2"),
            WorkOrderFragment.instance("3"),
            WorkOrderFragment.instance("4")
        )

    fun initTitles(): List<String> =
        listOf(
            getString(R.string.work_nav_whole),
            getString(R.string.work_nav_to_be_accepted),
            getString(R.string.work_nav_accepted),
            getString(R.string.work_nav_completed)
        )

    fun onPageAdapter() {
        pagerSlidingTabStrip.setViewPager(viewPager)
    }

    companion object {

        fun launch(activity: FragmentActivity, fragment: Fragment) =
            activity.apply {
                fragment.startActivity(Intent(this, WorkOrderActivity::class.java))
            }
    }
}