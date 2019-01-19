package com.huaqing.property.model

import me.yokeyword.indexablerv.IndexableEntity

data class InfoData(
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
    var name: String,
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
) : IndexableEntity {

    @Transient
    private var pinyin: String? = null

    override fun setFieldIndexBy(indexField: String?) {
        name = indexField!!
    }


    override fun setFieldPinyinIndexBy(pinyin: String?) {
        this.pinyin = pinyin
    }

    override fun getFieldIndexBy(): String = name
}