package com.example.derrick.zeropoint.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.derrick.zeropoint.ui.activitys.InsuranceWebActivity;
import com.example.derrick.zeropoint.ui.activitys.LoginActivity;
import com.example.derrick.zeropoint.R;
import com.example.derrick.zeropoint.model.bean.MainInsuranceData;
import com.example.derrick.zeropoint.util.HttpUtil;

import java.util.List;

/**
 * Created by derrick on 2018/8/14.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter <RecyclerViewAdapter.MyViewHolder>{
    private List<MainInsuranceData> myList;
    private Context myContext;
    private static final String TAG = "RecyclerViewAdapter";

    public RecyclerViewAdapter(List<MainInsuranceData> myList, Context myContext) {
        this.myList = myList;
        this.myContext = myContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(myContext).inflate(R.layout.main_fragment_recyclerview_item,parent,false));
        myViewHolder.itView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int posi = myViewHolder.getAdapterPosition();
                MainInsuranceData dat = myList.get(posi);
                itemClick(dat);
            }
        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MainInsuranceData dat = myList.get(position);
        String uri = HttpUtil.httpFetchCommonUrl+dat.product_pic_url;
        holder.nameText.setText(dat.product_name);
        Glide.with(myContext).load(uri).into(holder.iconImage);
    }


    @Override
    public int getItemCount() {
        return myList.size();
    }


    private void itemClick(MainInsuranceData dat){
        SharedPreferences preferences = myContext.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String token = preferences.getString("token","1212");
        if(TextUtils.isEmpty(token)){
            Intent intent = new Intent(myContext, LoginActivity.class);
            myContext.startActivity(intent);
        }else{
            Intent intent = new Intent(myContext, InsuranceWebActivity.class);
            intent.putExtra("businesser_id",dat.businesser_id);
            intent.putExtra("token",token);
            intent.putExtra("product_id",dat.product_id);
            myContext.startActivity(intent);
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        View itView;
        ImageView iconImage;
        TextView nameText;
        public MyViewHolder(View itemView) {
            super(itemView);
            itView = itemView;
            iconImage = (ImageView) itemView.findViewById(R.id.main_fragment_recyclerview_item_image);
            nameText = (TextView) itemView.findViewById(R.id.main_fragment_recyclerview_item_text);
        }
    }
}
