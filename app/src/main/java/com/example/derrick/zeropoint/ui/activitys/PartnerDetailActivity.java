package com.example.derrick.zeropoint.ui.activitys;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.derrick.zeropoint.R;
import com.example.derrick.zeropoint.ui.adapter.GlideImageLoader;
import com.example.derrick.zeropoint.model.bean.PartnerDetailDat;
import com.example.derrick.zeropoint.util.DataHandle;
import com.example.derrick.zeropoint.util.HttpUtil;
import com.youth.banner.Banner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PartnerDetailActivity extends AppCompatActivity {

    private ImageView navImg;
    private String businesser_id;
    private Banner bannerPartner;
    private TextView storeName;
    private TextView plTxt;
    private TextView addressTxt;
    private TextView connectTxt;
    private LinearLayout callLinear;
    private PartnerDetailDat.Item detailData;
    private List<String> partnerDetailImages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner_detail);

        initView();
        initClick();
        getPartnerDetail();
    }

    private void initView(){
        Intent intent = getIntent();
        businesser_id = intent.getStringExtra("bussID");
        navImg = (ImageView) findViewById(R.id.common_nav_bar);
        bannerPartner = (Banner) findViewById(R.id.partner_banner_img);
        storeName = (TextView) findViewById(R.id.main_partner_fragment_titlebar_left);
        plTxt = (TextView) findViewById(R.id.main_fragment_partner_detail_pl_text);
        addressTxt = (TextView) findViewById(R.id.main_fragment_partner_detail_address_text);
        connectTxt = (TextView) findViewById(R.id.main_fragment_partner_detail_connect_text);
        callLinear = (LinearLayout) findViewById(R.id.call_phone);
    }

    private void initClick(){
        navImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        callLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:"+detailData.businesser_link_phone);
                intent.setData(data);
                startActivity(intent);
            }
        });
    }

    private void getPartnerDetail(){
        String requestMainAdress = HttpUtil.httpFetchCommonUrl.concat("/apps/index/show_businesser");

        RequestBody requestBody = new FormBody.Builder()
                .add("businesser_id",businesser_id)
                .build();

        HttpUtil.postOKHttpRequest(requestMainAdress,"",requestBody,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                PartnerDetailActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(PartnerDetailActivity.this,"信息获取失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                final PartnerDetailDat mainDat = DataHandle.handlePartnerDetailDatResponse(responseText);
                PartnerDetailActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(mainDat != null && "1".equals(mainDat.status)){
                            detailData = mainDat.dataset;
                            displayMain(mainDat.dataset);
                        }

                    }
                });
            }
        });
    }

    private void displayMain(PartnerDetailDat.Item dat){
        List<String> arr = new ArrayList<>();
        if(dat.businesser_photo != null){
            arr = dat.businesser_photo;
        }

        for(int i = 0; i < arr.size(); i++){
            partnerDetailImages.add(HttpUtil.httpFetchCommonUrl+arr.get(i));
        }

        bannerPartner.setImageLoader(new GlideImageLoader());
        bannerPartner.setImages(partnerDetailImages);
        bannerPartner.start();


        storeName.setText(dat.businesser_name);
        plTxt.setText(dat.product_name);
        addressTxt.setText(dat.businesser_adderss);
        connectTxt.setText(dat.businesser_link_phone);
    }
}
