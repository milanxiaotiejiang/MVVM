package com.huaqing.property.di

import com.google.gson.Gson
import com.huaqing.property.common.Constants
import com.huaqing.property.common.okhttp.TokenInterceptor
import com.huaqing.property.utils.logger.loge
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val HTTP_CLIENT_MODULE_TAG = "httpClientModule"
const val HTTP_CLIENT_MODULE_INTERCEPTOR_LOG_TAG = "http_client_module_interceptor_log_tag"
const val HTTP_CLIENT_MODULE_INTERCEPTOR_TOKEN_TAG = "http_client_module_interceptor_token_tag"

const val TIME_OUT_SECONDS = 10

val httpClientModule = Kodein.Module(HTTP_CLIENT_MODULE_TAG) {
    //该函数每次都会被调用并返回对应的依赖
    bind<Retrofit.Builder>() with provider {
        Retrofit.Builder()
    }

    bind<OkHttpClient.Builder>() with provider {
        OkHttpClient.Builder()
    }

    bind<Retrofit>() with singleton {
        instance<Retrofit.Builder>()
            .baseUrl(Constants.HOST_API)
            .client(instance())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    bind<Interceptor>(HTTP_CLIENT_MODULE_INTERCEPTOR_LOG_TAG) with singleton {

        HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
            loge { "RetrofitModule : $message" }
        })
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    bind<Interceptor>(HTTP_CLIENT_MODULE_INTERCEPTOR_TOKEN_TAG) with singleton {
        TokenInterceptor(instance())
    }

    bind<OkHttpClient>() with singleton {
        instance<OkHttpClient.Builder>()
            .connectTimeout(TIME_OUT_SECONDS.toLong(), TimeUnit.SECONDS)
            .readTimeout(TIME_OUT_SECONDS.toLong(), TimeUnit.SECONDS)
            .addInterceptor(instance(HTTP_CLIENT_MODULE_INTERCEPTOR_LOG_TAG))
            .addInterceptor(instance(HTTP_CLIENT_MODULE_INTERCEPTOR_TOKEN_TAG))
            .build()
    }

    bind<Gson>() with singleton { Gson() }
}