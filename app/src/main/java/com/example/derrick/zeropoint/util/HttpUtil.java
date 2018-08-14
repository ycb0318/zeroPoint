package com.example.derrick.zeropoint.util;

/**
 * Created by derrick on 2018/8/13.
 */

import okhttp3.OkHttpClient;
import okhttp3.Request;


public class HttpUtil {

    public static final String httpFetchCommonUrl = "http://lf.upingou.com";

    public static void sendOKHttpRequest(String url,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(callback);
    }
}
