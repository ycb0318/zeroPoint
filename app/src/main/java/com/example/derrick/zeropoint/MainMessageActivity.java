package com.example.derrick.zeropoint;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.derrick.zeropoint.gson.GetCodeDat;
import com.example.derrick.zeropoint.gson.MainMessageDat;
import com.example.derrick.zeropoint.util.DataHandle;
import com.example.derrick.zeropoint.util.HttpUtil;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainMessageActivity extends AppCompatActivity {
    private ImageView navImg;
    private static final String TAG = "MainMessageActivity";
    private TextView messageItem1;
    private TextView messageItem2;
    private TextView messageItem3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_message);
        initView();
        initClick();

        getMessageMain();
    }



    private void initView(){
        navImg = (ImageView) findViewById(R.id.common_nav_bar);
        messageItem1 = (TextView) findViewById(R.id.main_message_item1);
        messageItem2 = (TextView) findViewById(R.id.main_message_item2);
        messageItem3 = (TextView) findViewById(R.id.main_message_item3);
    }

    private void initClick(){
        navImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getMessageMain(){
        String requestAddress = HttpUtil.httpFetchCommonUrl.concat("/apps/user/user_inside_message_index");

        SharedPreferences preferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        String token = preferences.getString("token","");

        RequestBody requestBody = new FormBody.Builder()
                .add("key","")
                .build();

        HttpUtil.postOKHttpRequest(requestAddress, token ,requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainMessageActivity.this,"请求失败！!",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String responseText = response.body().string();
                final MainMessageDat mainMessageDat = DataHandle.handleMainMessageDatResponse(responseText);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if("1".equals(mainMessageDat.status)){
                            displayMain(mainMessageDat);
                        }else{
                            Toast.makeText(MainMessageActivity.this,mainMessageDat.message,Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }

    private void displayMain(MainMessageDat dat){
        List<MainMessageDat.Item> arr = dat.dataset;
        for(int i = 0; i < arr.size(); i ++){
            if("1".equals(arr.get(i).key)){
                messageItem1.setText(arr.get(i).count);
            }
            if("2".equals(arr.get(i).key)){
                messageItem3.setText(arr.get(i).count);
            }
            if("3".equals(arr.get(i).key)){
                messageItem2.setText(arr.get(i).count);
            }
        }
    }
}
