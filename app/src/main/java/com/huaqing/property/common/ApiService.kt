package com.huaqing.property.common

import com.huaqing.property.model.LoginBean
import com.huaqing.property.model.MyInfoBean
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {


    @POST("api-company/sys/login")
    fun login(@Query("username") username: String, @Query("password") password: String): Flowable<LoginBean>

    @Headers("H_NAME" + ":" + "TOKEN")
    @GET("api-company/sys/myInfo")
    fun myInfo(): Flowable<MyInfoBean>

}