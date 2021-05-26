package com.openxu.core.net.interceptor;


import com.openxu.core.utils.FLog;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Author: openXu
 * Time: 2019/2/26 14:47
 * class: BaseInterceptor
 * Description: 基础拦截器，设置URL
 */
public class BaseInterceptor implements Interceptor {
    private Map<String, String> headers;

    public BaseInterceptor(Map<String, String> headers) {
        this.headers = headers;
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        if (headers != null && headers.size() > 0) {
            Set<String> keys = headers.keySet();
            for (String headerKey : keys) {
                builder.addHeader(headerKey, headers.get(headerKey));
            }
        }
        //根据请求类型改变url
        //http://114.115.144.251:8001/User_QueryPhonePrincipal?UserCode=nj&ApplicationID=4ccfccaf-9da0-11e7-840e-fa163ea287f1
        String url = request.url().toString();

        FLog.e("拦截器中Url:"+url);
        if (request.method().equals("GET")) {
        } else if(request.method().equals("POST")){
        }
        request = builder.url(url).build();
        //添加公共参数
        HttpUrl newUrl = request.url().newBuilder()
                .addEncodedQueryParameter("dataKey", "00-00-00-00")
                .build();
        builder.url(newUrl);

//        saveUrl2File(newUrl);
        return chain.proceed(builder.build());
    }

}
