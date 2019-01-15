package com.huaqing.property.base.glide.utils

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.TransitionOptions
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.huaqing.property.base.glide.GlideApp
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.bumptech.glide.request.transition.ViewPropertyTransition
import com.huaqing.property.R


/**
 * 清理内存中的缓存。
 *
 * @param context
 */
fun clearMemory(context: Context) {
    GlideApp.get(context).clearMemory()
}

/**
 * 清理硬盘中的缓存
 *
 * @param context
 */
fun clearDiskCache(context: Context) {
    GlideApp.get(context).clearDiskCache()
}

/**
 * @param context        上下文
 * @param imageView      图片展示控件
 * @param model          加载资源
 * @param placeholder    请求图片加载中
 * @param error          请求图片加载错误
 * @param priority       优先级，设置图片加载的顺序
 * @param skip           是否跳过内存，true跳过
 * @param strategy       磁盘缓存策略
 * @param transformation 图片变换
 *
 *
 * DiskCacheStrategy.ALL           使用DATA和RESOURCE缓存远程数据，仅使用RESOURCE来缓存本地数据。
 * DiskCacheStrategy.NONE          不使用磁盘缓存
 * DiskCacheStrategy.DATA          在资源解码前就将原始数据写入磁盘缓存
 * DiskCacheStrategy.RESOURCE      在资源解码后将数据写入磁盘缓存，即经过缩放等转换后的图片资源。
 * DiskCacheStrategy.AUTOMATIC     根据原始图片数据和资源编码策略来自动选择磁盘缓存策略。
 *
 *
 * Priority.LOW
 * Priority.NORMAL
 * Priority.HIGH
 * Priority.IMMEDIATE
 */
fun loadImage(
    context: Context, imageView: ImageView, @Nullable model: Any,
    placeholder: Int, error: Int, @NonNull priority: Priority, skip: Boolean,
    @NonNull strategy: DiskCacheStrategy,
    @NonNull transformation: Transformation<Bitmap>
) {
    val options = RequestOptions()
        .centerCrop()
        .placeholder(placeholder)
        .error(error)
        .priority(priority)
        .skipMemoryCache(skip)
        .diskCacheStrategy(strategy)
        .transform(transformation)

    GlideApp.with(context)
        .load(model)
        .apply(options)
        .into(imageView)

}

fun loadImage(
    context: Context, imageView: ImageView, @Nullable model: Any,
    skip: Boolean, @NonNull strategy: DiskCacheStrategy
) {
    GlideApp.with(context)
        .load(model)
        .apply(
            RequestOptions()
                .centerCrop()
                .skipMemoryCache(skip)
                .diskCacheStrategy(strategy)
        )
        .into(imageView)
}

fun loadImage(context: Context, imageView: ImageView, @Nullable model: Any) {
    loadImage(context, imageView, model, false, DiskCacheStrategy.RESOURCE)
}

fun loadImage(
    context: Context, imageView: ImageView, @Nullable model: Any,
    animator: ViewPropertyTransition.Animator
) {
    GlideApp.with(context)
        .asBitmap()
        .load(model)
        .transition(
            BitmapTransitionOptions()
                .transition(animator)
        )
        .into(imageView)
}