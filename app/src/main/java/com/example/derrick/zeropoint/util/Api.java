package com.example.derrick.zeropoint.util;

import android.util.Log;
import android.widget.Toast;

import com.example.derrick.zeropoint.gson.GetCodeDat;
import com.example.derrick.zeropoint.gson.MainDat;
import com.example.derrick.zeropoint.gson.MainInsuranceList;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by derrick on 2018/9/6.
 */

public class Api {
//    private static final String TAG = "Api";
//    //获取验证码
//    public static void getCode(String phone){
//        final String requestAddress = HttpUtil.httpFetchCommonUrl.concat("/apps/Login/send_message_code");
//
//        RequestBody requestBody = new FormBody.Builder()
//                .add("phone",phone)
//                .build();
//
//        HttpUtil.postOKHttpRequest(requestAddress, requestBody, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String responseText = response.body().string();
//                final GetCodeDat getCodeDat = DataHandle.handleGetCodeResponse(responseText);
//
//            }
//
//        });
//    }
}
