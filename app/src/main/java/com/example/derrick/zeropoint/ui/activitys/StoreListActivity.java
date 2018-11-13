package com.example.derrick.zeropoint.ui.activitys;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.derrick.zeropoint.R;
import com.example.derrick.zeropoint.adapter.RecyclerViewStoreDatAdapter;
import com.example.derrick.zeropoint.model.bean.StoreListDat;
import com.example.derrick.zeropoint.model.bean.StoreListTypeDat;
import com.example.derrick.zeropoint.layoutManage.MyStaggeredGridLayoutManager;
import com.example.derrick.zeropoint.util.DataHandle;
import com.example.derrick.zeropoint.util.HttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class StoreListActivity extends AppCompatActivity {


    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView navImg;
    private RecyclerView storeRecycleList;
    private List<StoreListDat.Item> storeLists;
    private String TypeID;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_list);
        getStoreType();
        initView();
        initData();
    }


    private void initView(){


        navImg = (ImageView) findViewById(R.id.common_nav_bar);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        storeRecycleList = (RecyclerView) findViewById(R.id.main_fragment_recyclerview_store_list);
//        viewPager = (ViewPager) findViewById(R.id.viewpager);

    }

    private void initData(){
        Intent intent = getIntent();
        TypeID = intent.getStringExtra("typeID");

        navImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

//
//        viewPager.setAdapter(new MyPagerAdapter());
//        tabLayout.setupWithViewPager(viewPager);

    }

    private void getStoreType(){
        String requestMainAdress = HttpUtil.httpFetchCommonUrl.concat("/apps/index/businesser_type");

        HttpUtil.sendOKHttpRequest(requestMainAdress, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                StoreListActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(StoreListActivity.this,"信息获取失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                final StoreListTypeDat mainDat = DataHandle.handleStoreListTypeDatResponse(responseText);
                StoreListActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(mainDat != null && "1".equals(mainDat.status)){
//                            Log.i(TAG, "run: "+mainDat.message);
                            displayMainStore(mainDat);
                            getStoreDat(TypeID);
                        }

                    }
                });
            }
        });
    }

    private void initRecyclerViewStore(){
        MyStaggeredGridLayoutManager myStaggerStore = new MyStaggeredGridLayoutManager(2,MyStaggeredGridLayoutManager.VERTICAL,true);
        storeRecycleList.setLayoutManager(myStaggerStore);

        RecyclerViewStoreDatAdapter viewAdapter = new RecyclerViewStoreDatAdapter(storeLists,StoreListActivity.this);
        storeRecycleList.setAdapter(viewAdapter);
    }
    private void getStoreDat(String type){
        String requestMainAdress = HttpUtil.httpFetchCommonUrl.concat("/apps/index/show_type_businesser");

        RequestBody requestBody = new FormBody.Builder()
                .add("key","")
                .add("type",type)
                .build();

        HttpUtil.postOKHttpRequest(requestMainAdress,"",requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                StoreListActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(StoreListActivity.this,"信息获取失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                final StoreListDat mainDat = DataHandle.handleStoreDatTypeDatResponse(responseText);
                StoreListActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(mainDat != null && "1".equals(mainDat.status)){
                            storeLists = mainDat.dataset;
                            initRecyclerViewStore();
                        }else{
                            storeLists = new ArrayList<StoreListDat.Item>();
                            initRecyclerViewStore();
                        }

                    }
                });
            }
        });
    }


    private void displayMainStore(StoreListTypeDat dat){



        for(int i=0;i<dat.dataset.size();i++){
            TabLayout.Tab tab = tabLayout.newTab();
            tab.setText(dat.dataset.get(i).type_name);
            tab.setContentDescription((String) dat.dataset.get(i).id);
            if(TypeID.equals(dat.dataset.get(i).id)){
                tabLayout.addTab(tab,true);
            }else{
                tabLayout.addTab(tab,false);
            }

        }

           tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
               @Override
               public int hashCode() {
                   return super.hashCode();
               }

               @Override
               public void onTabSelected(TabLayout.Tab tab) {
                   int i = tab.getPosition();
                   String str = (String) tab.getContentDescription();
                   getStoreDat(str);
               }

               @Override
               public void onTabUnselected(TabLayout.Tab tab) {

               }

               @Override
               public void onTabReselected(TabLayout.Tab tab) {

               }
           });
    }

//
//    public class MyPagerAdapter extends PagerAdapter {
//        @Override
//        public int getCount() {
//            return counts;
//        }
//        @Override
//        public boolean isViewFromObject(View view, Object object) {
//            return view == object;
//        }
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
//            container.removeView((View) object);
//        }
//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
//            TextView tv=new TextView(StoreListActivity.this);
//            tv.setText("TAB"+position);
//            container.addView(tv);
//            return tv;
//        }
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return "标签"+position;
//        }
//    }

}
