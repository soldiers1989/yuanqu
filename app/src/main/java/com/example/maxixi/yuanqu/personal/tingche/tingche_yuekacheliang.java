package com.example.maxixi.yuanqu.personal.tingche;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.maxixi.yuanqu.R;
import com.example.maxixi.yuanqu.util.weixin.Constants;
import com.example.maxixi.yuanqu.util.weixin.MD5;
import com.example.maxixi.yuanqu.util.zhifubao.zhifubaolei;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class tingche_yuekacheliang extends AppCompatActivity {

    private TextView cartype;
    private TextView brand;
    private TextView model;
    private TextView license_plate;
    private TextView day;
    private TextView status;
    private TextView indatatime;
    private TextView time;
    private TextView carendindata;
    private String cid;
    private String uid;
    private AlertDialog dialog;
    private IWXAPI iwxapi;

    public final static int REQUEST_READ_PHONE_STATE = 1;
    private PayReq request;

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_PHONE_STATE:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    //TODO
                }
                break;

            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dctivity_tingche_yuekacheliang);

        //动态权限申请
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
        } else {
            //TODO
        }

        Intent intent = getIntent();
        cid = intent.getStringExtra("cid");

        SharedPreferences sharedPreferences = getSharedPreferences("userdata", MODE_PRIVATE);
        uid = sharedPreferences.getString("uid", "null");

        //view
        cartype = (TextView) findViewById(R.id.tingchejiaofei_yueka_cartype_text);
        brand = (TextView) findViewById(R.id.tingchejiaofei_yueka_brand_text);
        model = (TextView) findViewById(R.id.tingchejiaofei_yueka_model_text);
        license_plate = (TextView) findViewById(R.id.tingchejiaofei_yueka_license_plate_text);
        day = (TextView) findViewById(R.id.tingchejiaofei_yueka_day_text);
        status = (TextView) findViewById(R.id.tingchejiaofei_yueka_status_text);
        indatatime = (TextView) findViewById(R.id.tingchejiaofei_yueka_indatatime_text);
        time = (TextView) findViewById(R.id.tingchejiaofei_yueka_time_text);
        carendindata = (TextView) findViewById(R.id.tingchejiaofei_yueka_carendindata_text);

        Toolbar toolbar = (Toolbar) findViewById(R.id.personal_tingchejiaofei_yueka_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sendokhttp();

        Button yuekaxufei = (Button) findViewById(R.id.tingchejiaofei_yueka_yuekaxufei_button);
        yuekaxufei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zhifuDialog();
            }
        });
    }

    private void zhifuDialog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        dialog = builder.create();

        final View view = View.inflate(this, R.layout.dctivity_tingche_zhifu_item, null);
        dialog.setView(view, 0, 0, 0, 0);// 设置边距为0,保证在2.x的版本上运行没问题

        TextView weixin = (TextView) view.findViewById(R.id.zhifu_dialog_weixin_text);
        final TextView zhifubao = (TextView) view.findViewById(R.id.zhifu_dialog_zhifubao_text);

        weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Toast.makeText(tingche_yuekacheliang.this,"未开放",Toast.LENGTH_SHORT).show();
                //getoutoder();
                //getwxappid();
            }
        });
        zhifubao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zhifubaozhifu();
                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);//透明
        dialog.show();

    }

    private void sendokhttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                FormBody formBody = new FormBody.Builder().add("cid", cid).build();
                Request request = new Request.Builder().url(getString(R.string.cheliangtingcheshoufeixiangqing_url)).post(formBody).build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("-----", "错误" + e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseData = response.body().string();
                        try {
                            JSONObject jsonObject = new JSONObject(responseData);
                            final JSONObject jsonObjectcl = jsonObject.getJSONObject("data");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        cartype.setText(jsonObjectcl.getString("cartype"));
                                        brand.setText(jsonObjectcl.getString("brand"));
                                        model.setText(jsonObjectcl.getString("model"));
                                        license_plate.setText(jsonObjectcl.getString("license_plate"));
                                        day.setText(jsonObjectcl.getString("day"));
                                        String statusstr = "停车状态：" + jsonObjectcl.getString("status");
                                        status.setText(statusstr);
                                        if (status.getText().toString().contains("停车中"))
                                            status.setTextColor(Color.parseColor("#FFF13D46"));
                                        if (day.getText().toString().contains("0"))
                                            day.setTextColor(Color.parseColor("#FFF13D46"));
                                        indatatime.setText(jsonObjectcl.getString("InDateTime"));
                                        time.setText(jsonObjectcl.getString("time"));
                                        carendindata.setText(jsonObjectcl.getString("CarEndIndate"));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });
            }
        }).start();
    }

    private void zhifubaozhifu() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                FormBody formBody = new FormBody.Builder().add("uid", uid).add("carNo", String.valueOf(license_plate.getText())).add("money", "150").add("paytype", "支付宝").build();
                Request request = new Request.Builder().url(getString(R.string.yuekaxufei_url)).post(formBody).build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("错误", String.valueOf(e));
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseData = response.body().string();
                        try {
                            JSONObject jsonObject=new JSONObject(responseData);
                            final String outoder=jsonObject.getString("data");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    zhifubaolei zhifubaolei = new zhifubaolei(tingche_yuekacheliang.this, tingche_yuekacheliang.this, "150", "月卡费用",outoder,getString(R.string.yuekaxufei_url));
                                    zhifubaolei.payV2(getWindow().getDecorView());
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }).start();
    }














    private void updatajiaofei() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("uid", uid);
                    jsonObject.put("carNo", license_plate.getText().toString());
                    jsonObject.put("money", "150");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void getwxappid() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                FormBody formBody = new FormBody.Builder().add("money", "0.1").add("out_trade_no", "cd138").build();
                Request request = new Request.Builder().url(getString(R.string.weixinappid_url)).post(formBody).build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("错误：", String.valueOf(e));
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseData = response.body().string();
                        try {
                            JSONObject jsonObject = new JSONObject(responseData);
                            toWXPay(jsonObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }).start();
    }

    private void toWXPay(JSONObject jsonObject) throws JSONException {
        iwxapi = WXAPIFactory.createWXAPI(tingche_yuekacheliang.this,null); //初始化微信api
        iwxapi.registerApp(Constants.APP_ID); //注册appid  appid可以在开发平台获取


//        Runnable payRunnable = new Runnable() {
//            //这里注意要放在子线程
//            @Override
//            public void run() {
        //调起微信APP的对象
        request = new PayReq();
        //下面是设置必要的参数，也就是前面说的参数,这几个参数从何而来请看上面说明
        request.appId = jsonObject.getString("appid");
        request.nonceStr = jsonObject.getString("noncestr");
        request.packageValue = jsonObject.getString("package");
        request.partnerId = jsonObject.getString("partnerid");
        request.prepayId = jsonObject.getString("prepayid");
        request.timeStamp = jsonObject.getString("timestamp");

        //request.sign=jsonObject.getString("sign");
        request.sign = genPayReq();

        iwxapi.sendReq(request);//发送调起微信的请求

    }

    /**
     * 生成签名
     */

    // 246055aabecbfd2d48f61218e33f1d66


//        };
//        Thread payThread = new Thread(payRunnable);
//        payThread.start();
//    }
    private String genPayReq() {

        ContentValues contentValues = new ContentValues();
        contentValues.put("appid", request.appId);
        contentValues.put("noncestr", request.nonceStr);
        contentValues.put("package", request.packageValue);
        contentValues.put("partnerid", request.partnerId);
        contentValues.put("prepayid", request.prepayId);
        contentValues.put("timestamp", request.timeStamp);

        String sb = "appid=" + request.appId + "&noncestr=" + request.nonceStr + "&package=" + request.packageValue + "&partnerid=" + request.partnerId + "&prepayid=" + request.partnerId + "&timestamp=" + request.timeStamp+"&key="+Constants.API_KEY;
        String appSign = MD5.getMessageDigest(sb.getBytes());
        return appSign;

    }


}