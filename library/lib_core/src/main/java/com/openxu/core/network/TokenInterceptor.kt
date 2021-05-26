package com.openxu.core.network

import okhttp3.Interceptor
import okhttp3.Response
import java.lang.RuntimeException


/**
 * Author: openXu
 * Time: 2021/3/9 11:28
 * class: TokenInterceptor
 * Description: 此拦截器实现了Token过期刷新
 *
 * 当token过期后，调用changeToken接口获取新的token
 * 获取到新token后，更新本地mmkv存储的token值，
 * 然后使用原来的request构建一个新的request重新请求，
 * HttpUrlInterceptor拦截器中会拦截到这个新请求，并获取mmkv中的新token添加到header中
 *
 * 从而实现token过期自动检测和刷新
 *
 */
class TokenInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response  {
        val originalRequest = chain.request()
        throw RuntimeException("自定义异常")
//        throw IOException("自定义异常")
        return chain.proceed(originalRequest)
    }



}