package com.huaqing.property.common.okhttp

import androidx.room.Database
import com.huaqing.property.base.App
import com.huaqing.property.common.Constants
import com.huaqing.property.common.helper.PrefsHelper
import com.huaqing.property.common.manager.UserManager
import com.huaqing.property.di.prefsModule
import okhttp3.Interceptor
import okhttp3.Response
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class TokenInterceptor(private val prefs: PrefsHelper) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        var authorised = chain.request()

        val headers = authorised.headers("H_NAME")
        if (headers != null && headers.size > 0) {
            if (headers.contains("TOKEN")) {
                val newBuilder = authorised.newBuilder()
                newBuilder.removeHeader("TOKEN")
                var accessToken = prefs.token
                authorised = newBuilder
                    .header("Authorization", "Bearer " + accessToken)
                    .build();
            }
        }
        return chain.proceed(authorised)
    }
}