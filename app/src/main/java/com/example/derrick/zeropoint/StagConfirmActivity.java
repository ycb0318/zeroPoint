package com.example.derrick.zeropoint;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.derrick.zeropoint.adapter.RecycleViewStagAdapter;
import com.example.derrick.zeropoint.gson.MainMessageDat;
import com.example.derrick.zeropoint.gson.ScanValueDat;
import com.example.derrick.zeropoint.gson.StagConfirmDat;
import com.example.derrick.zeropoint.util.DataHandle;
import com.example.derrick.zeropoint.util.HttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class StagConfirmActivity extends AppCompatActivity {

    private ImageView navImg;
    private ScanValueDat scanValueDat;
    private TextView textViewProductName;
    private TextView textViewOrderPremium;
    private TextView textViewOrderPeriods;
    private TextView textViewOrderserverpremium;
    private TextView textViewOrderrealpremium;
    private RecyclerView stagFqList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stag_confirm);
        scanValueDat = (ScanValueDat) getIntent().getSerializableExtra("data");
        initView();
        initClick();

        getStagInfo();


    }



    private void initView(){

        navImg = (ImageView) findViewById(R.id.common_nav_bar);
        textViewProductName = (TextView) findViewById(R.id.stag_product_name);
        textViewOrderPremium = (TextView) findViewById(R.id.stag_order_premium);
        textViewOrderPeriods = (TextView) findViewById(R.id.stag_order_periods);
        textViewOrderserverpremium = (TextView) findViewById(R.id.stag_order_server_premium);
        textViewOrderrealpremium = (TextView) findViewById(R.id.stag_order_real_premium);
        stagFqList = (RecyclerView) findViewById(R.id.stag_fq_list);
    }

    private void initClick(){
        navImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getStagInfo(){
        String requestAddress = HttpUtil.httpFetchCommonUrl.concat("/apps/Order/first_step");

        SharedPreferences preferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        String token = preferences.getString("token","");

        RequestBody requestBody = new FormBody.Builder()
                .add("businesser_id",scanValueDat.businesser_id)
                .add("product_id",scanValueDat.product_id)
                .add("order_premium",scanValueDat.order_premium)
                .add("order_periods",scanValueDat.order_periods)
                .add("order_mark",scanValueDat.order_mark)
                .build();

        HttpUtil.postOKHttpRequest(requestAddress, token ,requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(StagConfirmActivity.this,"请求失败！!",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String responseText = response.body().string();

                final StagConfirmDat mainMessageDat = DataHandle.handleScanValue(responseText);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if("1".equals(mainMessageDat.status)){
                            displayView(mainMessageDat.dataset);
                        }else{
                            Toast.makeText(StagConfirmActivity.this,mainMessageDat.message,Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }

    private void displayView(StagConfirmDat.Dat dat){
        textViewProductName.setText(dat.data.product_name);
        textViewOrderPremium.setText(dat.data.order_premium);
        textViewOrderPeriods.setText(dat.data.order_periods);
        textViewOrderserverpremium.setText(dat.data.order_server_premium);
        textViewOrderrealpremium.setText(dat.data.order_real_premium);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        stagFqList.setLayoutManager(linearLayoutManager);
        RecycleViewStagAdapter recycleViewStagAdapter = new RecycleViewStagAdapter(dat.data.stages,this);
        stagFqList.setAdapter(recycleViewStagAdapter);

    }
}
