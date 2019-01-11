package com.huaqing.property.common

import com.huaqing.property.model.LoginBean
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {


    @POST("api-company/sys/login")
    fun login(@Query("username") username: String, @Query("password") password: String): Flowable<LoginBean>

}