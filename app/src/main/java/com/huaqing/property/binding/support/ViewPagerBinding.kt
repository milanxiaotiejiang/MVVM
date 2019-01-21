package com.huaqing.property.binding.support

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import arrow.core.toOption
import com.huaqing.property.R.id.viewPager
import com.huaqing.property.common.functional.Consumer
import com.huaqing.property.ext.arrow.whenEmpty

@BindingAdapter(
    "bind_viewPager_fragmentManager",
    "bind_viewPager_fragments",
    "bind_viewPager_titles",
    "bind_viewPager_offScreenPageLimit",
    "bind_viewPager_adapter",
    requireAll = false
)
fun bindViewPagerAdapter(
    viewPager: ViewPager,
    fragmentManager: FragmentManager,
    fragments: List<Fragment>,
    titles: List<String>,
    pageLimit: Int?,
    adapterConsumer: ViewPagerAdapterConsumer
) {
    viewPager.adapter
        .toOption()
        .whenEmpty {
            when (titles.isNullOrEmpty()) {
                true ->
                    viewPager.adapter = ViewPagerAdapter(fragmentManager, fragments)
                false ->
                    viewPager.adapter = ViewPagerAdapter(fragmentManager, fragments, titles)
            }
        }
    viewPager.offscreenPageLimit = pageLimit ?: DEFAULT_OFF_SCREEN_PAGE_LIMIT
    adapterConsumer.accept(viewPager)
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

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

        }

        override fun onPageSelected(position: Int) = onPageSelected.accept(position)


        override fun onPageScrollStateChanged(state: Int) {

        }
    })

class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    private val fragments: List<Fragment>
) : FragmentPagerAdapter(fragmentManager) {

    private var titles: List<String> = emptyList()

    constructor(fragmentManager: FragmentManager, fragments: List<Fragment>, titles: List<String>)
            : this(fragmentManager, fragments) {
        this.titles = titles
    }

    override fun getItem(index: Int): Fragment = fragments[index]

    override fun getCount(): Int = fragments.size

    override fun getPageTitle(position: Int): CharSequence? {
        when (titles.isNullOrEmpty()) {
            true -> return super.getPageTitle(position)
            false -> return this.titles[position]
        }
    }
}

const val DEFAULT_OFF_SCREEN_PAGE_LIMIT = 1

interface ViewPagerConsumer : Consumer<Int>
interface ViewPagerAdapterConsumer : Consumer<View>