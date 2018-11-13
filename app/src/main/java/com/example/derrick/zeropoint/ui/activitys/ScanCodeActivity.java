package com.example.derrick.zeropoint.ui.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.derrick.zeropoint.R;
import com.example.derrick.zeropoint.model.bean.ScanValueDat;
import com.google.gson.Gson;

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
    public void onCameraAmbientBrightnessChanged(boolean isDark) {

    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        Toast.makeText(this,result,Toast.LENGTH_LONG).show();

        try {
//            JSONObject jsonObject = new JSONObject(result);
//
//            String jsonObjStr = jsonObject.toString();
            ScanValueDat scanValueDat = new Gson().fromJson(result,ScanValueDat.class);

            Intent intent = new Intent(this,StagConfirmActivity.class);
            intent.putExtra("data",scanValueDat);

            startActivity(intent);

            Log.e("¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥",result);
        }catch (Exception e){
            e.printStackTrace();
        }



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
