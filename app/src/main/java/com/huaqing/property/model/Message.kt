package com.huaqing.property.model

import com.google.gson.annotations.SerializedName

data class Meaasge(
    val code: Int,
    @SerializedName("data")
    val `data`: List<MessageData>,
    val msg: String,
    val success: Boolean
)

data class MessageData(
    val content: Any,
    val createDate: String,
    val description: String,
    val title: Any,
    val type: String,
    val urgentLevel: String
)
