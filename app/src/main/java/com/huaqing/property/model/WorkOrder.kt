package com.huaqing.property.model

import com.google.gson.annotations.SerializedName

data class WorkOrder(
    var code: Int,
    var msg: String,
    var success: Boolean,
    @SerializedName("data")
    var `data`: List<WorkOrderData>
)

data class WorkOrderData(
    val id: String,
    val createDate: String,
    val updateDate: String,
    val remarks: Any,
    val page: Int,
    val rows: Int,
    val community: Community,
    val reporter: Reporter,
    val reporterOwner: Any,
    val reportName: String,
    val reportMobile: String,
    val type: String,
    val expectDate: Any,
    val urgentLevel: String,
    val description: String,
    val status: String,
    val remindCount: Int,
    val pictures: String,
    val audioUrl: Any,
    val assignedUser: AssignedUser,
    val assignedDate: String,
    val afterPictures: Any,
    val comment: Any,
    var afterPictureList: List<*>,
    var pictureList: List<String>
)

data class Community(
    var id: String,
    var createDate: Any,
    var updateDate: Any,
    var remarks: Any,
    var page: Int,
    var rows: Int,
    var propertyCompany: Any,
    var name: String,
    var provinceCode: Any,
    var province: Any,
    var cityCode: Any,
    var city: Any,
    var address: Any,
    var contact: Any,
    var tel: Any,
    var signed: Any,
    var admin: Any,
    var roleIds: String,
    var roleIdList: List<*>
)

data class Reporter(
    var id: String,
    var createDate: Any,
    var updateDate: Any,
    var remarks: Any,
    var page: Int,
    var rows: Int,
    var name: String,
    var username: Any,
    var password: Any,
    var newPassword: Any,
    var salt: Any,
    var email: Any,
    var mobile: String,
    var avatar: String,
    var no: Any,
    var status: Any,
    var department: Any,
    var job: Any,
    var firstEnName: Any,
    var roleIds: String,
    var roleIdList: List<*>
)

data class AssignedUser(
    var id: String,
    var createDate: Any,
    var updateDate: Any,
    var remarks: Any,
    var page: Int,
    var rows: Int,
    var name: String,
    var username: Any,
    var password: Any,
    var newPassword: Any,
    var salt: Any,
    var email: Any,
    var mobile: String,
    var avatar: String,
    var no: String,
    var status: Any,
    var department: Any,
    var job: Any,
    var firstEnName: Any,
    var roleIds: String,
    var roleIdList: List<*>
)