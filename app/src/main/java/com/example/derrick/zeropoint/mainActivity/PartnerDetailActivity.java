package com.example.derrick.zeropoint.mainActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.derrick.zeropoint.R;

public class PartnerDetailActivity extends AppCompatActivity {

    private ImageView navImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner_detail);

        initView();
        initClick();
    }

    private void initView(){
        navImg = (ImageView) findViewById(R.id.common_nav_bar);
    }

    private void initClick(){
        navImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
