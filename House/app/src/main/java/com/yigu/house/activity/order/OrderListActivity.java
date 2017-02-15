package com.yigu.house.activity.order;

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
import com.yigu.house.fragment.indent.IndentCompleteFrag;
import com.yigu.house.fragment.indent.IndentUnCompleteFrag;
import com.yigu.house.fragment.order.OrderPayCompleteFragment;
import com.yigu.house.fragment.order.OrderPayReceiveFragment;
import com.yigu.house.fragment.order.OrderPaySendFragment;
import com.yigu.house.fragment.order.OrderPayWaitFragment;

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
    OrderPayReceiveFragment orderPayReceiveFragment;
    OrderPayCompleteFragment orderPayCompleteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        back.setImageResource(R.mipmap.back);
        center.setText("我的订单");

        orderPayWaitFragment = new OrderPayWaitFragment();
        orderPaySendFragment = new OrderPaySendFragment();
        orderPayReceiveFragment = new OrderPayReceiveFragment();
        orderPayCompleteFragment = new OrderPayCompleteFragment();

        list.add(orderPayWaitFragment);
        list.add(orderPaySendFragment);
        list.add(orderPayReceiveFragment);
        list.add(orderPayCompleteFragment);

        list_title.add("待付款");
        list_title.add("待发货");
        list_title.add("待收货");
        list_title.add("已完成");

        tablayout.setTabMode(TabLayout.MODE_FIXED);
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tablayout.addTab(tablayout.newTab().setText(list_title.get(0)));
        tablayout.addTab(tablayout.newTab().setText(list_title.get(1)));
        tablayout.addTab(tablayout.newTab().setText(list_title.get(2)));
        tablayout.addTab(tablayout.newTab().setText(list_title.get(3)));

        mAdapter = new TabFragmentAdapter(getSupportFragmentManager(), list, list_title);
        viewpager.setAdapter(mAdapter);
        tablayout.setupWithViewPager(viewpager);
    }

    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }

}
