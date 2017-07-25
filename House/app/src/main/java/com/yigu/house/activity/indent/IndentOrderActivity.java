package com.yigu.house.activity.indent;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.yigu.commom.api.ItemApi;
import com.yigu.commom.result.IndexData;
import com.yigu.commom.result.MapiCarResult;
import com.yigu.commom.result.MapiCartItemResult;
import com.yigu.commom.result.MapiOrderResult;
import com.yigu.commom.util.DebugLog;
import com.yigu.commom.util.RequestExceptionCallback;
import com.yigu.commom.util.RequestPageCallback;
import com.yigu.commom.widget.MainToast;
import com.yigu.house.R;
import com.yigu.house.adapter.indent.IndentCompleteAdapter;
import com.yigu.house.base.BaseActivity;
import com.yigu.house.widget.BestSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IndentOrderActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.center)
    TextView center;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.swipeRefreshLayout)
    BestSwipeRefreshLayout swipeRefreshLayout;


    private Integer pageIndex = 1;
    private Integer counts;

    List<MapiOrderResult> list;
    List<IndexData> mList;
    int count;
    IndentCompleteAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indent_order);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }

    private void initView() {

        back.setImageResource(R.mipmap.back);
        center.setText("下单");

        list = new ArrayList<>();
        mList = new ArrayList<>();
        pageIndex =1;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new IndentCompleteAdapter(this, mList);
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

        ItemApi.ordersamplelist(this, pageIndex + "", new RequestPageCallback<List<MapiOrderResult>>() {
            @Override
            public void success(Integer isNext, List<MapiOrderResult> success) {
                hideLoading();
                swipeRefreshLayout.setRefreshing(false);
                counts = isNext;
                if (success.isEmpty())
                    return;
                list.addAll(success);
                DebugLog.i("list.size()==>"+list.size());
                refreshItemList(list);
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

    private void refreshItemList(List<MapiOrderResult> data){

        mList.clear();
        int heatCount=0;
        count = 0;
        if (data == null || data.size() == 0) {
            count = 0;
        } else {
            for(MapiOrderResult orderResult : data){
                heatCount++;
                for (MapiCarResult ware : orderResult.getList()) {
                    mList.add(new IndexData(count++,"item", ware));
                }
                if (heatCount <= list.size() - 1)
                    mList.add(new IndexData(count++, "divider", new Object()));
            }

        }
        DebugLog.i("mList.size()==>"+mList.size());
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

    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }
}
