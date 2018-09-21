package com.example.derrick.zeropoint.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.derrick.zeropoint.R;
import com.example.derrick.zeropoint.gson.MainDatPartner;
import com.example.derrick.zeropoint.layoutManage.GlideRoundTransform;
import com.example.derrick.zeropoint.mainActivity.PartnerDetailActivity;
import com.example.derrick.zeropoint.util.HttpUtil;

import java.util.List;

/**
 * Created by derrick on 2018/8/14.
 */

public class RecyclerViewPartnerAdapter extends RecyclerView.Adapter<RecyclerViewPartnerAdapter.MyViewHolder> {

    private List<MainDatPartner> myList;
    private Context context;

    public RecyclerViewPartnerAdapter(List<MainDatPartner> myList, Context context) {
        this.myList = myList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.main_fragment_partner_item,parent,false));
        myViewHolder.partnerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int posi = myViewHolder.getAdapterPosition();
                MainDatPartner item = myList.get(posi);
                Intent intent = new Intent(context, PartnerDetailActivity.class);
                intent.putExtra("bussID",item.businesser_id);
                context.startActivity(intent);
            }
        });

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MainDatPartner dat = myList.get(position);
        if(dat.businesser_name == null){
            holder.textViewName.setText("未知店铺");
        }else{
            holder.textViewName.setText(dat.businesser_name);
        }
        if(dat.businesser_introduce == null){
            holder.textViewIntro.setText("未知介绍");
        }else{
            holder.textViewIntro.setText(dat.businesser_introduce);
        }
        if(dat.businesser_photo == null){
            Glide.with(context).load(R.mipmap.ic_launcher).bitmapTransform(new GlideRoundTransform(context)).into(holder.imageView);
//            holder.imageView.setImageURI(Uri.parse(R.mipmap.ic_launcher));
        }else{
            String uri = HttpUtil.httpFetchCommonUrl+dat.businesser_photo.get(0);
            Glide.with(context).load(uri).bitmapTransform(new GlideRoundTransform(context)).into(holder.imageView);
        }


    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textViewName;
        TextView textViewIntro;
        View partnerView;

        public MyViewHolder(View itemView) {
            super(itemView);
            partnerView = itemView;
            imageView = (ImageView) itemView.findViewById(R.id.main_fragment_partner_recyclerview_image);
            textViewName = (TextView) itemView.findViewById(R.id.main_fragment_partner_recyclerview_storeName);
            textViewIntro = (TextView) itemView.findViewById(R.id.main_fragment_partner_recyclerview_storeIntro);
        }
    }
}
