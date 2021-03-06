package com.yigu.house.wxapi;

import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yigu.commom.application.AppContext;
import com.yigu.commom.util.DebugLog;
import com.yigu.commom.widget.MainToast;
import com.yigu.house.base.BaseActivity;
import com.yigu.house.broadcast.ReceiverAction;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler{
	
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.pay_result);
		AppContext.getInstance().api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		AppContext.getInstance().api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {

		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			/*AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(R.string.app_tip);
			builder.setMessage(getString(R.string.pay_result_callback_msg, String.valueOf(resp.errCode)));
			builder.show();*/
			if(resp.errCode == 0){
				Intent msgIntent = new Intent(ReceiverAction.WEIXIN_PAY_ACTION);
				sendBroadcast(msgIntent);
//				startActivity(new Intent(WXPayEntryActivity.this, OrderCompleteActivity.class));
			}else{
				DebugLog.i("1111111111111");
				Intent msgIntent = new Intent(ReceiverAction.WEIXIN_PAY_FAIL_ACTION);
				sendBroadcast(msgIntent);
			}

		}else{
			DebugLog.i("222222222222");
			Intent msgIntent = new Intent(ReceiverAction.WEIXIN_PAY_FAIL_ACTION);
			sendBroadcast(msgIntent);
		}
		finish();
	}
}