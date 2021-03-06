package com.example.derrick.zeropoint.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by derrick on 2018/8/13.
 */

public class MainDatData {
    @SerializedName("inside_message_count")
    public int messageCount;
    @SerializedName("slide_list")
    public List<MainDatSlideList> slideLists;
    @SerializedName("businesser_list")
    public List<MainDatPartner> storeList;
    @SerializedName("business_types")
    public List<MainDatStoreList> businessTypes;
}

