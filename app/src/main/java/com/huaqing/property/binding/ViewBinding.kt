package com.huaqing.property.binding

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("bind_view_visibility")
fun setVisible(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}