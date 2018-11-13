package com.example.derrick.zeropoint.ui.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.derrick.zeropoint.ui.activitys.MeMoreActivity;
import com.example.derrick.zeropoint.R;
import com.example.derrick.zeropoint.ui.activitys.RealNameApprove;
import com.example.derrick.zeropoint.model.bean.MeMainDat;
import com.example.derrick.zeropoint.layoutManage.GlideRoundTransform;
import com.example.derrick.zeropoint.util.DataHandle;
import com.example.derrick.zeropoint.util.EventKey;
import com.example.derrick.zeropoint.util.HttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by derrick on 2018/8/8.
 */

public class MeFragment extends Fragment {

    private TextView userNameTxt;
    private TextView userPhoneTxt;
    private ImageView userPic;
    private TextView userReal;
    private TextView userBank;
    private TextView userEditPhone;
    private LinearLayout moreLinear;
    private View square;
    private LinearLayout realNameLinearLayout;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        square = inflater.inflate(R.layout.me_fragment,container,false);
        return square;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initClick();
        initBroadCast();
        getMeMainDat();
    }

    private void initBroadCast(){
        IntentFilter intentFilter = new IntentFilter(new EventKey().loginSuccess);
        getActivity().getApplicationContext().registerReceiver(receiver,intentFilter);
    }

    private void initView(){
        userNameTxt = (TextView) square.findViewById(R.id.me_user_name);
        userPhoneTxt = (TextView) square.findViewById(R.id.me_user_phone);
        userPic = (ImageView) square.findViewById(R.id.me_fragment_user_pic);
        userReal = (TextView) square.findViewById(R.id.me_real_name_right);
        userBank = (TextView) square.findViewById(R.id.me_bank_right);
        userEditPhone = (TextView) square.findViewById(R.id.me_edit_phone_right);
        moreLinear = (LinearLayout) square.findViewById(R.id.me_more_bar);
        realNameLinearLayout = (LinearLayout) square.findViewById(R.id.me_real_name_bar);
    }

    private void initClick(){
        moreLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getActivity(),"111",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), MeMoreActivity.class);
                startActivity(intent);
            }
        });

        realNameLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RealNameApprove.class);
                startActivity(intent);
            }
        });


    }

    private void getMeMainDat(){
        String requestAddress = HttpUtil.httpFetchCommonUrl.concat("/apps/User/index");

        RequestBody requestBody = new FormBody.Builder()
                .add("key","")
                .build();
        SharedPreferences preferences = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String token = preferences.getString("token","");

        HttpUtil.postOKHttpRequest(requestAddress,token, requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity().getApplicationContext(),"数据获取失败!",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String responseText = response.body().string();
                final MeMainDat meMainDat = DataHandle.handleMeDatResponse(responseText);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if("1".equals(meMainDat.status) && meMainDat != null){
                            displayMe(meMainDat);
                        }else{
//                            Toast.makeText(getActivity().getApplicationContext(),meMainDat.message,Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(getActivity(), LoginActivity.class);
//                            startActivity(intent);
                        }
                    }
                });
            }
        });
    }

    private void displayMe(MeMainDat dat){
        userNameTxt.setText(dat.dataset.user.nick_name);
        userPhoneTxt.setText(dat.dataset.user.user_name);
        userEditPhone.setText(dat.dataset.user.user_name);
        String uri = HttpUtil.httpFetchCommonUrl+dat.dataset.user.user_avater;
        Glide.with(getContext()).load(uri).bitmapTransform(new GlideRoundTransform(getContext())).into(userPic);
        String realName = "";
        switch (dat.dataset.user.if_realname){
            case 0:
                realName = "未认证";
                break;
            case 1:
                realName = "审核中";
                break;
            case 2:
                realName = "已实名";
                break;
            case -1:
                realName = "申请失败";
                break;

        }
        userReal.setText(realName);
        String cardST = "未绑定";
        if(dat.dataset.user.bank_count > 0){
            cardST = dat.dataset.user.bank_count+"张";
        }
        userBank.setText(cardST);
    }
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
//            Toast.makeText(getActivity().getApplicationContext(),"11111",Toast.LENGTH_SHORT).show();
            getMeMainDat();
        }
    };
}
