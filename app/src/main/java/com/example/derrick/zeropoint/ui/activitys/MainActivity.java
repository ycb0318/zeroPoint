package com.example.derrick.zeropoint.ui.activitys;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.derrick.zeropoint.R;
import com.example.derrick.zeropoint.ui.fragments.MainFragment;
import com.example.derrick.zeropoint.ui.fragments.MeFragment;
import com.example.derrick.zeropoint.ui.fragments.WalletFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MainActivity";
    private static boolean isExit = false;

    protected MainFragment mainFragment = new MainFragment();
    protected WalletFragment walletFragment = new WalletFragment();
    protected MeFragment meFragment = new MeFragment();

    protected LinearLayout menu_main;
    protected LinearLayout menu_wallet;
    protected LinearLayout menu_me;


    private static Handler myhandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0){
                isExit = false;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT >= 21){
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.BLACK);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }


        Log.i(TAG, "onCreate: "+ Build.VERSION.SDK_INT);

        initMenu();

        this.getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container_content,mainFragment)
                .add(R.id.container_content,walletFragment)
                .hide(walletFragment)
                .add(R.id.container_content,meFragment)
                .hide(meFragment)
                .commit();
    }

    private void initMenu(){
        menu_main = (LinearLayout) findViewById(R.id.menu_main);
        menu_wallet = (LinearLayout) findViewById(R.id.menu_wallet);
        menu_me = (LinearLayout) findViewById(R.id.menu_me);


        menu_main.setOnClickListener(this);
        menu_wallet.setOnClickListener(this);
        menu_me.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.menu_main:
                this.getSupportFragmentManager()
                        .beginTransaction()
                        .show(mainFragment)
                        .hide(walletFragment)
                        .hide(meFragment)
                        .commit();
                break;
            case R.id.menu_wallet:
                this.getSupportFragmentManager()
                        .beginTransaction()
                        .hide(mainFragment)
                        .show(walletFragment)
                        .hide(meFragment)
                        .commit();
                break;
            case R.id.menu_me:
                this.getSupportFragmentManager()
                        .beginTransaction()
                        .hide(mainFragment)
                        .hide(walletFragment)
                        .show(meFragment)
                        .commit();
                break;

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit(){
        if(!isExit){
            isExit = true;
            Toast.makeText(this,"再按一次退出零分",Toast.LENGTH_SHORT).show();
            myhandler.sendEmptyMessageDelayed(0,2000);
        }else{
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//设置启动的界面在新的栈中
            intent.addCategory(Intent.CATEGORY_HOME);//启动Home界面
            startActivity(intent);
        }
    }
}
