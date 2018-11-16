package com.example.derrick.zeropoint.ui.activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.derrick.zeropoint.R;
import com.example.derrick.zeropoint.base.BaseActivity;
import com.example.derrick.zeropoint.model.bean.GetCodeDat;
import com.example.derrick.zeropoint.model.bean.LoginDat;
import com.example.derrick.zeropoint.util.DataFormat;
import com.example.derrick.zeropoint.util.DataHandle;
import com.example.derrick.zeropoint.util.EventKey;
import com.example.derrick.zeropoint.util.HttpUtil;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";

    @BindView(R.id.saveDat)
    LinearLayout saveLinearLayout;

    @BindView(R.id.getPhoneCode)
    LinearLayout phoneCodeLinearLayout;

    @BindView(R.id.et_phone)
    EditText editTextPhone;

    @BindView(R.id.et_phone_code)
    EditText editTextCode;


//    private LinearLayout saveLinearLayout;
//    private LinearLayout phoneCodeLinearLayout;
//    private EditText editTextPhone;
//    private EditText editTextCode;



//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//        initView();
//    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }


    @Override
    protected void initView(){
//        saveLinearLayout = (LinearLayout) findViewById(R.id.saveDat);
//        phoneCodeLinearLayout = (LinearLayout) findViewById(R.id.getPhoneCode);
//        editTextPhone = (EditText) findViewById(R.id.et_phone);
//        editTextCode = (EditText) findViewById(R.id.et_phone_code);


    }

    @Override
    protected void initEvent() {
        phoneCodeLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneStr = editTextPhone.getText().toString();
                if(DataFormat.isMobileNO(phoneStr)){
                    getCode(phoneStr);
                }else{
                    Toast.makeText(LoginActivity.this,"请输入正确手机号码",Toast.LENGTH_SHORT).show();
                }
            }
        });

        saveLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doLogin();
            }
        });
    }

    private void doLogin(){
        String phoneStr = editTextPhone.getText().toString();
        String codeStr = editTextCode.getText().toString();

        String requestAddress = HttpUtil.httpFetchCommonUrl.concat("/apps/Login/post_login");
        if(TextUtils.isEmpty(phoneStr)){
            Toast.makeText(LoginActivity.this,"请输入正确手机号码！",Toast.LENGTH_SHORT).show();
            return;
        }
        if(DataFormat.isCodeNO(codeStr)){
            RequestBody requestBody = new FormBody.Builder()
                    .add("phone",phoneStr)
                    .add("verify",codeStr)
                    .build();
            HttpUtil.postOKHttpRequest(requestAddress,"", requestBody, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                    LoginActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this.getApplicationContext(),"登录失败!",Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    String responseText = response.body().string();
                    final LoginDat loginDat = DataHandle.handleLoginDatResponse(responseText);
                    LoginActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(loginDat != null && "1".equals(loginDat.status)){
                               loginDatHandle(loginDat);
                            }else{
                                Toast.makeText(LoginActivity.this.getApplicationContext(),"登录失败!",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
        }else{
            Toast.makeText(LoginActivity.this,"请输入正确验证码",Toast.LENGTH_SHORT).show();
        }

        Log.i(TAG, "doLogin: "+phoneStr);
        Log.i(TAG, "doLogin: "+codeStr);
    }
    private void getCode(String phone){
        String requestAddress = HttpUtil.httpFetchCommonUrl.concat("/apps/Login/send_message_code");

        RequestBody requestBody = new FormBody.Builder()
                .add("phone",phone)
                .build();

        HttpUtil.postOKHttpRequest(requestAddress, "",requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                LoginActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this.getApplicationContext(),"获取验证码失败!",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String responseText = response.body().string();
                final GetCodeDat getCodeDat = DataHandle.handleGetCodeResponse(responseText);
                LoginActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this.getApplicationContext(),"验证码已发送!",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    private void loginDatHandle(LoginDat dat){
        Log.i(TAG, "loginDatHandle: "+dat);
        Toast.makeText(LoginActivity.this.getApplicationContext(),"登录成功!",Toast.LENGTH_SHORT).show();
        SharedPreferences preferences = getSharedPreferences("userInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor = getSharedPreferences("userInfo",MODE_PRIVATE).edit();
        editor.putString("token",dat.dataset.token);
        editor.apply();
        Intent intent = new Intent(new EventKey().loginSuccess);
        sendBroadcast(intent);
        LoginActivity.this.finish();

    }


}
