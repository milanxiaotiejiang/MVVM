package com.huaqing.property.model

import com.google.gson.annotations.SerializedName

data class MyInfo(
    var code: Int = 0,
    var msg: String? = null,
    var success: Boolean = false,
    @SerializedName("data")
    val `data`: InfoData
)