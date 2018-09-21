package com.example.derrick.zeropoint.mainFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.derrick.zeropoint.MainMessageActivity;
import com.example.derrick.zeropoint.R;
import com.example.derrick.zeropoint.adapter.GlideImageLoader;
import com.example.derrick.zeropoint.adapter.RecyclerViewAdapter;
import com.example.derrick.zeropoint.adapter.RecyclerViewPartnerAdapter;
import com.example.derrick.zeropoint.adapter.RecyclerViewStoreAdapter;
import com.example.derrick.zeropoint.gson.MainDat;
import com.example.derrick.zeropoint.gson.MainDatData;
import com.example.derrick.zeropoint.gson.MainDatPartner;
import com.example.derrick.zeropoint.gson.MainDatSlideList;
import com.example.derrick.zeropoint.gson.MainDatStoreList;
import com.example.derrick.zeropoint.gson.MainInsuranceData;
import com.example.derrick.zeropoint.gson.MainInsuranceList;
import com.example.derrick.zeropoint.layoutManage.MyStaggeredGridLayoutManager;
import com.example.derrick.zeropoint.util.DataHandle;
import com.example.derrick.zeropoint.util.HttpUtil;
import com.youth.banner.Banner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by derrick on 2018/8/8.
 */

public class MainFragment extends Fragment {

    private List<String> viewPagerUri = new ArrayList<>();
    private Banner banner;
    private View square;
    private TextView titleBarRightInfo;
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewStore;
    private RecyclerView partnerRecycleView;
    private List<MainInsuranceData> insuranceList;
    private List<MainDatStoreList> storeLists;
    private List<MainDatPartner> partnerList;
    private LinearLayout messageLinear;
    private static final String TAG = "MainFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        square = inflater.inflate(R.layout.main_fragment,container,false);
        initView();
        initClick();
        return square;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getMainData();
        getMainInsuranceData();
    }

//    初始化view
    private void initView(){
        banner = (Banner) square.findViewById(R.id.banner);
        titleBarRightInfo = (TextView) square.findViewById(R.id.main_fragment_titlebar_right);
        recyclerView = (RecyclerView) square.findViewById(R.id.main_fragment_recyclerview);
        partnerRecycleView = (RecyclerView) square.findViewById(R.id.main_fragment_partner_recyclerview);
        recyclerViewStore = (RecyclerView) square.findViewById(R.id.main_fragment_recyclerview_store);
        messageLinear = (LinearLayout) square.findViewById(R.id.main_fragment_message_click);
    }

    private void initClick(){
        messageLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainMessageActivity.class);
                startActivity(intent);
            }
        });
    }
//    初始化recyclerView 保险公司列表;
    private void initRecyclerView(){

//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);

        MyStaggeredGridLayoutManager myStagger = new MyStaggeredGridLayoutManager(4,MyStaggeredGridLayoutManager.VERTICAL,true);
        recyclerView.setLayoutManager(myStagger);
        RecyclerViewAdapter viewAdapter = new RecyclerViewAdapter(insuranceList,getContext());
        recyclerView.setAdapter(viewAdapter);

    }

    private void initRecyclerViewStore(){
        MyStaggeredGridLayoutManager myStaggerStore = new MyStaggeredGridLayoutManager(2,MyStaggeredGridLayoutManager.VERTICAL,true);
        recyclerViewStore.setLayoutManager(myStaggerStore);

        RecyclerViewStoreAdapter viewAdapter = new RecyclerViewStoreAdapter(storeLists,getContext());
        recyclerViewStore.setAdapter(viewAdapter);
    }




//    获取首页数据
    private void getMainData(){
        String requestMainAdress = HttpUtil.httpFetchCommonUrl.concat("/apps/index/index");

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
                            initRecyclerViewStore();
                        }

                    }
                });
            }
        });

    }

//    获取保险公司列表
    private void getMainInsuranceData(){
        String requestAddress = HttpUtil.httpFetchCommonUrl.concat("/apps/index/car_insurance_list");
        RequestBody requestBody = new FormBody.Builder()
                .add("businesser_id","12")
                .build();
        
        HttpUtil.postOKHttpRequest(requestAddress, "",requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity().getApplicationContext(),"保险公司列表获取失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                final MainInsuranceList mainInsuranceList = DataHandle.handleMainInsuranceResponse(responseText);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        insuranceList = mainInsuranceList.data;
                        initRecyclerView();
                    }
                });
            }
        });
    }

    private void displayMain(MainDatData data){
        partnerList = data.storeList;
        storeLists = data.businessTypes;
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

        //合作伙伴显示
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        partnerRecycleView.setLayoutManager(linearLayoutManager);
        RecyclerViewPartnerAdapter recyclerViewPartnerAdapter = new RecyclerViewPartnerAdapter(partnerList,getContext());
        partnerRecycleView.setAdapter(recyclerViewPartnerAdapter);

    }
}
