package com.huaqing.property.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single

@Dao
abstract class UserInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(userInfo: UserInfo)

    @Query("SELECT * FROM user_info WHERE telephone= :telephone")
    abstract fun findUserByPhoto(telephone: String): Single<UserInfo>

    @Query("SELECT * FROM user_info")
    abstract fun findAll(): List<UserInfo>

}