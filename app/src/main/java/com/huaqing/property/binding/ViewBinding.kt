package com.huaqing.property.binding

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.databinding.BindingAdapter
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.view.longClicks
import io.reactivex.functions.Consumer
import java.util.concurrent.TimeUnit

interface ViewClickConsumer : Consumer<View>

const val DEFAULT_THROTTLE_TIME = 500L

@BindingAdapter("bind_view_visibility")
fun setVisible(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

@SuppressLint("CheckResult")
@BindingAdapter("bind_view_onLongClick")
fun setOnLongClickEvent(view: View, consumer: ViewClickConsumer) {
    view.longClicks()
        .subscribe { consumer.accept(view) }
}

@SuppressLint("CheckResult")
@BindingAdapter("bind_view_onClick", "bind_view_throttleFirst", requireAll = false)
fun setOnClickEvent(view: View, consumer: ViewClickConsumer, time: Long?) {
    view.clicks()
        .throttleFirst(time ?: DEFAULT_THROTTLE_TIME, TimeUnit.MILLISECONDS)
        .subscribe { consumer.accept(view) }
}

@SuppressLint("CheckResult")
@BindingAdapter("bind_view_onClick_closeSoftInput")
fun closeSoftInputWhenClick(view: View, closed: Boolean = false) {
    view.clicks()
        .subscribe {
            if (closed) {
                val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }
}