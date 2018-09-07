package com.example.maxixi.yuanqu.fragment;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.maxixi.yuanqu.diancan.diancan;
import com.example.maxixi.yuanqu.perkservice.Parkservice_yuannei;
import com.example.maxixi.yuanqu.R;
import com.example.maxixi.yuanqu.personal.tingche.tingchejilu;

public class Fragmentpark extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        final View view = inflater.inflate(R.layout.bctivity_parkservice, container, false);

        ImageButton imageButton = (ImageButton) view.findViewById(R.id.parkservice_yuannei);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Parkservice_yuannei.class);
                startActivity(intent);
            }
        });

        ImageButton tingchejilu = (ImageButton) view.findViewById(R.id.parkservice_tingchejiaofei);
        tingchejilu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),tingchejilu.class);
                startActivity(intent);
            }
        });

        final ImageButton yuanneidiancan=(ImageButton)view.findViewById(R.id.parkservice_yuanneidiancan);
        yuanneidiancan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),diancan.class);
                startActivity(intent);

            }
        });

        return view;
    }
}
