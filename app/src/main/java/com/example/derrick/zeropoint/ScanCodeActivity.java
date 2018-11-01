package com.example.derrick.zeropoint;

import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zbar.ZBarView;

public class ScanCodeActivity extends AppCompatActivity implements QRCodeView.Delegate {


    private QRCodeView myqrcodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code);
        initView();
    }


    @Override
    public void onScanQRCodeSuccess(String result) {
        Toast.makeText(this,result,Toast.LENGTH_LONG).show();
        Log.e("¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥",result+"*******");
    }

    @Override
    public void onScanQRCodeOpenCameraError() {

        Toast.makeText(this,"错误",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        myqrcodeView.startCamera();
        myqrcodeView.showScanRect();
        myqrcodeView.startSpot();
    }

    @Override
    public void onStop() {
        super.onStop();
        myqrcodeView.stopCamera();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        myqrcodeView.onDestroy();
    }

    private void initView(){

        myqrcodeView = (ZBarView) findViewById(R.id.zbarview);

        myqrcodeView.setDelegate(this);
    }
}
