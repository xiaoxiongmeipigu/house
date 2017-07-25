package com.yigu.house.activity.order;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.yigu.house.R;
import com.yigu.house.adapter.TabFragmentAdapter;
import com.yigu.house.base.BaseActivity;
import com.yigu.house.broadcast.LoginBroadcast;
import com.yigu.house.broadcast.ReceiverAction;
import com.yigu.house.fragment.indent.IndentCompleteFrag;
import com.yigu.house.fragment.indent.IndentUnCompleteFrag;
import com.yigu.house.fragment.order.OrderIndentFailFragment;
import com.yigu.house.fragment.order.OrderPayCompleteFragment;
import com.yigu.house.fragment.order.OrderPayReceiveFragment;
import com.yigu.house.fragment.order.OrderPaySendFragment;
import com.yigu.house.fragment.order.OrderPayWaitFragment;
import com.yigu.house.fragment.order.OrderSwapFailFragment;
import com.yigu.house.util.ControllerUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderListActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.center)
    TextView center;
    @Bind(R.id.tablayout)
    TabLayout tablayout;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    private List<String> list_title = new ArrayList<>();
    private List<Fragment> list = new ArrayList<>();
    TabFragmentAdapter mAdapter;

    OrderPayWaitFragment orderPayWaitFragment;
    OrderPaySendFragment orderPaySendFragment;
//    OrderPayReceiveFragment orderPayReceiveFragment;
    OrderPayCompleteFragment orderPayCompleteFragment;
    OrderIndentFailFragment orderIndentFailFragment;
    OrderSwapFailFragment orderSwapFailFragment;
    int fragIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        ButterKnife.bind(this);
        if(null!=getIntent()) {
            fragIndex = getIntent().getIntExtra("fragIndex",0);
        }
        initView();
        registerMessageReceiver();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        fragIndex = intent.getIntExtra("fragIndex",0);
        if(viewpager!=null){
            viewpager.setCurrentItem(fragIndex);
        }
    }

    private void initView(){
        back.setImageResource(R.mipmap.back);
        center.setText("我的订单");

        orderPayWaitFragment = new OrderPayWaitFragment();
        orderPaySendFragment = new OrderPaySendFragment();
//        orderPayReceiveFragment = new OrderPayReceiveFragment();
        orderPayCompleteFragment = new OrderPayCompleteFragment();
        orderIndentFailFragment = new OrderIndentFailFragment();
        orderSwapFailFragment = new OrderSwapFailFragment();

        list.add(orderPayWaitFragment);
        list.add(orderPaySendFragment);
//        list.add(orderPayReceiveFragment);
        list.add(orderPayCompleteFragment);
        list.add(orderIndentFailFragment);
        list.add(orderSwapFailFragment);

        list_title.add("待付款");
        list_title.add("待发货");
//        list_title.add("待收货");
        list_title.add("已完成");
        list_title.add("批量失败");
        list_title.add("互调失败");

        tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tablayout.addTab(tablayout.newTab().setText(list_title.get(0)));
        tablayout.addTab(tablayout.newTab().setText(list_title.get(1)));
//        tablayout.addTab(tablayout.newTab().setText(list_title.get(2)));
        tablayout.addTab(tablayout.newTab().setText(list_title.get(2)));
        tablayout.addTab(tablayout.newTab().setText(list_title.get(3)));
        tablayout.addTab(tablayout.newTab().setText(list_title.get(4)));
        mAdapter = new TabFragmentAdapter(getSupportFragmentManager(), list, list_title);
        viewpager.setAdapter(mAdapter);
        tablayout.setupWithViewPager(viewpager);

        viewpager.setCurrentItem(fragIndex);

    }

    public void registerMessageReceiver() {

        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(ReceiverAction.Order_SUCESS_ACTION);
        filter.addAction(ReceiverAction.Order_Indent_FAIL_ACTION);
        filter.addAction(ReceiverAction.Order_Swap_FAIL_ACTION);
        registerReceiver(mMessageReceiver, filter);
    }

    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }

    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;



    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (ReceiverAction.Order_SUCESS_ACTION.equals(intent.getAction())) {
                if(null!=orderPaySendFragment)
                    orderPaySendFragment.refreshData();
                if(null!=orderPayCompleteFragment)
                    orderPayCompleteFragment.refreshData();

            }else if(ReceiverAction.Order_Indent_FAIL_ACTION.equals(intent.getAction())){
                if(null!=orderPaySendFragment)
                    orderPaySendFragment.refreshData();
                if(null!=orderIndentFailFragment)
                    orderIndentFailFragment.refreshData();
            }else if(ReceiverAction.Order_Swap_FAIL_ACTION.equals(intent.getAction())){
                if(null!=orderPaySendFragment)
                    orderPaySendFragment.refreshData();
                if(null!=orderSwapFailFragment)
                    orderSwapFailFragment.refreshData();
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
