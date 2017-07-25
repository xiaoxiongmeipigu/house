package com.yigu.house.activity.addr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yigu.commom.api.CommonApi;
import com.yigu.commom.application.AppContext;
import com.yigu.commom.result.MapiAddrResult;
import com.yigu.commom.util.RequestCallback;
import com.yigu.commom.util.RequestExceptionCallback;
import com.yigu.commom.widget.MainToast;
import com.yigu.house.R;
import com.yigu.house.activity.MainActivity;
import com.yigu.house.adapter.ItemAdapter;
import com.yigu.house.adapter.addr.AddrListAdapter;
import com.yigu.house.base.BaseActivity;
import com.yigu.house.base.RequestCode;
import com.yigu.house.interfaces.RecyOnItemClickListener;
import com.yigu.house.util.ControllerUtil;
import com.yigu.house.util.JpushUtil;
import com.yigu.house.widget.BestSwipeRefreshLayout;
import com.yigu.house.widget.MainAlertDialog;

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

    private boolean fromBalance = false;

    MainAlertDialog delDialog;

    int delPos = -1;

    boolean isShowDel = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addr_list);
        ButterKnife.bind(this);
        initView();
        initListener();
        load();
    }

    private void initView() {

        back.setImageResource(R.mipmap.back);
        center.setText("收货地址");

        if(null!=getIntent()) {
            fromBalance = getIntent().getBooleanExtra("fromBalance", false);
            isShowDel = getIntent().getBooleanExtra("isShowDel", true);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new AddrListAdapter(this, mList);
        mAdapter.setFromBalance(fromBalance);
        mAdapter.setShowDel(isShowDel);
        recyclerView.setAdapter(mAdapter);

        delDialog = new MainAlertDialog(this);
        delDialog.setLeftBtnText("删除").setRightBtnText("取消").setTitle("确认删除？");

    }

    private void initListener(){

        delDialog.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(delPos>=0){
                    showLoading();
                    CommonApi.delAddr(AddrListActivity.this, mList.get(delPos).getAddress_id(), new RequestCallback() {
                        @Override
                        public void success(Object success) {
                            delDialog.dismiss();
                            hideLoading();
                            mList.remove(delPos);
                            delPos = -1;
                            mAdapter.notifyDataSetChanged();
                        }
                    }, new RequestExceptionCallback() {
                        @Override
                        public void error(Integer code, String message) {
                            delPos = -1;
                            delDialog.dismiss();
                            hideLoading();
                            MainToast.showShortToast(message);
                        }
                    });
                }
            }
        });

        delDialog.setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delDialog.dismiss();
            }
        });

        swipeRefreshLayout.setBestRefreshListener(new BestSwipeRefreshLayout.BestRefreshListener() {
            @Override
            public void onBestRefresh() {
                refreshData();
            }
        });

        mAdapter.setAddrListener(new AddrListAdapter.AddrListener() {
            @Override
            public void del(final int postion) {
                delPos = postion;
                delDialog.show();
            }

            @Override
            public void edit(int postion) {
                Intent intent = new Intent(AddrListActivity.this,EditAddrActivity.class);
                intent.putExtra("item",mList.get(postion));
                startActivityForResult(intent, RequestCode.add_addr);
            }

            @Override
            public void defaultAddr(final int postion) {
                showLoading();
                CommonApi.setAddressDefault(AddrListActivity.this, mList.get(postion).getAddress_id(), new RequestCallback() {
                    @Override
                    public void success(Object success) {
                        hideLoading();
                        for(int pos=0;pos<mList.size();pos++){
                            if(pos==postion)
                                mList.get(pos).setIs_default("1");
                            else
                                mList.get(pos).setIs_default("0");
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                }, new RequestExceptionCallback() {
                    @Override
                    public void error(Integer code, String message) {
                        hideLoading();
                        MainToast.showShortToast(message);
                    }
                });
            }
        });

        mAdapter.setOnItemClickListener(new RecyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                MapiAddrResult mapiAddrResult = mList.get(position);
                Intent intent = new Intent();
                intent.putExtra("item",mapiAddrResult);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }

    private void load(){
        showLoading();
        CommonApi.addresslist(this, new RequestCallback<List<MapiAddrResult>>() {
            @Override
            public void success(List<MapiAddrResult> success) {
                hideLoading();
                swipeRefreshLayout.setRefreshing(false);
                if(success.isEmpty())
                    return;
                mList.addAll(success);
                mAdapter.notifyDataSetChanged();
            }
        }, new RequestExceptionCallback() {
            @Override
            public void error(Integer code, String message) {
                hideLoading();
                swipeRefreshLayout.setRefreshing(false);
                MainToast.showShortToast(message);
            }
        });
    }

    private void refreshData(){
        mList.clear();
        mAdapter.notifyDataSetChanged();
        load();
    }

    @OnClick({R.id.back, R.id.bottom_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.bottom_layout:
                Intent intent = new Intent(this,AddAddrActivity.class);
                startActivityForResult(intent, RequestCode.add_addr);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case RequestCode.add_addr:
                    refreshData();
                    break;
            }
        }
    }

}
