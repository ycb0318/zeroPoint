package com.example.derrick.zeropoint.util;

import android.app.ProgressDialog;
import android.content.Context;

import java.text.DecimalFormat;

/**
 * Created by derrick on 2018/9/6.
 */

public class Util {
    public static String doubleToString(double num){
        //使用0.00不足位补0，#.##仅保留有效位
        return new DecimalFormat("0.00").format(num);
    }


}
