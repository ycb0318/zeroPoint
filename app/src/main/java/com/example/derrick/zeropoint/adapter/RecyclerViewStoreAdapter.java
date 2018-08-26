package com.example.derrick.zeropoint.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.derrick.zeropoint.R;
import com.example.derrick.zeropoint.gson.MainDatStoreList;
import com.example.derrick.zeropoint.util.HttpUtil;

import java.util.List;

/**
 * Created by derrick on 2018/8/26.
 */

public class RecyclerViewStoreAdapter extends RecyclerView.Adapter<RecyclerViewStoreAdapter.MyHolder> {

    private List<MainDatStoreList> myList;
    private Context context;

    public RecyclerViewStoreAdapter(List<MainDatStoreList> myList, Context context) {
        this.myList = myList;
        this.context = context;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerViewStoreAdapter.MyHolder myViewHolder = new RecyclerViewStoreAdapter.MyHolder(LayoutInflater.from(context).inflate(R.layout.main_fragement_store_list_recyclerview_item,parent,false));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        MainDatStoreList item = myList.get(position);
        String uri = HttpUtil.httpFetchCommonUrl+item.type_pic;
        holder.storeTxt.setText(item.type_name);
        Glide.with(context).load(uri).into(holder.storeImg);
    }

    @Override
    public int getItemCount() {
       return myList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        ImageView storeImg;
        TextView storeTxt;
        public MyHolder(View itemView) {
            super(itemView);
            storeImg = (ImageView) itemView.findViewById(R.id.recyclerView_store_img);
            storeTxt = (TextView) itemView.findViewById(R.id.recyclerView_store_txt);
        }
    }
}
