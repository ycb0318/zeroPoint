package com.example.derrick.zeropoint.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by derrick on 2018/9/14.
 */

public class MainMessageDat {
    public String status;
    public String message;
    @SerializedName("data")
    public List<Item> dataset;

    public class Item{
        public String key;
        public String count;
        public String item;
    }
}
