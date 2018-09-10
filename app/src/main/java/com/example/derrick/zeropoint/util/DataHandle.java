package com.example.derrick.zeropoint.util;

import android.util.Log;

import com.example.derrick.zeropoint.gson.GetCodeDat;
import com.example.derrick.zeropoint.gson.LoginDat;
import com.example.derrick.zeropoint.gson.MainDat;
import com.example.derrick.zeropoint.gson.MainDatStoreList;
import com.example.derrick.zeropoint.gson.MainInsuranceList;
import com.example.derrick.zeropoint.gson.WalletMainDat;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by derrick on 2018/8/13.
 */

public class DataHandle {
    private static final String TAG = "DataHandle";
    public static MainDat handleMainDataResponse(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);





            JSONArray arr = new JSONArray();
            JSONObject obj = jsonObject.getJSONObject("data").getJSONObject("business_types");

            Iterator<String> iter = obj.keys();
            while(iter.hasNext()){
                String key1 = iter.next();
                Log.i(TAG, "handleMainDataResponse: "+key1);
                String value1 = obj.getString(key1);
                JSONObject val = new JSONObject(value1);
                arr.put(val);
                Log.i(TAG, "handleMainDataResponse: "+value1);
            }

            jsonObject.getJSONObject("data").put("business_types",arr);







            String mainDatContent = jsonObject.toString();
            return new Gson().fromJson(mainDatContent,MainDat.class);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
    //处理首页保险信息数据
    public static MainInsuranceList handleMainInsuranceResponse(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            String mainDatContent = jsonObject.toString();
            return new Gson().fromJson(mainDatContent,MainInsuranceList.class);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
    //处理获取验证码数据
    public static GetCodeDat handleGetCodeResponse(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            String mainDatContent = jsonObject.toString();
            return new Gson().fromJson(mainDatContent,GetCodeDat.class);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
    //处理登录数据
    public static LoginDat handleLoginDatResponse(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            String mainDatContent = jsonObject.toString();
            return new Gson().fromJson(mainDatContent,LoginDat.class);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
    //处理钱包页面数据
    public static WalletMainDat handleWalletDatResponse(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            String mainDatContent = jsonObject.toString();
            return new Gson().fromJson(mainDatContent,WalletMainDat.class);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
