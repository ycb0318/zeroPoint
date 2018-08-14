package com.example.derrick.zeropoint.util;

/**
 * Created by derrick on 2018/8/13.
 */

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


    public static void postOKHttpRequest(String url, RequestBody requestBody , okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(callback);
    }
}
