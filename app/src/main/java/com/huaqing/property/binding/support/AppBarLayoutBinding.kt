package com.huaqing.property.binding.support

import androidx.databinding.BindingAdapter
import com.google.android.material.appbar.AppBarLayout
import com.huaqing.property.common.functional.Consumer
import com.huaqing.property.utils.logger.loge

@BindingAdapter("bind_appBarLayout_addOnOffsetChanged")
fun addOnOffsetChanged(
    appBarLayout: AppBarLayout,
    consumer: AppBarLayoutConsumer
) =
    appBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
        override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
            consumer.accept(verticalOffset)
        }
    })


interface AppBarLayoutConsumer : Consumer<Int>