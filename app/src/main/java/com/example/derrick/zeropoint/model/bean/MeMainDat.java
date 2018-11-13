package com.example.derrick.zeropoint.model.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by derrick on 2018/9/10.
 */

public class MeMainDat {
    public String status;
    public String message;
    @SerializedName("data")
    public DataObj dataset;

    public class DataObj{
        public User user;
        public class User{
            @SerializedName("id")
            public String user_id;
            public String user_name;
            public String nick_name;
            public String user_avater;
            public int if_realname;
            public int bank_count;
        }


    }
}
