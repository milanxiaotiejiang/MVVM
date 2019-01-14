package com.huaqing.property.model

data class MyInfo(
    var code: Int = 0,
    var msg: String? = null,
    var success: Boolean = false,
    val `data`: Data
)

data class Data(
    val avatar: String,
    val createDate: String,
    val department: Department,
    val email: String,
    val firstEnName: Any,
    val gender: String,
    val id: String,
    val identityNumber: Any,
    val job: Job,
    val mobile: String,
    val name: String,
    val newPassword: Any,
    val no: String,
    val page: Int,
    val password: String,
    val remarks: Any,
    val roleIdList: List<String>,
    val roleIds: String,
    val rows: Int,
    val salt: String,
    val status: Int,
    val updateDate: String,
    val username: String
)

data class Job(
    val createDate: Any,
    val department: Any,
    val id: String,
    val name: String,
    val page: Int,
    val remarks: Any,
    val rows: Int,
    val updateDate: Any
)

data class Department(
    val community: Any,
    val createDate: Any,
    val id: String,
    val name: String,
    val page: Int,
    val propertyCompany: Any,
    val remarks: Any,
    val rows: Int,
    val updateDate: Any
)