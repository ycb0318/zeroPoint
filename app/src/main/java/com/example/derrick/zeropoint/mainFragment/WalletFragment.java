package com.example.derrick.zeropoint.mainFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.derrick.zeropoint.LoginActivity;
import com.example.derrick.zeropoint.R;

/**
 * Created by derrick on 2018/8/8.
 */

public class WalletFragment extends Fragment {

    private static final String TAG = "WalletFragment";

    private View square;
    private LinearLayout scanLinearLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         square = inflater.inflate(R.layout.wallet_fragment,container,false);
         initView();
         return square;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initClick();

    }

    private void initView(){
        scanLinearLayout = (LinearLayout) square.findViewById(R.id.wallet_scan_code);
    }

    private void initClick(){
        scanLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
