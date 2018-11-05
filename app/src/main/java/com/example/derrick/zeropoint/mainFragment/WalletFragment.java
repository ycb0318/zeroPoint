package com.example.derrick.zeropoint.mainFragment;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.derrick.zeropoint.LoginActivity;
import com.example.derrick.zeropoint.R;
import com.example.derrick.zeropoint.ScanCodeActivity;
import com.example.derrick.zeropoint.StagConfirmActivity;
import com.example.derrick.zeropoint.gson.GetCodeDat;
import com.example.derrick.zeropoint.gson.MainDatData;
import com.example.derrick.zeropoint.gson.WalletMainDat;
import com.example.derrick.zeropoint.util.DataHandle;
import com.example.derrick.zeropoint.util.EventKey;
import com.example.derrick.zeropoint.util.HttpUtil;
import com.example.derrick.zeropoint.util.Util;

import org.w3c.dom.Text;

import java.io.IOException;
import java.text.DecimalFormat;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zbar.ZBarView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by derrick on 2018/8/8.
 */

public class WalletFragment extends Fragment {

    private static final String TAG = "WalletFragment";

    private View square;
    private LinearLayout scanLinearLayout;
    private TextView walletCanuseEdText;
    private TextView walletTotalED;
    private TextView walletMonthMon;
    private TextView walletMonthTxt;
    private TextView walletAuto;
    private TextView walletAllPay;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         square = inflater.inflate(R.layout.wallet_fragment,container,false);
         initView();
         initBroadCast();
         return square;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initClick();
        getWalletMainDat();
    }


    private void initView(){
        scanLinearLayout = (LinearLayout) square.findViewById(R.id.wallet_scan_code);
        walletCanuseEdText = (TextView) square.findViewById(R.id.wallet_canuse_ed);
        walletTotalED = (TextView) square.findViewById(R.id.wallet_total_ed);
        walletMonthMon = (TextView) square.findViewById(R.id.month_bill_mon);
        walletMonthTxt = (TextView) square.findViewById(R.id.month_bill_txt);
        walletAuto = (TextView) square.findViewById(R.id.wallet_auto);
        walletAllPay = (TextView) square.findViewById(R.id.all_wait_pay);

    }

    private void initBroadCast(){
        IntentFilter intentFilter = new IntentFilter(new EventKey().loginSuccess);
        getActivity().getApplicationContext().registerReceiver(receiver,intentFilter);
    }

    private void initClick(){
        scanLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                String token = preferences.getString("token","");

                Intent intentStag = new Intent(getActivity(), StagConfirmActivity.class);
                startActivity(intentStag);
           /*
               if(TextUtils.isEmpty(token)){
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }else{

                    int hasReadExternalStoragePermission = ContextCompat.checkSelfPermission(getActivity().getApplication(),Manifest.permission.CAMERA);
                    Log.e("PERMISION_CODE",hasReadExternalStoragePermission+"***");
                    Log.e("PERMISION_CODE",PackageManager.PERMISSION_GRANTED+"*******");
                    if(hasReadExternalStoragePermission== PackageManager.PERMISSION_GRANTED){
                        Intent intent = new Intent(getActivity(), ScanCodeActivity.class);
                        startActivity(intent);
                        Toast.makeText(getActivity(),"开始扫描...",Toast.LENGTH_SHORT).show();
                    }else{
                        //若没有授权，会弹出一个对话框（这个对话框是系统的，开发者不能自己定制），用户选择是否授权应用使用系统权限
                        WalletFragment.this.requestPermissions(new String[]{Manifest.permission.CAMERA},1);
                    }
                }
             */

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1){
            if(permissions[0].equals(Manifest.permission.CAMERA) && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(getActivity(), ScanCodeActivity.class);
                startActivity(intent);
                Toast.makeText(getActivity(),"开始扫描...",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getActivity(),"拒绝了...",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getWalletMainDat(){
        String requestAddress = HttpUtil.httpFetchCommonUrl.concat("/apps/User_wallet/index");

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
                final WalletMainDat walletMainDat = DataHandle.handleWalletDatResponse(responseText);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if("1".equals(walletMainDat.status) && walletMainDat != null){
                            displayMain(walletMainDat);
                        }else{
                            Toast.makeText(getActivity().getApplicationContext(),walletMainDat.message,Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(getActivity(), LoginActivity.class);
//                            startActivity(intent);
                        }

                    }
                });
            }
        });
    }

    private void displayMain(WalletMainDat data){
        walletCanuseEdText.setText(data.dataset.wallet_info.now_limit);
        walletTotalED.setText(data.dataset.wallet_info.user_max_limit);
        walletMonthTxt.setText(data.dataset.bill.message);
        walletMonthMon.setText(data.dataset.bill.month+"月份账单");
        String str  = Util.doubleToString(data.dataset.surplus_order_premium);
        walletAllPay.setText(str);

        walletAuto.setText(("1".equals(data.dataset.wallet_info.if_auto))?"已开启":"未开启");
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
//            Toast.makeText(getActivity().getApplicationContext(),"11111",Toast.LENGTH_SHORT).show();
            getWalletMainDat();
        }
    };
}
