package com.huaqing.property.common.okhttp

import com.huaqing.property.base.App
import com.huaqing.property.common.Constants
import com.huaqing.property.common.manager.UserManager
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        var authorised = chain.request()

        val headers = authorised.headers("H_NAME")
        if (headers != null && headers.size > 0) {
            if (headers.contains("TOKEN")) {
                val newBuilder = authorised.newBuilder()
                newBuilder.removeHeader("TOKEN")
                var accessToken = App.Companion.INSTANCE.prefs.token
                if (accessToken != null) {
                    authorised = newBuilder
                        .header("Authorization", "Bearer " + accessToken)
                        .build();
                }
            }
        }
        return chain.proceed(authorised)
    }
}