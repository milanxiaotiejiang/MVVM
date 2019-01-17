package com.huaqing.property.db

import androidx.room.*
import arrow.core.Either
import com.huaqing.property.model.Errors
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.DELETE

@Dao
abstract class UserInfoDao {

    @Query("SELECT * FROM user_info WHERE id= :id")
    abstract fun findDefaultUserInfo(id: Int): Single<UserInfo>

    @Query("SELECT * FROM user_info")
    abstract fun findAll(): List<UserInfo>

    @Update
    abstract fun update(userInfo: UserInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(userInfo: UserInfo)

    @Delete
    abstract fun delete(userInfo: UserInfo)

}