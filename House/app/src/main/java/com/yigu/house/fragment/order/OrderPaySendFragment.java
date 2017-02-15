package com.yigu.house.fragment.order;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yigu.commom.result.IndexData;
import com.yigu.commom.result.MapiCarResult;
import com.yigu.commom.result.MapiItemResult;
import com.yigu.commom.util.DPUtil;
import com.yigu.house.R;
import com.yigu.house.adapter.order.OrderListAdapter;
import com.yigu.house.base.BaseFrag;
import com.yigu.house.widget.BestSwipeRefreshLayout;
import com.yigu.house.widget.DividerListItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderPaySendFragment extends BaseFrag {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.swipeRefreshLayout)
    BestSwipeRefreshLayout swipeRefreshLayout;

    List<MapiCarResult> list = new ArrayList<>();
    List<IndexData> mList = new ArrayList<>();
    int count;

    OrderListAdapter mAdapter;

    public OrderPaySendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_order_pay_wait, container, false);
        ButterKnife.bind(this, view);
        initView();
        initListener();
        load();
        return view;
    }

    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.addItemDecoration(new DividerListItemDecoration(getActivity(), OrientationHelper.HORIZONTAL, DPUtil.dip2px(8), Color.parseColor("#f7f7f7")));
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new OrderListAdapter(getActivity(), mList);
        recyclerView.setAdapter(mAdapter);
    }

    private void initListener() {
        swipeRefreshLayout.setBestRefreshListener(new BestSwipeRefreshLayout.BestRefreshListener() {
            @Override
            public void onBestRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void load(){
        mList.clear();
        list.clear();
        MapiCarResult mapiCarResult = new MapiCarResult();
        MapiItemResult itemResult1 = new MapiItemResult();
        MapiItemResult itemResult2 = new MapiItemResult();
        MapiItemResult itemResult3 = new MapiItemResult();
        mapiCarResult.getItems().add(itemResult1);
        mapiCarResult.getItems().add(itemResult2);
        mapiCarResult.getItems().add(itemResult3);
        list.add(mapiCarResult);

        count = 0;
        if(list==null||list.size()==0){
            count = 0;
        }else{
            for (MapiCarResult ware : list) {
                mList.add(new IndexData(count++,"head", ware));
                for (int i=0;i<ware.getItems().size();i++) {
                    mList.add(new IndexData(count++,"item", ware.getItems().get(i)));
                    if(i<ware.getItems().size()-1)
                        mList.add(new IndexData(count++,"divider", new Object()));
                }
                mList.add(new IndexData(count++,"send_bottom", new Object()));
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
