package com.huaqing.property.binding

import androidx.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.request.RequestOptions
import com.huaqing.property.base.glide.GlideApp
import com.huaqing.property.base.glide.utils.loadImage

@BindingAdapter("bind_imageView_url")
fun loadImageUrl(imageView: ImageView, model: Any?) {
    loadImage(imageView.context, imageView, model!!)
}

@BindingAdapter("bind_imageView_url_circle")
fun loadImageCircle(imageView: ImageView, url: String?) {
    GlideApp.with(imageView.context)
        .load(url)
        .apply(RequestOptions().circleCrop())
        .into(imageView)
}