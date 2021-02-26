package com.openxu.libs.net;

import androidx.annotation.IntDef;

import com.openxu.libs.net.converter.FzyGsonConverterFactory;
import com.openxu.libs.net.interceptor.BaseInterceptor;
import com.openxu.libs.net.interceptor.LoggerInterceptor;
import com.openxu.libs.net.util.TimeOutDns;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Author: openXu
 * Time: 2019/4/12 10:30
 * class: NetworkManager
 * Description: 网络请求管理器
 * Update:
 */
public class NetworkManager {

    private Retrofit retrofit;
    private ApiService apiService;

    private static NetworkManager INSTANCE;

    static {
        loadAddress();
    }

    public static void loadAddress() {
        INSTANCE = new NetworkManager();
    }


    public static NetworkManager getInstance() {
        return INSTANCE;
    }

    private NetworkManager() {

        Map<String, String> header = new HashMap<>();
//        header.put("Content-Type","application/json;charset=UTF-8");
//         header.put("Accept-Encoding","gzip,deflate");
        //可以把客户端请求改成短连接，修改http header:Connection=close（http1.1协议默认是Connection=Keep-Alive，也就是长连接）
        /**
         * 保持长连接，会产生一个问题，如果客户端保持时间比服务端长，服务端超时后主动断开连接了，而客户端认为这个连接还能使用，就会报connect reset
         * 解决办法：客户端保持连接时间比服务端短，断开连接不能让服务端执行，而是客户端断开
         *          .connectionPool(new ConnectionPool(5, 50, TimeUnit.SECONDS))
         */
        header.put("Connection", "keep-alive");
//        header.put("Connection", "close");
        header.put("Accept", "*/*");
//        httpGet.setProtocolVersion(HttpVersion.HTTP_1_0);
//        httpGetheader.put(HTTP.CONN_DIRECTIVE,HTTP.CONN_CLOSE);

        // 初始化okhttp
        OkHttpClient client = new OkHttpClient.Builder()
//                .retryOnConnectionFailure(true)//默认重试一次，若需要重试N次，则要实现拦截器。
                .retryOnConnectionFailure(false)
                //DNS解析超时，如果不设置可能回出现在网络不可用的情况下，DNS解析时间太长
                .connectTimeout(4000, TimeUnit.MILLISECONDS)       //IP连接超时
                .dns(new TimeOutDns(4000, TimeUnit.MILLISECONDS))
                .readTimeout(60000, TimeUnit.MILLISECONDS)
                .writeTimeout(60000, TimeUnit.MILLISECONDS)
                //连接线程池：最大空闲连接数、keep-Alive时间、时间单位
                .connectionPool(new ConnectionPool(5, 30, TimeUnit.SECONDS))
                .addInterceptor(new BaseInterceptor(header))
//                .addInterceptor(new LoggerInterceptor())
                .addInterceptor(new HttpLoggingInterceptor())
                // 模拟服务器返回数据（注释）
                //.addInterceptor(new MockInterceptor(Parrot.create(MockService.class)))
                //.addInterceptor(new RetryInterceptor(2))  //重试
                .build();
        client.dispatcher().setMaxRequests(60);         //最大并发请求数为60，多个主机最大请求数叠加不能超过该并发数（超过取最小值）
        client.dispatcher().setMaxRequestsPerHost(5);   //每个主机最大请求数(保持长连接的数量)

        // 初始化Retrofit
        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("https://www.wanandroid.com")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(FzyGsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public ApiService getApiService() {
        return apiService;
    }

    @IntDef({Method.GET, Method.POST, Method.DOWNLOAD, Method.UPLOAD})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Methods {
    }

    public static class Method {
        public static final int GET = 1;
        public static final int POST = 2;
        public static final int DOWNLOAD = 3;
        public static final int UPLOAD = 4;
    }


}
