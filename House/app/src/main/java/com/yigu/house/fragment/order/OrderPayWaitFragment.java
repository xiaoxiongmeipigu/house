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

import com.yigu.commom.api.CommonApi;
import com.yigu.commom.api.ItemApi;
import com.yigu.commom.result.IndexData;
import com.yigu.commom.result.MapiCarResult;
import com.yigu.commom.result.MapiCartItemResult;
import com.yigu.commom.result.MapiItemResult;
import com.yigu.commom.result.MapiOrderResult;
import com.yigu.commom.util.DPUtil;
import com.yigu.commom.util.DebugLog;
import com.yigu.commom.util.RequestCallback;
import com.yigu.commom.util.RequestExceptionCallback;
import com.yigu.commom.util.RequestPageCallback;
import com.yigu.commom.widget.MainToast;
import com.yigu.house.R;
import com.yigu.house.adapter.indent.IndentCompleteAdapter;
import com.yigu.house.adapter.order.OrderListAdapter;
import com.yigu.house.base.BaseFrag;
import com.yigu.house.interfaces.OrderDeelListener;
import com.yigu.house.interfaces.RecyOnItemClickListener;
import com.yigu.house.util.ControllerUtil;
import com.yigu.house.widget.BestSwipeRefreshLayout;
import com.yigu.house.widget.DividerListItemDecoration;
import com.yigu.house.widget.MainAlertDialog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderPayWaitFragment extends BaseFrag {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.swipeRefreshLayout)
    BestSwipeRefreshLayout swipeRefreshLayout;

    List<MapiOrderResult> list;
    List<IndexData> mList;


    OrderListAdapter mAdapter;
    int count;
    private Integer pageIndex = 1;
    private Integer counts;

    /**
     * 是否创建
     */
    protected boolean isCreate = false;

    MainAlertDialog delDialog;

    int delPos = -1;

    public OrderPayWaitFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isCreate=true;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_order_pay_wait, container, false);
        ButterKnife.bind(this, view);
        initView();
        initListener();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        DebugLog.i("onResume");
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

        delDialog = new MainAlertDialog(getActivity());
        delDialog.setLeftBtnText("删除").setRightBtnText("取消").setTitle("确认删除？");
    }

    private void initListener() {

        delDialog.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(delPos>=0){
                    final MapiOrderResult mapiOrderResult = (MapiOrderResult) mList.get(delPos).getData();
                    if(null!=mapiOrderResult){
                        showLoading();
                        ItemApi.orderdelete(getActivity(), mapiOrderResult.getOrder_id(), new RequestCallback() {
                            @Override
                            public void success(Object success) {
                                delPos = -1;
                                delDialog.dismiss();
                                hideLoading();
                                MainToast.showShortToast("删除成功");
                                Iterator<MapiOrderResult> it =list.iterator();
                                while(it.hasNext()){
                                    MapiOrderResult result = it.next();
                                    if(mapiOrderResult.getOrder_id()==result.getOrder_id()){
                                        it.remove();
                                    }
                                }

                                refreshItemList(list);
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

        mAdapter.setOrderDeelListener(new OrderDeelListener() {
            @Override
            public void del(View view, int position) {

                delPos = position;
                delDialog.show();

            }

            @Override
            public void pay(View view, int position) {
                MapiOrderResult mapiOrderResult = (MapiOrderResult) mList.get(position).getData();
                if(null!=mapiOrderResult)
                    ControllerUtil.go2SelPay(mapiOrderResult);
            }
        });

        mAdapter.setRecyOnItemClickListener(new RecyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String orderId = "";
                if(mList.get(position).getData() instanceof MapiCarResult) {
                    MapiCarResult mapiCarResult = (MapiCarResult) mList.get(position).getData();
                    orderId = mapiCarResult.getOrder_id();
                }else if(mList.get(position).getData() instanceof MapiCartItemResult){
                    MapiCartItemResult mapiCartItemResult = (MapiCartItemResult) mList.get(position).getData();
                    orderId = mapiCartItemResult.getOrder_id();
                }
                ControllerUtil.go2OrderDetail(orderId);
            }
        });

    }

    public void load(){
        showLoading();
        ItemApi.orderList(getActivity(), pageIndex + "", "0", new RequestPageCallback<List<MapiOrderResult>>() {
            @Override
            public void success(Integer isNext,  List<MapiOrderResult> success) {
                hideLoading();
                if(null!=swipeRefreshLayout)
                    swipeRefreshLayout.setRefreshing(false);
                counts = isNext;
                if (success.isEmpty())
                    return;
                list.addAll(success);
                refreshItemList(list);
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

    private void refreshItemList(List<MapiOrderResult> data){

        mList.clear();
        int heatCount=0;
        count = 0;
        if (list == null || list.size() == 0) {
            count = 0;
        } else {
            for(MapiOrderResult orderResult : list){
                heatCount++;
                mList.add(new IndexData(count++, "head", orderResult));
                for (MapiCarResult ware : orderResult.getList()) {
                    ware.setOrder_id(orderResult.getOrder_id());
                    mList.add(new IndexData(count++, "banlance_head", ware));
                    for (int i = 0; i < ware.getList().size(); i++) {
                        MapiCartItemResult mapiCartItemResult = ware.getList().get(i);
                        mapiCartItemResult.setOrder_id(orderResult.getOrder_id());
                        if("1".equals(ware.getGoods_type())||"2".equals(ware.getGoods_type()))
                            mList.add(new IndexData(count++, "banlance_item",mapiCartItemResult));
                        else if("3".equals(ware.getGoods_type()))
                            mList.add(new IndexData(count++, "banlance_item_three",mapiCartItemResult));
                    }
                }
                mList.add(new IndexData(count++,"bottom",orderResult));
                if (heatCount <= list.size() - 1)
                    mList.add(new IndexData(count++, "divider", new Object()));
            }

        }
        mAdapter.notifyDataSetChanged();

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
