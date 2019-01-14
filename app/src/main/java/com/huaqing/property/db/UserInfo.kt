package com.huaqing.property.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.huaqing.property.model.Department
import com.huaqing.property.model.Job

@Entity(tableName = "user_info")
data class UserInfo(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "username")
    var username: String? = null,
    @ColumnInfo(name = "password")
    var password: String? = null,

    @ColumnInfo(name = "avatar")
    val avatar: String,
    @ColumnInfo(name = "departmentId")
    val departmentId: String,
    @ColumnInfo(name = "email")
    val email: String,
    @ColumnInfo(name = "gender")
    val gender: String,
    @ColumnInfo(name = "infoId")
    val infoId: String,
    @ColumnInfo(name = "jobId")
    val jobId: String,
    @ColumnInfo(name = "mobile")
    val mobile: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "salt")
    val salt: String,
    @ColumnInfo(name = "status")
    val status: Int

)