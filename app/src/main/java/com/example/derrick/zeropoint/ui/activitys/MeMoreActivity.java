package com.example.derrick.zeropoint.ui.activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.derrick.zeropoint.R;
import com.example.derrick.zeropoint.util.EventKey;

import java.util.Timer;


public class MeMoreActivity extends AppCompatActivity {

    private ImageView navImg;
    private LinearLayout loginOutLinear;
    Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_more);

        initView();
        initClick();
    }

    private void initView(){
        navImg = (ImageView) findViewById(R.id.common_nav_bar);
        loginOutLinear = (LinearLayout) findViewById(R.id.me_more_loginout);
    }

    private void initClick(){
        navImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        loginOutLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = getSharedPreferences("userInfo",MODE_PRIVATE).edit();
                editor.clear();
                editor.apply();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"退出成功",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(new EventKey().loginSuccess);
                        sendBroadcast(intent);
                        MeMoreActivity.this.finish();
                    }
                },1500);


            }
        });
    }

}
