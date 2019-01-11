package com.huaqing.property.http

import com.huaqing.property.http.core.GlobalErrorTransformer
import com.huaqing.property.utils.toast
import retrofit2.HttpException

fun <T> globalHandleError(): GlobalErrorTransformer<T> = GlobalErrorTransformer(
    globalDoOnErrorConsumer = { error ->
        when (error) {
            is HttpException -> {
                when (error.code()) {
                    401 -> toast { "401 Unauthorized" }
                    404 -> toast { "404 error" }
                    500 -> toast { "500 server error" }
                    else -> toast { "network error" }
                }
            }
            else -> toast { "network error" }
        }
    }
)