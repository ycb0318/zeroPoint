package com.example.derrick.zeropoint.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by derrick on 2018/9/6.
 */

public class LoginDat {
    public String status;
    public String message;
    @SerializedName("data")
    public responseData dataset;

    public class responseData{
        public String token;
    }
}
