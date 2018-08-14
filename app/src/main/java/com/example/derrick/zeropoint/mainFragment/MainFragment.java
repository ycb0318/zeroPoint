package com.example.derrick.zeropoint.mainFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.derrick.zeropoint.R;
import com.example.derrick.zeropoint.adapter.GlideImageLoader;
import com.example.derrick.zeropoint.gson.MainDat;
import com.example.derrick.zeropoint.gson.MainDatData;
import com.example.derrick.zeropoint.gson.MainDatSlideList;
import com.example.derrick.zeropoint.util.DataHandle;
import com.example.derrick.zeropoint.util.HttpUtil;
import com.youth.banner.Banner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by derrick on 2018/8/8.
 */

public class MainFragment extends Fragment {

    private List<String> viewPagerUri = new ArrayList<>();
    private Banner banner;
    private View square;
    private TextView titleBarRightInfo;
    private static final String TAG = "MainFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        square = inflater.inflate(R.layout.main_fragment,container,false);
        initView();
//        initViewPager();
        return square;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getMainData();

    }

//    初始化view
    private void initView(){
        banner = (Banner) square.findViewById(R.id.banner);
        titleBarRightInfo = (TextView) square.findViewById(R.id.main_fragment_titlebar_right);
    }

//    轮播图初始化
    private void initViewPager(){
        viewPagerUri.add("http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg");
        viewPagerUri.add("http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg");
        viewPagerUri.add("http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg");
    }

//    获取首页数据

    private void getMainData(){
        String requestMainAdress = "http://lf.upingou.com/apps/index/index";

        HttpUtil.sendOKHttpRequest(requestMainAdress, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity().getApplicationContext(),"首页信息获取失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                final MainDat mainDat = DataHandle.handleMainDataResponse(responseText);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(mainDat != null && "1".equals(mainDat.status)){
//                            Log.i(TAG, "run: "+mainDat.message);
                            displayMain(mainDat.data);
                        }

                    }
                });
            }
        });

    }
    private void displayMain(MainDatData data){
        //信息数量
        titleBarRightInfo.setText(data.messageCount+"条消息");

        //图片轮播
        List<MainDatSlideList> arr = data.slideLists;
        for(int i = 0; i < arr.size()-1; i ++){
            Log.i(TAG, "displayMain: "+arr.get(i).imageUri);
            viewPagerUri.add(HttpUtil.httpFetchCommonUrl+arr.get(i).imageUri);
        }
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(viewPagerUri);
        banner.start();

    }
}
