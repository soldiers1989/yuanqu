package com.example.maxixi.yuanqu.cloud.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.maxixi.yuanqu.R;
import com.example.maxixi.yuanqu.cloud.cloud_adapter.cloud_zhidao_adapter;
import com.example.maxixi.yuanqu.cloud.cloud_adapter.cloud_zhidao_lei;
import com.example.maxixi.yuanqu.cloud.cloud_jingrong_jinrong;
import com.example.maxixi.yuanqu.cloud.cloud_zhengfu;
import com.example.maxixi.yuanqu.cloud.cloud_zhengfu_zhengfu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Fragmentjingrongfengxian extends Fragment {

    private RecyclerView recyclerView;
    private List<cloud_zhidao_lei> zhidaoList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.cctivity_cloud_jinrong_fengxianfg, container, false);


        recyclerView = (RecyclerView) view.findViewById(R.id.cloud_fengxian_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        sendRequestWithOkHttp(0);


        return view;
    }

    private int code;
    private int page = 0;

    private void sendRequestWithOkHttp(final int page_value) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    FormBody formBody = new FormBody.Builder().add("type", "1").add("page", String.valueOf(page)).build();
                    Request request = new Request.Builder().url(getString(R.string.jinrongfuwu_url)).post(formBody).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(responseData);
                        code = jsonObject.getInt("code");
                        JSONArray array = jsonObject.getJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jsonObjectchil = array.getJSONObject(i);
                            cloud_zhidao_lei madada = new cloud_zhidao_lei(jsonObjectchil.getString("title"), jsonObjectchil.getString("ctime"),jsonObjectchil.getString("lid"));
                            zhidaoList.add(madada);
                            final cloud_zhidao_adapter cloud_zhidao_adapter = new cloud_zhidao_adapter(zhidaoList);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    recyclerView.setAdapter(cloud_zhidao_adapter);
                                    cloud_zhidao_adapter.setOnItemClickListener(new cloud_zhidao_adapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, int position) {
                                            Intent intent=new Intent(getContext(),cloud_jingrong_jinrong.class);
                                            intent.putExtra("lid",zhidaoList.get(position).getLid());
                                            startActivity(intent);
                                        }
                                    });
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (code != -1 && code != 201) {
                        sendRequestWithOkHttp(++page);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}