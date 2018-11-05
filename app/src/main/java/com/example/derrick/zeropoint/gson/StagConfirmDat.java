package com.example.derrick.zeropoint.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by derrick on 2018/11/5.
 */

public class StagConfirmDat {
    public String status;
    public String message;
    @SerializedName("data")
    public Dat dataset;


    public class Dat{
        public Boolean status;
        public DatDat data;
    }

    public class DatDat{
        public String user_id;
        public String businesser_id;
        public String order_periods;
        public String order_premium;
        public String product_name;
        public String order_server_premium;
        public String order_real_premium;
        public String if_periods;
        public String order_mark;
        public String product_id;
        public List<DatStage> stages;
    }

    public class DatStage{
        public String stages_premium;
        public String date;
        public String stage_id;
    }
}
