package com.example.derrick.zeropoint.adapter;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.derrick.zeropoint.R;
import com.example.derrick.zeropoint.gson.MainInsuranceData;
import com.example.derrick.zeropoint.util.HttpUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by derrick on 2018/8/14.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter <RecyclerViewAdapter.MyViewHolder>{
    private List<MainInsuranceData> myList;
    private Context myContext;

    public RecyclerViewAdapter(List<MainInsuranceData> myList, Context myContext) {
        this.myList = myList;
        this.myContext = myContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(myContext).inflate(R.layout.main_fragment_recyclerview_item,parent,false));
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

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView iconImage;
        TextView nameText;
        public MyViewHolder(View itemView) {
            super(itemView);
            iconImage = (ImageView) itemView.findViewById(R.id.main_fragment_recyclerview_item_image);
            nameText = (TextView) itemView.findViewById(R.id.main_fragment_recyclerview_item_text);
        }
    }
}
