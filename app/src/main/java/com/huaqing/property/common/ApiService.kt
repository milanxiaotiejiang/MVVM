package com.huaqing.property.common

import com.huaqing.property.model.*
import io.reactivex.Flowable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {


    @POST("api-company/sys/login")
    fun login(@Query("username") username: String, @Query("password") password: String): Flowable<Login>

    @Headers("H_NAME" + ":" + "TOKEN")
    @GET("api-company/sys/myInfo")
    fun myInfo(): Flowable<MyInfo>

    @Headers("H_NAME" + ":" + "TOKEN")
    @GET("/api-company/com/message/messageList")
    fun messageList(): Flowable<Meaasge>

    @Headers("H_NAME" + ":" + "TOKEN")
    @GET("/api-company/com/companyInfo/addressList")
    fun addressList(): Flowable<Address>


    @Headers("H_NAME" + ":" + "TOKEN")
    @GET("/api-company/com/workOrder/workOrderList")
    fun workOrderList(@Query("userId") userId: String, @Query("status") status: String): Flowable<WorkOrder>
}