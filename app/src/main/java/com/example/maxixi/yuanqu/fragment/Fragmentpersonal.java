package com.example.maxixi.yuanqu.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.example.maxixi.yuanqu.R;


public class Fragmentpersonal extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle saveInstanceState){
        View view=inflater.inflate(R.layout.dctivity_personal,container,false);

        //toolbar_menu
        Toolbar toolbar=(Toolbar)view.findViewById(R.id.personal_toolbar);
        toolbar.inflateMenu(R.menu.personal_toolbar);

        return view;
    }
}