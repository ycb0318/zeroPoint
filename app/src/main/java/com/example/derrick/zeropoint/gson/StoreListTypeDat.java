package com.example.derrick.zeropoint.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by derrick on 2018/9/20.
 */

public class StoreListTypeDat {
    public String status;
    public String message;
    @SerializedName("data")
    public List<Item> dataset;

    public class Item{
        public String id;
        public String type_name;
        public String type_pic;
    }
}
