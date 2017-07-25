package com.yigu.house.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yigu.commom.api.UserApi;
import com.yigu.commom.result.MapiResourceResult;
import com.yigu.commom.util.DebugLog;
import com.yigu.commom.util.RequestCallback;
import com.yigu.commom.util.RequestExceptionCallback;
import com.yigu.commom.util.SMSUtils;
import com.yigu.commom.util.StringUtil;
import com.yigu.commom.widget.MainToast;
import com.yigu.house.R;
import com.yigu.house.base.BaseActivity;
import com.yigu.house.receiver.SMSBroadcastReceiver;
import com.yigu.house.widget.ItemDialog;
import com.yigu.house.widget.PhotoDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonalRegisterActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.center)
    TextView center;
    @Bind(R.id.request_code)
    TextView requestCode;

    ItemDialog itemDialog;
    @Bind(R.id.type_tv)
    TextView typeTv;

    List<MapiResourceResult> mList;
    String business_type = "";
    @Bind(R.id.shop_name)
    EditText shopName;
    @Bind(R.id.user_name)
    EditText userName;
    @Bind(R.id.phone)
    EditText phone;
    @Bind(R.id.mark)
    EditText markEt;

    /**
     * 短信验证倒计时--时长
     */
    private int i = 60;
    // 读取短信广播
    private SMSBroadcastReceiver smsBroadcastReceiver;
    private static final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";
    SMSUtils.EventHandler eventHandler;

    PhotoDialog photoDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_register);
        ButterKnife.bind(this);
        initView();
        initListener();
        load();
    }

    private void initView() {
        back.setImageResource(R.mipmap.back);
        center.setText("个人申请");

        if (itemDialog == null)
            itemDialog = new ItemDialog(this, R.style.image_dialog_theme);

        mList = new ArrayList<>();

    }

    private void initListener() {

        itemDialog.setDialogItemClickListner(new ItemDialog.DialogItemClickListner() {
            @Override
            public void onItemClick(View view, int position) {
                typeTv.setText(mList.get(position).getTitle());
                business_type = mList.get(position).getId();
            }
        });
    }

    private void load() {
        showLoading();
        UserApi.businesslist(this, new RequestCallback<List<MapiResourceResult>>() {
            @Override
            public void success(List<MapiResourceResult> success) {
                hideLoading();
                if (success.isEmpty())
                    return;
                mList.addAll(success);
                itemDialog.setmList(mList);
            }
        }, new RequestExceptionCallback() {
            @Override
            public void error(Integer code, String message) {
                hideLoading();
                MainToast.showShortToast(message);
            }
        });
    }

    @OnClick({R.id.back, R.id.request_code, R.id.apply, R.id.reset, R.id.type_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.request_code:

                String phoneStr = phone.getText().toString();

                if(TextUtils.isEmpty(phoneStr)){
                    MainToast.showShortToast("请输入手机号");
                    return;
                }

                if(!StringUtil.isMobile(phoneStr)){
                    MainToast.showShortToast("手机号格式不正确！");
                    return;
                }

                requestCode();

                break;
            case R.id.apply:
                if(TextUtils.isEmpty(business_type)){
                    MainToast.showShortToast("请选择运营类型");
                    return;
                }
                if(TextUtils.isEmpty(shopName.getText().toString())){
                    MainToast.showShortToast("请输入店名");
                    return;
                }
                if(TextUtils.isEmpty(userName.getText().toString())){
                    MainToast.showShortToast("请输入姓名");
                    return;
                }
                if(TextUtils.isEmpty(phone.getText().toString())){
                    MainToast.showShortToast("请输入手机号");
                    return;
                }
                if(TextUtils.isEmpty(markEt.getText().toString())){
                    MainToast.showShortToast("请输入验证码");
                    return;
                }
                showLoading();
                UserApi.user_register(this, "1", business_type, shopName.getText().toString(), userName.getText().toString(), phone.getText().toString(), markEt.getText().toString(),
                        "", "", "", new RequestCallback() {
                            @Override
                            public void success(Object success) {
                                hideLoading();
                                MainToast.showShortToast("正在审核，请耐心等待...");
                                finish();
                            }
                        }, new RequestExceptionCallback() {
                            @Override
                            public void error(Integer code, String message) {
                                hideLoading();
                                MainToast.showShortToast(message);
                            }
                        });


                break;
            case R.id.reset:

                business_type = "";
                typeTv.setText("");
                shopName.setText("");
                userName.setText("");
                phone.setText("");
                markEt.setText("");

                break;
            case R.id.type_tv:
                itemDialog.showDialog();
                break;
        }
    }

    /**
     * 向服务器请求验证码
     */
    private void requestCode() {
        SMSUtils.requestCode(this,phone.getText().toString(),"1");
        // 把按钮变成不可点击，并且显示倒计时（正在获取）
        requestCode.setClickable(false);
        requestCode.setFocusableInTouchMode(false);
        requestCode.setBackgroundDrawable(getResources().getDrawable(R.drawable.rect_soild_dark_orange_round_15));
        requestCode.setText("重新发送(" + i + ")");
        initHandler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (; i > 0; i--) {
                    handler.sendEmptyMessage(-9);
                    if (i <= 0) {
                        i = 30;
                        break;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                handler.sendEmptyMessage(-8);
            }
        }).start();
    }

    private void initHandler(){
        eventHandler = new SMSUtils.EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                msg.what = -7;
                handler.sendMessage(msg);
            }
        };
        // 注册回调监听接口
        SMSUtils.registerEventHandler(eventHandler);
    }

    String mark = "";

    /**
     * 处理服务器返回的信息
     */
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case -9:
                    requestCode.setText("重新发送(" + i + ")");
                    break;
                case -8:
                    requestCode.setText("获取验证码");
                    requestCode.setClickable(true);
                    requestCode.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_pressed_round_15_color_orange));
                    i = 60;
                    break;
                case -7:
                    int event = msg.arg1;
                    int result = msg.arg2;
                    Object data = msg.obj;
                    DebugLog.e("event=" + event);
                    if (result == SMSUtils.RESULT_COMPLETE) {
                        if (event == SMSUtils.EVENT_GET_VERIFICATION_CODE) {
                            mark = (String) data;
                            MainToast.showShortToast("验证码已经发送");
                        }
                    }else if(result == SMSUtils.RESULT_ERROR){
                        if (event == SMSUtils.EVENT_GET_VERIFICATION_CODE_ERROR) {
                            MainToast.showShortToast((String) data);

                        }
                    }
                    break;
                default:
                    break;
            }

        }
    };

    @Override
    protected void onDestroy() {
        if(null!=eventHandler)
            SMSUtils.unregisterEventHandler(eventHandler);
//        if(null!=smsBroadcastReceiver)
//            unregisterReceiver(smsBroadcastReceiver);
        super.onDestroy();
    }

}
