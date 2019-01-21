package com.huaqing.property.binding

import androidx.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.request.RequestOptions
import com.huaqing.property.R
import com.huaqing.property.base.glide.GlideApp
import com.huaqing.property.base.glide.utils.loadImage
import com.huaqing.property.base.glide.utils.loadImageHead
import kotlinx.android.synthetic.main.item_address_layout.view.*

@BindingAdapter("bind_imageView_url")
fun loadImageUrl(imageView: ImageView, model: Any?) {
    loadImage(imageView.context, imageView, model!!)
}

@BindingAdapter("bind_imageView_url_circle")
fun loadImageCircle(imageView: ImageView, url: Any?) {
    GlideApp.with(imageView.context)
        .load(url)
        .apply(RequestOptions().circleCrop().error(R.drawable.head))
        .into(imageView)
}

