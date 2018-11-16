package com.example.derrick.zeropoint.base;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

//    protected Activity myContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
//        myContext = this;
        ButterKnife.bind(this);
        initView();
        initEvent();
    }

    protected abstract int getLayout();

    protected void initView(){}
    protected void initEvent(){}
}
