package com.example.derrick.zeropoint.gson;

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
}
