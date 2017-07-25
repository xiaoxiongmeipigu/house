package com.yigu.house.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.yigu.commom.application.AppContext;
import com.yigu.commom.result.IndexData;
import com.yigu.commom.result.MapiResourceResult;
import com.yigu.commom.result.MapiUserResult;
import com.yigu.commom.util.DebugLog;
import com.yigu.commom.widget.MainToast;
import com.yigu.house.R;
import com.yigu.house.adapter.MainAdapter;
import com.yigu.house.base.BaseActivity;
import com.yigu.house.base.BaseFrag;
import com.yigu.house.broadcast.LoginBroadcast;
import com.yigu.house.broadcast.ReceiverAction;
import com.yigu.house.fragment.home.HomeFragment;
import com.yigu.house.fragment.person.PersonFragment;
import com.yigu.house.fragment.purcase.PurcaseFragment;
import com.yigu.house.util.ControllerUtil;
import com.yigu.house.util.JpushUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

public class MainActivity extends BaseActivity {

    @Bind(R.id.radio_home)
    RadioButton radioHome;
    @Bind(R.id.radio_order)
    RadioButton radioOrder;
    @Bind(R.id.radio_person)
    RadioButton radioPerson;
    @Bind(R.id.content)
    FrameLayout content;

    private BaseFrag[] fragments;
    private int index = 0;
    private long exitTime = 0;

    private RadioButton[] buttons;

    LoginBroadcast loginBroadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!userSP.checkLogin()){
            ControllerUtil.go2Login();
            finish();
        }else{
            setContentView(R.layout.activity_main);
            ButterKnife.bind(this);
            initView();
            if (userSP.getAlias()) {
                userSP.setAlias(false);
                JpushUtil.getInstance().setAlias("");
                JpushUtil.getInstance().stopPush(AppContext.getInstance());
            }
            JpushUtil.getInstance().verifyInit(this);
            DebugLog.i(JPushInterface.isPushStopped(AppContext.getInstance())+":服务状态");
            if (JPushInterface.isPushStopped(AppContext.getInstance())) {
                JPushInterface.resumePush(AppContext.getInstance());
            }

            DebugLog.i(JPushInterface.getConnectionState(AppContext.getInstance())+":连接状态");

            if (!userSP.getAlias()) {
                DebugLog.i("getAlias===>false");
                JpushUtil.getInstance().setAlias(userSP.getUserBean().getUser_id());
            }

            /*if(!JPushInterface.getConnectionState(AppContext.getInstance())){
                JpushUtil.getInstance().verifyInit(this);
            }

            DebugLog.i(JPushInterface.getConnectionState(AppContext.getInstance())+":连接状态");*/
            registerMessageReceiver();
        }
    }

    private void initView() {
        fragments = new BaseFrag[3];
        fragments[0] = new HomeFragment();
        fragments[1] = new PurcaseFragment();
        fragments[2] = new PersonFragment();

        buttons = new RadioButton[3];
        buttons[0] = radioHome;
        buttons[1] = radioOrder;
        buttons[2] = radioPerson;

        selectTab();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int type = intent.getIntExtra("type",0);
        if(type==3){
            ControllerUtil.go2Login();
            finish();
        }
    }

    private void selectTab() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for (BaseFrag frag : fragments) {
            if (!frag.isAdded())
                transaction.add(R.id.content, frag);
            transaction.hide(frag);
        }
        transaction.show(fragments[index]);
        transaction.commitAllowingStateLoss();
    }

    @OnClick({R.id.radio_home, R.id.radio_order, R.id.radio_person})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.radio_home:
                index = 0;
                selectTab();
                break;
            case R.id.radio_order:
                index = 1;
                selectTab();
//                fragments[index].refreshData();
                break;
            case R.id.radio_person:
                index = 2;
                selectTab();
                fragments[index].load();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出应用", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void registerMessageReceiver() {

        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(ReceiverAction.MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);

        loginBroadcast = new LoginBroadcast();
        IntentFilter filter2 = new IntentFilter();
        filter2.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter2.addAction(ReceiverAction.LOGIN_ACTION);
        registerReceiver(loginBroadcast, filter2);
    }

    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;



    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (ReceiverAction.MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(JpushUtil.KEY_MESSAGE);
                /*String extras = intent.getStringExtra(JpushUtil.KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(JpushUtil.KEY_MESSAGE + " : " + messge + "\n");
                if (!JpushUtil.getInstance().isEmpty(extras)) {
                    showMsg.append(JpushUtil.KEY_EXTRAS + " : " + extras + "\n");
                }*/
                DebugLog.i(messge);
                if(!TextUtils.isEmpty(messge)){
                    JSONObject jsonObject = JSONObject.parseObject(messge);

                    String type = jsonObject.getString("type");
                    String result = jsonObject.getString("result");

                    if(("1".equals(type)&&"1".equals(result))||("2".equals(type)&&"1".equals(result))||"4".equals(type)){//成功
                        Intent foodIntent = new Intent(ReceiverAction.Order_SUCESS_ACTION);
                        sendBroadcast(foodIntent);
                    }else if("1".equals(type)&&"2".equals(result)){//批量失败
                        Intent foodIntent = new Intent(ReceiverAction.Order_Indent_FAIL_ACTION);
                        sendBroadcast(foodIntent);
                    }else if("2".equals(type)&&"2".equals(result)){//互调失败
                        Intent foodIntent = new Intent(ReceiverAction.Order_Swap_FAIL_ACTION);
                        sendBroadcast(foodIntent);
                    }else if("3".equals(type)&&"1".equals(result)||"3".equals(type)&&"3".equals(result)){//VIP
                        MapiUserResult mapiUserResult = userSP.getUserBean();
                        mapiUserResult.setIs_vip("1");
                        userSP.saveUserBean(mapiUserResult);
                        if(null!=fragments){
                            PersonFragment personFragments = (PersonFragment) fragments[2];
                            personFragments.notifyVip();
                        }
                    }else if("3".equals(type)&&"2".equals(result)||"3".equals(type)&&"4".equals(result)){//取消VIP
                        MapiUserResult mapiUserResult = userSP.getUserBean();
                        mapiUserResult.setIs_vip("0");
                        userSP.saveUserBean(mapiUserResult);
                        if(null!=fragments){
                            PersonFragment personFragments = (PersonFragment) fragments[2];
                            personFragments.notifyVip();
                        }
                    }

                }

            }
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null!=loginBroadcast)
            unregisterReceiver(loginBroadcast);
        if(null!=mMessageReceiver)
            unregisterReceiver(mMessageReceiver);
    }
}