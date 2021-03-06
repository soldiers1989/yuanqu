package com.example.maxixi.yuanqu.wxapi;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.maxixi.yuanqu.R;
import com.example.maxixi.yuanqu.util.weixin.Constants;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {


	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	private static final String APP_ID = "wx3b685e9343096c49";


	private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        
    	api = WXAPIFactory.createWXAPI(this,APP_ID);
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);

		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("title");
			builder.setMessage("微信支付结果" +String.valueOf(resp.errCode));
			builder.show();
		}
//		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//			if (resp.errCode == 0) {
//				finish();
//			} else if (resp.errCode == -2) {
//				Toast.makeText(this, "您已取消付款!", Toast.LENGTH_SHORT).show();
//				finish();
//			} else {
//				Toast.makeText(this, "参数错误", Toast.LENGTH_SHORT).show();
//				finish();
//			}
//		} else {
//			finish();
//		}
	}
}

//public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
//
//	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
//
//	private IWXAPI api;
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
//		api.handleIntent(getIntent(), this);
//	}
//
//	@Override
//	protected void onNewIntent(Intent intent) {
//		super.onNewIntent(intent);
//		setIntent(intent);
//		api.handleIntent(intent, this);
//	}
//
//	@Override
//	public void onReq(BaseReq req) {
//	}
//
//	@Override
//	public void onResp(BaseResp resp) {
//		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//
//			if (resp.errCode == 0) {
//				finish();
//			} else if (resp.errCode == -2) {
//				Toast.makeText(this, "您已取消付款!", Toast.LENGTH_SHORT).show();
//				finish();
//			} else {
//				Toast.makeText(this, "参数错误", Toast.LENGTH_SHORT).show();
//				finish();
//			}
//		} else {
//			finish();
//		}
//	}
//}