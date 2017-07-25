package com.yigu.house.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.yigu.commom.result.MapiAddrResult;
import com.yigu.commom.result.MapiCarResult;
import com.yigu.commom.result.MapiCartItemResult;
import com.yigu.commom.util.DebugLog;
import com.yigu.commom.util.RequestCallback;
import com.yigu.commom.util.RequestExceptionCallback;
import com.yigu.commom.widget.MainToast;
import com.yigu.house.R;
import com.yigu.house.activity.addr.AddrListActivity;
import com.yigu.house.base.BaseActivity;
import com.yigu.house.base.RequestCode;
import com.yigu.house.broadcast.ReceiverAction;
import com.yigu.house.util.ControllerUtil;
import com.yigu.house.util.zhifubao.PayResult;
import com.yigu.house.view.BalanceItemLayout;
import com.yigu.house.view.LogisticsLayout;

import java.util.ArrayList;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BalanceActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.center)
    TextView center;
    @Bind(R.id.addr_ll)
    LinearLayout addrLL;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.mobile)
    TextView mobile;
    @Bind(R.id.address)
    TextView address;
    @Bind(R.id.addr_info)
    LinearLayout addr_info;
    @Bind(R.id.default_tip)
    TextView default_tip;
    @Bind(R.id.balanceItemLayout)
    BalanceItemLayout balanceItemLayout;
    @Bind(R.id.logisticLayout)
    LogisticsLayout logisticLayout;
    @Bind(R.id.weixin_sel)
    ImageView weixinSel;
    @Bind(R.id.zhifubao_sel)
    ImageView zhifubaoSel;
    @Bind(R.id.price)
    TextView price;

    MapiAddrResult mapiAddrResult = null;

    ArrayList<MapiCartItemResult> itemList;
    ArrayList<MapiCarResult> carList;
    ArrayList<MapiCarResult> list;

    String eId = "";
    String selType = "";

    double allPrice = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        ButterKnife.bind(this);
        if (null != getIntent()) {
            itemList = (ArrayList<MapiCartItemResult>) getIntent().getSerializableExtra("itemList");
            carList = (ArrayList<MapiCarResult>) getIntent().getSerializableExtra("carList");
        }

        if (null != itemList && !itemList.isEmpty()) {
            initView();
            initItem();
            initListener();
            load();
            registerMessageReceiver();
        }

    }

    private void initView() {
        list = new ArrayList<>();
        back.setImageResource(R.mipmap.back);
        center.setText("结算");
        addrLL.setFocusable(true);
        addrLL.setFocusableInTouchMode(true);
        addrLL.requestFocus();
    }

    private void initItem() {
        for (MapiCarResult ware : carList)
        {

            MapiCarResult carResult = new MapiCarResult();
            carResult.setGoods_id(ware.getGoods_id());
            carResult.setImg(ware.getImg());
            carResult.setTitle(ware.getTitle());
            carResult.setGoods_type(ware.getGoods_type());
            boolean isSel = false;
            for (MapiCartItemResult cartItemResult : ware.getList()) {

                if (itemList.contains(cartItemResult)) {
                    isSel = true;
                    carResult.getList().add(cartItemResult);
                    String price = TextUtils.isEmpty(cartItemResult.getPrice())?"0":cartItemResult.getPrice();
                    double priceDouble = Double.parseDouble(price);
                    allPrice += priceDouble;
                }

            }
            if (isSel)
                list.add(carResult);
        }

        price.setText(allPrice+"");

        /*for (MapiCarResult carResult : list) {
            DebugLog.i("goods_id:" + carResult.getGoods_id() + " title:" + carResult.getTitle());
            for (MapiCartItemResult mapiCartItemResult : carResult.getList()) {
                DebugLog.i("颜色" + mapiCartItemResult.getColor() + " 尺寸" + mapiCartItemResult.getSize() + " 价格" + mapiCartItemResult.getPrice() + " 数量" + mapiCartItemResult.getCount());
            }
        }*/

        balanceItemLayout.load(list);
    }

    private void initListener() {
        logisticLayout.setOnSelExpressListener(new LogisticsLayout.OnSelExpressListener() {
            @Override
            public void selExpress(String id, String postage) {
                eId = id;

                double postDouble = Double.parseDouble((TextUtils.isEmpty(postage)?"0":postage));
                double newPrice = allPrice + postDouble;
                price.setText(newPrice+"");
            }

        });

    }

    private void load() {
        showLoading();
        ItemApi.default_address(this, new RequestCallback<MapiAddrResult>() {
            @Override
            public void success(MapiAddrResult success) {
                hideLoading();
                if (null != success) {
                    mapiAddrResult = success;
                }
                if (null != mapiAddrResult) {
                    logisticLayout.setMapiAddrResult(mapiAddrResult);
                    default_tip.setVisibility(View.GONE);
                    addr_info.setVisibility(View.VISIBLE);
                    name.setText("收货人：" + (TextUtils.isEmpty(mapiAddrResult.getName()) ? "" : mapiAddrResult.getName()));
                    mobile.setText("联系电话：" + (TextUtils.isEmpty(mapiAddrResult.getMobile()) ? "" : mapiAddrResult.getMobile()));
                    address.setText("收货地址：" + mapiAddrResult.getProvince() + " " + mapiAddrResult.getCity() + " " + mapiAddrResult.getArea() + " " + mapiAddrResult.getAddress());
                } else {
                    default_tip.setVisibility(View.VISIBLE);
                    addr_info.setVisibility(View.GONE);
                }
            }
        }, new RequestExceptionCallback() {
            @Override
            public void error(Integer code, String message) {
                hideLoading();
                MainToast.showShortToast(message);
            }
        });
    }

    @OnClick({R.id.back, R.id.addr_ll, R.id.pay,R.id.weixin_ll, R.id.zhifubao_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.addr_ll:
                Intent intent = new Intent(BalanceActivity.this, AddrListActivity.class);
                intent.putExtra("fromBalance", true);
                intent.putExtra("isShowDel", false);
                startActivityForResult(intent, RequestCode.sel_addr);
                break;
            case R.id.pay:
                if (null == mapiAddrResult) {
                    MainToast.showShortToast("请选择地址");
                    return;
                }
                if (TextUtils.isEmpty(eId)) {
                    MainToast.showShortToast("请选择物流");
                    return;
                }
                if (TextUtils.isEmpty(selType)) {
                    MainToast.showShortToast("请选择支付方式");
                    return;
                }
                uploadOrder();
                break;
            case R.id.weixin_ll:
                notifyPayStaus("2");
                break;
            case R.id.zhifubao_ll:
                notifyPayStaus("1");
                break;
        }
    }

    private void uploadOrder(){

        String ids = getCart_ids();
        showLoading();
        ItemApi.orderconfirmation(this, ids, eId, mapiAddrResult.getAddress_id(),selType, new RequestCallback<JSONObject>() {
            @Override
            public void success(JSONObject success) {
                hideLoading();
                try {
                    String orderInfo  = success.getJSONObject("data").getJSONObject("pay_data").getString("order_string");
                    if("1".equals(selType))
                        callZhifubao(orderInfo);
                    else if("2".equals(selType))
                        callweixinPay(success);
                }catch (Exception e){
                    MainToast.showShortToast("服务器繁忙");
                    finish();
                    e.printStackTrace();
                }
            }
        }, new RequestExceptionCallback() {
            @Override
            public void error(Integer code, String message) {
                hideLoading();
                MainToast.showShortToast(message);
            }
        });
    }

    private String getCart_ids(){
        String cart_ids = "";
        for(MapiCartItemResult mapiCartItemResult : itemList){
            if(TextUtils.isEmpty(cart_ids))
                cart_ids = mapiCartItemResult.getCar_id();
            else
                cart_ids += ","+mapiCartItemResult.getCar_id();
        }
        return cart_ids;
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
                PayTask alipay = new PayTask(BalanceActivity.this);
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

                        Toast.makeText(BalanceActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        DebugLog.i("支付宝支付成功");
                        ControllerUtil.go2OrderList(1);
                        finish();
//					Toast.makeText(PayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(BalanceActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();
                            ControllerUtil.go2OrderList(0);
                            finish();
                        }
                        else if(TextUtils.equals(resultStatus, "6002 ")){
                            Log.i("info","网络连接出错");
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(BalanceActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                            DebugLog.i("支付宝支付失败");
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

    private void notifyPayStaus(String type){
        if(type.equals("2")){
            weixinSel.setImageResource(R.mipmap.sel_right);
            zhifubaoSel.setImageResource(R.mipmap.sel);
            selType = "2";
        }else if(type.equals("1")){
            weixinSel.setImageResource(R.mipmap.sel);
            zhifubaoSel.setImageResource(R.mipmap.sel_right);
            selType = "1";
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (RESULT_OK == resultCode) {
            if (requestCode == RequestCode.sel_addr) {
                if (null != data) {
                    mapiAddrResult = (MapiAddrResult) data.getSerializableExtra("item");
                    DebugLog.i("mapiAddrResult==null===>" + (mapiAddrResult == null));
                    if (null != mapiAddrResult) {
                        logisticLayout.setMapiAddrResult(mapiAddrResult);
                        logisticLayout.refreshPrice(mapiAddrResult.getAddress_id());
                        default_tip.setVisibility(View.GONE);
                        addr_info.setVisibility(View.VISIBLE);
                        name.setText("收货人：" + (TextUtils.isEmpty(mapiAddrResult.getName()) ? "" : mapiAddrResult.getName()));
                        mobile.setText("联系电话：" + (TextUtils.isEmpty(mapiAddrResult.getMobile()) ? "" : mapiAddrResult.getMobile()));
                        address.setText("收货地址：" + mapiAddrResult.getProvince() + " " + mapiAddrResult.getCity() + " " + mapiAddrResult.getArea() + " " + mapiAddrResult.getAddress());
                    }else{

                    }

                }

            }
        }
    }

    /**
     * 微信支付
     */
    private void callweixinPay(JSONObject json){
        try{
            JSONObject jsonObject = json.getJSONObject("data").getJSONObject("pay_data").getJSONObject("wechat_pay");
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

                    Toast.makeText(BalanceActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    ControllerUtil.go2OrderList(1);
                    DebugLog.i("微信支付成功");
                    finish();
                }else{
                    Toast.makeText(BalanceActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
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
