package com.example.derrick.zeropoint.util;

import android.util.Log;

import com.example.derrick.zeropoint.model.bean.GetCodeDat;
import com.example.derrick.zeropoint.model.bean.LoginDat;
import com.example.derrick.zeropoint.model.bean.MainDat;
import com.example.derrick.zeropoint.model.bean.MainInsuranceList;
import com.example.derrick.zeropoint.model.bean.MainMessageDat;
import com.example.derrick.zeropoint.model.bean.MeMainDat;
import com.example.derrick.zeropoint.model.bean.PartnerDetailDat;
import com.example.derrick.zeropoint.model.bean.StagConfirmDat;
import com.example.derrick.zeropoint.model.bean.StoreListDat;
import com.example.derrick.zeropoint.model.bean.StoreListTypeDat;
import com.example.derrick.zeropoint.model.bean.WalletMainDat;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;

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
    //处理我的页面数据
    public static MeMainDat handleMeDatResponse(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            String mainDatContent = jsonObject.toString();
            return new Gson().fromJson(mainDatContent,MeMainDat.class);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
    //处理消息页面数据
    public static MainMessageDat handleMainMessageDatResponse(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            String mainDatContent = jsonObject.toString();
            return new Gson().fromJson(mainDatContent,MainMessageDat.class);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
    //处理商铺类型数据
    public static StoreListTypeDat handleStoreListTypeDatResponse(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);


            JSONArray arr = new JSONArray();
            JSONObject obj = jsonObject.getJSONObject("data");

            Iterator<String> iter = obj.keys();
            while(iter.hasNext()){
                String key1 = iter.next();
                Log.i(TAG, "handleMainDataResponse: "+key1);
                String value1 = obj.getString(key1);
                JSONObject val = new JSONObject(value1);
                arr.put(val);
                Log.i(TAG, "handleMainDataResponse: "+value1);
            }

            jsonObject.put("data",arr);



            String mainDatContent = jsonObject.toString();
            return new Gson().fromJson(mainDatContent,StoreListTypeDat.class);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
    //处理商铺列表数据
    public static StoreListDat handleStoreDatTypeDatResponse(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);


            JSONArray arr = new JSONArray();
            JSONObject obj = jsonObject.getJSONObject("data");



            Iterator<String> iter = obj.keys();
            while(iter.hasNext()){
                String key1 = iter.next();
                Log.i(TAG, "handleMainDataResponse: "+key1);
                String value1 = obj.getString(key1);
                JSONObject val = new JSONObject(value1);
                arr.put(val);
                Log.i(TAG, "handleMainDataResponse: "+value1);
            }

            jsonObject.put("data",arr);



            String mainDatContent = jsonObject.toString();
            return new Gson().fromJson(mainDatContent,StoreListDat.class);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;

    }

    //处理合作伙伴详情的数据
    public static PartnerDetailDat handlePartnerDetailDatResponse(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            String mainDatContent = jsonObject.toString();
            return new Gson().fromJson(mainDatContent,PartnerDetailDat.class);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    //处理分期确认的数据
    public static StagConfirmDat handleScanValue(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);

            JSONObject obj = jsonObject.getJSONObject("data").getJSONObject("data").getJSONObject("stages");
            JSONArray arr = new JSONArray();

            Iterator<String> iter = obj.keys();
            while(iter.hasNext()){
                String key1 = iter.next();
                Log.i(TAG, "handleMainDataResponse: "+key1);
                String value1 = obj.getString(key1);
                JSONObject val = new JSONObject(value1);
                arr.put(val);
                Log.i(TAG, "handleMainDataResponse: "+value1);
            }

            jsonObject.getJSONObject("data").getJSONObject("data").put("stages",arr);
//            jsonObject.put("data",arr);

            String mainDatContent = jsonObject.toString();
            return new Gson().fromJson(mainDatContent,StagConfirmDat.class);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
