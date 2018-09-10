package com.example.derrick.zeropoint.util;

import android.text.TextUtils;

/**
 * Created by derrick on 2018/9/6.
 */

public class DataFormat {
    //校验手机号码是否正确
    public static boolean isMobileNO(String mobile){
        String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobile)) return false;
        else return mobile.matches(telRegex);

    }
    //校验验证码
    public static boolean isCodeNO(String code){
        if (TextUtils.isEmpty(code)) return false;
        else return true;

    }
}
