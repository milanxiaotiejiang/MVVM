package com.huaqing.property.ui.main

import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.huaqing.property.R
import com.huaqing.property.base.ui.fragment.BaseFragment
import com.huaqing.property.databinding.FragmentMainBinding
import com.huaqing.property.ui.main.home.HomeFragment
import com.huaqing.property.ui.main.profile.ProfileFragment
import com.huaqing.property.ui.main.repos.ReposFragment
import kotlinx.android.synthetic.main.fragment_main.*
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance

class MainFragment : BaseFragment<FragmentMainBinding>() {

    override val kodein = Kodein.lazy {
        extend(parentKodein)
        import(mainKodeinModule)
        bind<FragmentManager>() with instance(childFragmentManager)
    }

    val viewModel: MainViewModel by instance()

    override val layoutId: Int = R.layout.fragment_main

    override fun initView() {

    }

    fun initFragments(): List<Fragment> =
        listOf(HomeFragment(), ReposFragment(), ProfileFragment())

    fun initTitles(): List<String> = emptyList()

    // port-mode only
    fun onPageSelectChangedPort(index: Int) {
        for (position in 0..index) {
            if (navigation.visibility == View.VISIBLE)
                navigation.menu.getItem(position).isChecked = index == position
        }
    }

    fun onPageAdapter(){

    }

    // port-mode only
    fun onBottomNavigationSelectChanged(menuItem: MenuItem) {
        when (menuItem.itemId) {
            R.id.nav_home -> {
                viewPager.currentItem = 0
            }
            R.id.nav_repos -> {
                viewPager.currentItem = 1
            }
            R.id.nav_profile -> {
                viewPager.currentItem = 2
            }
        }
    }

    // land-mode only
    fun onPageSelectChangedLand(index: Int) {
        if (viewPager.currentItem != index) {
            viewPager.currentItem = index
            closeFabMenuLand()
        }
    }

    // land-mode only
    private fun closeFabMenuLand() {
        if (fabMenu != null && fabMenu.isExpanded)
            fabMenu.toggle()
    }
}