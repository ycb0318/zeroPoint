package com.example.derrick.zeropoint.util;

import com.example.derrick.zeropoint.gson.MainDat;
import com.example.derrick.zeropoint.gson.MainInsuranceList;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by derrick on 2018/8/13.
 */

public class DataHandle {
    public static MainDat handleMainDataResponse(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            String mainDatContent = jsonObject.toString();
            return new Gson().fromJson(mainDatContent,MainDat.class);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

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
}
