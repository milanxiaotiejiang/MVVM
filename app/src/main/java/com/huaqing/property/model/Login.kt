package com.huaqing.property.model

data class Login(
    var code: Int = 0,
    var msg: String? = null,
    var success: Boolean = false,
    val data: String
)
