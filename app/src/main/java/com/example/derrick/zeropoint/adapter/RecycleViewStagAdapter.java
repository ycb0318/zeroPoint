package com.example.derrick.zeropoint.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.derrick.zeropoint.R;
import com.example.derrick.zeropoint.gson.StagConfirmDat;

import java.util.List;

/**
 * Created by derrick on 2018/11/5.
 */

public class RecycleViewStagAdapter extends RecyclerView.Adapter<RecycleViewStagAdapter.MyViewHolder>{

    private List<StagConfirmDat.DatStage> myList;
    private Context myContext;

    public RecycleViewStagAdapter(List<StagConfirmDat.DatStage> myList, Context myContext) {
        this.myList = myList;
        this.myContext = myContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(myContext).inflate(R.layout.wallet_stag_confirm_fq_item,parent,false));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        StagConfirmDat.DatStage stage = myList.get(position);
        holder.textViewAccount.setText(stage.stages_premium);
        holder.textViewDate.setText(stage.date);
        String str = String.valueOf(position+1);
        holder.textViewTitle.setText(str);
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textViewAccount;
        TextView textViewDate;
        TextView textViewTitle;

        public MyViewHolder(View itemView) {
            super(itemView);

            textViewAccount = (TextView) itemView.findViewById(R.id.stag_confirm_account);
            textViewDate = (TextView) itemView.findViewById(R.id.stag_confirm_date);
            textViewTitle = (TextView) itemView.findViewById(R.id.stag_confirm_t);
        }
    }

}
