package com.example.derrick.zeropoint.util;

/**
 * Created by derrick on 2018/8/13.
 */

import android.text.TextUtils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


public class HttpUtil {

    public static final String httpFetchCommonUrl = "http://lf.upingou.com";

    public static void sendOKHttpRequest(String url,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(callback);
    }


    public static void postOKHttpRequest(String url, String token,RequestBody requestBody , okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request.Builder builder = new Request.Builder().url(url);
        if(!TextUtils.isEmpty(token)){
            builder.addHeader("token",token);
        }

        Request request = builder.post(requestBody).build();

        client.newCall(request).enqueue(callback);
    }
}
