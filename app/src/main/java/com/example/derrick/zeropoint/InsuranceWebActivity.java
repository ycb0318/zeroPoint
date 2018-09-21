package com.example.derrick.zeropoint;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class InsuranceWebActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance_web);
        initView();
    }

    private void initView(){
        Intent intent = getIntent();
        String product_id = intent.getStringExtra("product_id");
        String businesser_id = intent.getStringExtra("businesser_id");
        String token = intent.getStringExtra("token");

        String url = "http://lf.upingou.com/mobile".concat("?token=").concat(token).concat("&product_id=").concat(product_id).concat("&businesser_id=").concat(businesser_id);
        webView = (WebView) findViewById(R.id.insurance_web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }
}
