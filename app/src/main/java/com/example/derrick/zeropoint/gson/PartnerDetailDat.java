package com.example.derrick.zeropoint.gson;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by derrick on 2018/9/21.
 */

public class PartnerDetailDat {
    public String status;
    public String message;
    @SerializedName("data")
    public Item dataset;

    public class Item {
        public String businesser_id;
        public String businesser_name;
        public String businesser_adderss;
        public String businesser_link_phone;
        public String businesser_link_man;
        public String product_name;
        public String businesser_introduce;
        public List<String> businesser_photo = new ArrayList<>();
    }
}
