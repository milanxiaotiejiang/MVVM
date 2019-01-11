package com.huaqing.property.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = arrayOf(UserInfo::class),
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userInfoDao(): UserInfoDao
}