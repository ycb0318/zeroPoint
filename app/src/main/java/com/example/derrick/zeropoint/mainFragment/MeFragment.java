package com.example.derrick.zeropoint.mainFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.derrick.zeropoint.R;
import com.example.derrick.zeropoint.gson.MeMainDat;
import com.example.derrick.zeropoint.gson.WalletMainDat;
import com.example.derrick.zeropoint.util.DataHandle;
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
    private View square;


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
        getMeMainDat();
    }


    private void initView(){
        userNameTxt = (TextView) square.findViewById(R.id.me_user_name);
        userPhoneTxt = (TextView) square.findViewById(R.id.me_user_phone);
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
                            Toast.makeText(getActivity().getApplicationContext(),meMainDat.message,Toast.LENGTH_SHORT).show();
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
    }
}
