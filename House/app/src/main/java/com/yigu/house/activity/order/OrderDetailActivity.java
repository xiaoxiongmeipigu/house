package com.yigu.house.activity.order;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.yigu.commom.api.ItemApi;
import com.yigu.commom.result.IndexData;
import com.yigu.commom.result.MapiCarResult;
import com.yigu.commom.result.MapiOrderDetailResult;
import com.yigu.commom.result.MapiOrderResult;
import com.yigu.commom.util.DebugLog;
import com.yigu.commom.util.RequestCallback;
import com.yigu.commom.util.RequestExceptionCallback;
import com.yigu.commom.widget.MainToast;
import com.yigu.house.R;
import com.yigu.house.adapter.order.OrderDetailAdapter;
import com.yigu.house.adapter.order.OrderListAdapter;
import com.yigu.house.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderDetailActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.center)
    TextView center;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    String order_id = "";

    List<IndexData> mList;
    int count;

    OrderDetailAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        if(null!=getIntent()) {
            order_id = getIntent().getStringExtra("order_id");
        }
        if(!TextUtils.isEmpty(order_id)){
            initView();
            load();
        }

    }

    private void initView() {

        mList = new ArrayList<>();

        back.setImageResource(R.mipmap.back);
        center.setText("订单");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
//        recyclerView.addItemDecoration(new DividerListItemDecoration(getActivity(), OrientationHelper.HORIZONTAL, DPUtil.dip2px(8), Color.parseColor("#f7f7f7")));
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new OrderDetailAdapter(this, mList);
        recyclerView.setAdapter(mAdapter);

    }

    private void load(){

        showLoading();
        ItemApi.orderdetail(this, order_id, new RequestCallback<MapiOrderDetailResult>() {
            @Override
            public void success(MapiOrderDetailResult success) {
                hideLoading();
                if(null==success) {
                    MainToast.showShortToast("null");
                    return;
                }
                mList.add(new IndexData(count++, "head",success));
                if(null!=success.getGoods_list()){
                    refreshItemList(success.getGoods_list());
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
    private void refreshItemList(List<MapiCarResult> data){

        int heatCount=0;
        if (data == null || data.size() == 0) {
            count = 1;
        } else {
            for (MapiCarResult ware : data) {
                heatCount++;
                mList.add(new IndexData(count++, "banlance_head", ware));
                for (int i = 0; i < ware.getList().size(); i++) {
                    DebugLog.i("type:"+ware.getGoods_type());
                    if("1".equals(ware.getGoods_type())||"2".equals(ware.getGoods_type()))
                        mList.add(new IndexData(count++, "banlance_item", ware.getList().get(i)));
                    else if("3".equals(ware.getGoods_type()))
                        mList.add(new IndexData(count++, "banlance_item_three", ware.getList().get(i)));
                }
                if (heatCount <= data.size() - 1)
                    mList.add(new IndexData(count++, "divider", new Object()));
            }

        }

        mAdapter.notifyDataSetChanged();

    }

    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }
}
