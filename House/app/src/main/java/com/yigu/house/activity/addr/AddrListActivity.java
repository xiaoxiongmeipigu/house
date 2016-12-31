package com.yigu.house.activity.addr;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yigu.commom.result.MapiAddrResult;
import com.yigu.house.R;
import com.yigu.house.adapter.ItemAdapter;
import com.yigu.house.adapter.addr.AddrListAdapter;
import com.yigu.house.base.BaseActivity;
import com.yigu.house.util.ControllerUtil;
import com.yigu.house.widget.BestSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddrListActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.center)
    TextView center;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.swipeRefreshLayout)
    BestSwipeRefreshLayout swipeRefreshLayout;

    AddrListAdapter mAdapter;
    private List<MapiAddrResult> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addr_list);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initView() {

        back.setImageResource(R.mipmap.back);
        center.setText("收货地址");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new AddrListAdapter(this, mList);
        recyclerView.setAdapter(mAdapter);

    }

    private void initListener(){

        swipeRefreshLayout.setBestRefreshListener(new BestSwipeRefreshLayout.BestRefreshListener() {
            @Override
            public void onBestRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });



    }

    @OnClick({R.id.back, R.id.bottom_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.bottom_layout:
                ControllerUtil.go2AddAddr();
                break;
        }
    }
}
