package com.openxu.libs.datasource.remote

import com.openxu.core.utils.FLog
import com.openxu.libs.net.converter.FzyGsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Author: openXu
 * Time: 2021/3/2 10:11
 * class: RetrofitClient
 * Description:
 */
object RetrofitClient {


    /**log**/
    private val logger = HttpLoggingInterceptor.Logger {
        FLog.i(this::class.simpleName, it)
    }
    private val logInterceptor = HttpLoggingInterceptor(logger).apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    /**OkhttpClient*/
    private val okHttpClient = OkHttpClient.Builder()
            .callTimeout(10, TimeUnit.SECONDS)
//            .cookieJar(cookieJar)
            .addNetworkInterceptor(logInterceptor)
            .build()

    // 初始化okhttp
    //        httpGet.setProtocolVersion(HttpVersion.HTTP_1_0);
//        httpGetheader.put(HTTP.CONN_DIRECTIVE,HTTP.CONN_CLOSE);

    /**Retrofit*/
    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(ApiService.BASE_URL)
//        .addConverterFactory(MoshiConverterFactory.create(MoshiHelper.moshi))
        .addConverterFactory(FzyGsonConverterFactory.create())
        .build()

    /**ApiService*/
    val apiService: ApiService = retrofit.create(ApiService::class.java)

}