package com.huaqing.property.ext

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.jumpBrowser(url: String) {
    val uri = Uri.parse(url)
    Intent(Intent.ACTION_VIEW, uri).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(this)
    }
}

fun Context.jumpPhone(phone: String) {
    val intent = Intent()
    intent.action = Intent.ACTION_CALL
    val data = Uri.parse("tel:$phone")
    intent.data = data
    startActivity(intent)
}