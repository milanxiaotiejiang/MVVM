package com.huaqing.property.di

import com.huaqing.property.common.ApiService
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit

private const val SERVICE_MODULE_TAG = "serviceModule"

val serviceModule = Kodein.Module(SERVICE_MODULE_TAG) {
    bind<ApiService>() with singleton {
        instance<Retrofit>().create(ApiService::class.java)
    }
}