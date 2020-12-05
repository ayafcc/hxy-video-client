package com.github.StormWyrm.wanandroid.base.net.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class SaveCookieInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        var requestUrl = request.url.toString()
        return response
    }
}