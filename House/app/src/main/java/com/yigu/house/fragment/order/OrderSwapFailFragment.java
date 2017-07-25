package com.yigu.house.fragment.order;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yigu.commom.api.ItemApi;
import com.yigu.commom.result.IndexData;
import com.yigu.commom.result.MapiCarResult;
import com.yigu.commom.result.MapiOrderResult;
import com.yigu.commom.util.RequestExceptionCallback;
import com.yigu.commom.util.RequestPageCallback;
import com.yigu.commom.widget.MainToast;
import com.yigu.house.R;
import com.yigu.house.adapter.order.OrderListAdapter;
import com.yigu.house.base.BaseFrag;
import com.yigu.house.widget.BestSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderSwapFailFragment extends BaseFrag {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.swipeRefreshLayout)
    BestSwipeRefreshLayout swipeRefreshLayout;

    List<MapiOrderResult> list;
    List<IndexData> mList;
    int count;
    private Integer pageIndex = 1;
    private Integer counts;
    OrderListAdapter mAdapter;

    /**
     * 是否创建
     */
    protected boolean isCreate = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isCreate=true;
    }

    public OrderSwapFailFragment() {
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
//        refreshData();
        return view;
    }

    /*@Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isCreate) {
            //相当于Fragment的onResume
            //在这里处理加载数据等操作
            refreshData();
        } else {
            //相当于Fragment的onPause
        }
    }*/

    @Override
    public void onResume() {
        super.onResume();
        refreshData();
    }

    private void initView() {

        list = new ArrayList<>();
        mList = new ArrayList<>();
        pageIndex =1;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
//        recyclerView.addItemDecoration(new DividerListItemDecoration(getActivity(), OrientationHelper.HORIZONTAL, DPUtil.dip2px(8), Color.parseColor("#f7f7f7")));
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new OrderListAdapter(getActivity(), mList);
        recyclerView.setAdapter(mAdapter);
    }

    private void initListener() {
        swipeRefreshLayout.setBestRefreshListener(new BestSwipeRefreshLayout.BestRefreshListener() {
            @Override
            public void onBestRefresh() {
                refreshData();
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if ((newState == RecyclerView.SCROLL_STATE_IDLE) && manager.findLastVisibleItemPosition() >= 0 && (manager.findLastVisibleItemPosition() == (manager.getItemCount() - 1))) {
                    loadNext();
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    public void load(){

        showLoading();
        ItemApi.orderList(getActivity(), pageIndex + "", "4", new RequestPageCallback<List<MapiOrderResult>>() {
            @Override
            public void success(Integer isNext,  List<MapiOrderResult> success) {
                hideLoading();
                if(null!=swipeRefreshLayout)
                    swipeRefreshLayout.setRefreshing(false);
                counts = isNext;
                if (success.isEmpty())
                    return;
                list.addAll(success);
                int heatCount=0;
                count = 0;
                if (list == null || list.size() == 0) {
                    count = 0;
                } else {
                    for(MapiOrderResult orderResult : list){
                        heatCount++;
                        mList.add(new IndexData(count++, "head", orderResult));
                        for (MapiCarResult ware : orderResult.getList()) {
                            mList.add(new IndexData(count++, "banlance_head", ware));
                            for (int i = 0; i < ware.getList().size(); i++) {
                                if("1".equals(ware.getGoods_type())||"2".equals(ware.getGoods_type()))
                                    mList.add(new IndexData(count++, "banlance_item", ware.getList().get(i)));
                                else if("3".equals(ware.getGoods_type()))
                                    mList.add(new IndexData(count++, "banlance_item_three", ware.getList().get(i)));
                            }

                        }
                        mList.add(new IndexData(count++,"send_bottom",orderResult));
                        if (heatCount <= list.size() - 1)
                            mList.add(new IndexData(count++, "divider", new Object()));
                    }

                }
                mAdapter.notifyDataSetChanged();
            }
        }, new RequestExceptionCallback() {
            @Override
            public void error(Integer code, String message) {
                hideLoading();
                if(null!=swipeRefreshLayout)
                    swipeRefreshLayout.setRefreshing(false);
                MainToast.showShortToast(message);
            }
        });
    }

    private void loadNext() {
        if (counts == null || counts <= mList.size()) {
            MainToast.showShortToast("没有更多数据了");
            return;
        }
        pageIndex++;
        load();
    }

    public void refreshData() {
        if (null != mList) {
            list.clear();
            mList.clear();
            pageIndex = 1;
            mAdapter.notifyDataSetChanged();
            load();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
