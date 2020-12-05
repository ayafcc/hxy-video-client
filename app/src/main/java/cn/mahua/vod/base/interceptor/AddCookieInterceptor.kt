package com.github.StormWyrm.wanandroid.base.net.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class AddCookieInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
//        val cookies = UserUtils.getCookie().split("#")
//        for (cookie in cookies) {
//            requestBuilder.addHeader("Cookie", cookie)
//        }
        return chain.proceed(requestBuilder.build())
    }
}