package com.example.derrick.zeropoint.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by derrick on 2018/9/10.
 */

public class WalletMainDat {
    public String status;
    public String message;
    @SerializedName("data")
    public dataObj dataset;

    public class dataObj{
        public Double surplus_order_premium;
        public Wallet wallet_info;
        public Bill bill;
        public Code qd_code;


        public class Wallet{
            @SerializedName("id")
            public String wallet_id;
            public String user_id;
            public String user_max_limit;
            public String now_limit;
            public String if_auto;
        }
        public class Bill{
            public Boolean status;
            public String month;
            public String message;
        }
        public class Code{
            public String type;
            public String message;

        }
    }
}
