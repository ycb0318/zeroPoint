package com.example.derrick.zeropoint.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.derrick.zeropoint.R;
import com.example.derrick.zeropoint.gson.MainDatStoreList;
import com.example.derrick.zeropoint.gson.StoreListDat;
import com.example.derrick.zeropoint.mainActivity.StoreListActivity;
import com.example.derrick.zeropoint.util.HttpUtil;

import java.util.List;

/**
 * Created by derrick on 2018/9/20.
 */

public class RecyclerViewStoreDatAdapter extends RecyclerView.Adapter<RecyclerViewStoreDatAdapter.MyHolder> {
    private List<StoreListDat.Item> myList;
    private Context context;

    public RecyclerViewStoreDatAdapter(List<StoreListDat.Item> myList, Context context) {
        this.myList = myList;
        this.context = context;
    }

    @Override
    public RecyclerViewStoreDatAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerViewStoreDatAdapter.MyHolder myViewHolder = new RecyclerViewStoreDatAdapter.MyHolder(LayoutInflater.from(context).inflate(R.layout.main_fragment_store_dat_recycler_item,parent,false));
        myViewHolder.storeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick();
            }
        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewStoreDatAdapter.MyHolder holder, int position) {
        StoreListDat.Item item = myList.get(position);
        String uri = HttpUtil.httpFetchCommonUrl+"/upload/apps/index/20180402/89e5b541268cbcc9f62cc7f290d63360.jpg";
        if(item.businesser_photo != null){
             uri = HttpUtil.httpFetchCommonUrl+item.businesser_photo.get(0);
        }

        holder.storeTxt.setText(item.businesser_name);
        Glide.with(context).load(uri).into(holder.storeImg);
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    private void itemClick(){
//        Intent intent = new Intent(context, StoreListActivity.class);
//        context.startActivity(intent);
    }

    class MyHolder extends RecyclerView.ViewHolder{
        ImageView storeImg;
        TextView storeTxt;
        View storeView;
        public MyHolder(View itemView) {
            super(itemView);
            storeView = itemView;
            storeImg = (ImageView) itemView.findViewById(R.id.store_recyclerView_store_img);
            storeTxt = (TextView) itemView.findViewById(R.id.store_recyclerView_store_txt);
        }
    }
}
