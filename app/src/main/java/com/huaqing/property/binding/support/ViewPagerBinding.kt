package com.huaqing.property.binding.support

import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import arrow.core.toOption
import com.huaqing.property.ext.arrow.whenEmpty
import io.reactivex.functions.Consumer

@BindingAdapter(
    "bind_viewPager_fragmentManager",
    "bind_viewPager_fragments",
    "bind_viewPager_offScreenPageLimit", requireAll = false
)
fun bindViewPagerAdapter(
    viewPager: ViewPager,
    fragmentManager: FragmentManager,
    fragments: List<Fragment>,
    pageLimit: Int?
) {
    viewPager.adapter
        .toOption()
        .whenEmpty {
            viewPager.adapter = ViewPagerAdapter(fragmentManager, fragments)
        }
    viewPager.offscreenPageLimit = pageLimit ?: DEFAULT_OFF_SCREEN_PAGE_LIMIT
}

@BindingAdapter(
    "bind_viewPager_onPageSelectedChanged",
    requireAll = false
)
fun bindOnPageChangeListener(
    viewPager: ViewPager,
    onPageSelected: ViewPagerConsumer
) =
    viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) =
            onPageSelected.accept(position)

        override fun onPageSelected(position: Int) {
        }

    })


class ViewPagerAdapter(fragmentManager: FragmentManager, private val fragments: List<Fragment>) :
    FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int) = fragments[position]

    override fun getCount() = fragments.size
}

const val DEFAULT_OFF_SCREEN_PAGE_LIMIT = 1

interface ViewPagerConsumer : Consumer<Int>