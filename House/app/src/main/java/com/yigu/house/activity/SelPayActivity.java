package com.yigu.house.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.alipay.sdk.app.PayTask;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.yigu.commom.api.ItemApi;
import com.yigu.commom.application.AppContext;
import com.yigu.commom.result.MapiOrderResult;
import com.yigu.commom.util.DebugLog;
import com.yigu.commom.util.RequestCallback;
import com.yigu.commom.util.RequestExceptionCallback;
import com.yigu.commom.widget.MainToast;
import com.yigu.house.R;
import com.yigu.house.base.BaseActivity;
import com.yigu.house.broadcast.ReceiverAction;
import com.yigu.house.util.ControllerUtil;
import com.yigu.house.util.zhifubao.PayResult;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelPayActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.center)
    TextView center;
    @Bind(R.id.price_tv)
    TextView priceTv;
    @Bind(R.id.upload)
    TextView upload;
    @Bind(R.id.weixin_sel)
    ImageView weixinSel;
    @Bind(R.id.weixin_ll)
    LinearLayout weixinLl;
    @Bind(R.id.zhifubao_sel)
    ImageView zhifubaoSel;
    @Bind(R.id.zhifubao_ll)
    LinearLayout zhifubaoLl;

    MapiOrderResult mapiOrderResult;

    private int selPay = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sel_pay);
        ButterKnife.bind(this);
        if (null != getIntent())
            mapiOrderResult = (MapiOrderResult) getIntent().getSerializableExtra("item");
        if (null != mapiOrderResult) {
            ButterKnife.bind(this);
            initView();
            registerMessageReceiver();
        }
    }

    private void initView() {
        back.setImageResource(R.mipmap.back);
        center.setText("选择支付方式");

        priceTv.setText("订单总额：" + mapiOrderResult.getTotal_amount());

    }

    private void notifyPay(){
        if(selPay==1){
            weixinSel.setImageResource(R.mipmap.sel);
            zhifubaoSel.setImageResource(R.mipmap.sel_right);
        }else if(selPay==2){
            weixinSel.setImageResource(R.mipmap.sel_right);
            zhifubaoSel.setImageResource(R.mipmap.sel);
        }
    }

    @OnClick({R.id.back, R.id.upload,R.id.weixin_ll,R.id.zhifubao_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.zhifubao_ll:
                selPay = 1;
                notifyPay();
                break;
            case R.id.weixin_ll:
                selPay = 2;
                notifyPay();
                break;
            case R.id.upload:
                if(selPay<=0){
                    MainToast.showShortToast("请选择支付方式");
                    return;
                }
                showLoading();
                ItemApi.ordergetpaydata(this, mapiOrderResult.getOrder_id(), selPay + "", new RequestCallback<JSONObject>() {
                    @Override
                    public void success(JSONObject success) {
                        hideLoading();
                        String orderInfo  = success.getJSONObject("data").getString("order_string");
                        if(1==selPay)
                            callZhifubao(orderInfo);
                        else if(2==selPay)
                            callweixinPay(success);
                    }
                }, new RequestExceptionCallback() {
                    @Override
                    public void error(Integer code, String message) {
                        hideLoading();
                        MainToast.showShortToast(message);
                    }
                });
                break;
        }
    }

    private static final int SDK_PAY_FLAG = 1;
    private void callZhifubao(final String orderInfo){
/*try {
            *//**
         * 仅需对sign 做URL编码
         *//*
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/

        /**
         * 完整的符合支付宝参数规范的订单信息
         */
       /* final String payInfo = orderInfo + "&sign=\"" + sign ;*/

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(SelPayActivity.this);
                // 调用支付接口，获取支付结果
                Map<String, String> result = alipay.payV2(orderInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);


            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    Log.i("resultStatus",resultStatus);
                    Log.i("resultInfo",payResult.getResult());
                    Log.i("memo",payResult.getMemo());
                    if (TextUtils.equals(resultStatus, "9000")) {

                        Toast.makeText(SelPayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        DebugLog.i("支付宝支付成功");
                        ControllerUtil.go2OrderList(1);
                        finish();
//					Toast.makeText(PayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(SelPayActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();
                            ControllerUtil.go2OrderList(0);
                            finish();
                        }
                        else if(TextUtils.equals(resultStatus, "6002 ")){
                            Log.i("info","网络连接出错");
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(SelPayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                            ControllerUtil.go2OrderList(0);
                            finish();
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    /**
     * 微信支付
     */
    private void callweixinPay(JSONObject json){

        try {
            JSONObject jsonObject = json.getJSONObject("data").getJSONObject("wechat_pay");
            if(null != jsonObject){
                DebugLog.i("调微信接口");
                PayReq req = new PayReq();
                //req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
                req.appId			= jsonObject.getString("appid");
                req.partnerId		= jsonObject.getString("partnerid");
                req.prepayId		= jsonObject.getString("prepayid");
                req.nonceStr		= jsonObject.getString("noncestr");
                req.timeStamp		= jsonObject.getString("timestamp");
                req.packageValue	= jsonObject.getString("package");
                req.sign			= jsonObject.getString("sign");
                // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                DebugLog.i(req.toString());
                AppContext.getInstance().api.sendReq(req);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    //for receive customer msg from jpush server
    private WEIXINPAYReceiver mMessageReceiver;

    public void registerMessageReceiver() {
        mMessageReceiver = new WEIXINPAYReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(ReceiverAction.WEIXIN_PAY_ACTION);
        filter.addAction(ReceiverAction.WEIXIN_PAY_FAIL_ACTION);
        registerReceiver(mMessageReceiver, filter);
    }

    int mark = 0;

    public class WEIXINPAYReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(mark==0){
                if (ReceiverAction.WEIXIN_PAY_ACTION.equals(intent.getAction())) {
                    mark = 1;

                    Toast.makeText(SelPayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    ControllerUtil.go2OrderList(1);
                    DebugLog.i("微信支付成功");
                    finish();
                }else{
                    Toast.makeText(SelPayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    ControllerUtil.go2OrderList(0);
                    DebugLog.i("微信支付失败");
                    finish();
                }
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null!=mMessageReceiver)
            unregisterReceiver(mMessageReceiver);
    }

}
