package com.huaqing.property.model

import com.google.gson.annotations.SerializedName

data class Address(
    val code: Int,
    @SerializedName("data")
    val `data`: List<InfoData>,
    val msg: String,
    val success: Boolean
)