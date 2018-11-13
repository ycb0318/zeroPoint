package com.example.derrick.zeropoint.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by derrick on 2018/9/20.
 */

public class StoreListDat {
    public String status;
    public String message;
    @SerializedName("data")
    public List<Item> dataset;

    public class Item{
        public String businesser_id;
        public String businesser_name;
        public String businesser_link_phone;
        public List<String> businesser_photo = new ArrayList<>();
    }
}
