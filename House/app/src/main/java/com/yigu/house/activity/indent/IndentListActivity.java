package com.yigu.house.activity.indent;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yigu.commom.api.ItemApi;
import com.yigu.commom.result.MapiItemResult;
import com.yigu.commom.util.DPUtil;
import com.yigu.commom.util.DebugLog;
import com.yigu.commom.util.RequestExceptionCallback;
import com.yigu.commom.util.RequestPageCallback;
import com.yigu.commom.widget.MainToast;
import com.yigu.commom.widget.circlelayoutmanager.CenterScrollListener;
import com.yigu.commom.widget.circlelayoutmanager.ScrollZoomLayoutManager;
import com.yigu.house.R;
import com.yigu.house.adapter.indent.IndentListAdapter;
import com.yigu.house.base.BaseActivity;
import com.yigu.house.interfaces.RecyOnItemClickListener;
import com.yigu.house.util.ControllerUtil;
import com.yigu.house.widget.BestSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IndentListActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.center)
    TextView center;
    @Bind(R.id.iv_right_two)
    ImageView ivRightTwo;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.swipeRefreshLayout)
    BestSwipeRefreshLayout swipeRefreshLayout;

    ScrollZoomLayoutManager scrollZoomLayoutManager;
    IndentListAdapter mAdapter;
    List<MapiItemResult> mList;

    private Integer pageIndex = 1;
    private Integer counts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indent_list);
        ButterKnife.bind(this);
        initView();
        initListener();
        load();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initView() {
        center.setText("下单");
        back.setImageResource(R.mipmap.back);
        ivRightTwo.setImageResource(R.mipmap.purcase_icon);

        mList = new ArrayList<>();
        scrollZoomLayoutManager = new ScrollZoomLayoutManager(this, DPUtil.dip2px(10));
        recyclerView.addOnScrollListener(new CenterScrollListener());
        recyclerView.setLayoutManager(scrollZoomLayoutManager);
        recyclerView.setHasFixedSize(true);
        mAdapter = new IndentListAdapter(this,mList);
        recyclerView.setAdapter(mAdapter);
    }

    private void initListener(){
        swipeRefreshLayout.setBestRefreshListener(new BestSwipeRefreshLayout.BestRefreshListener() {
            @Override
            public void onBestRefresh() {
                refreshData();
            }
        });
        mAdapter.setOnItemClickListener(new RecyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ControllerUtil.go2IndentDetail(mList.get(position).getGoods_id());
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                ScrollZoomLayoutManager manager = (ScrollZoomLayoutManager) recyclerView.getLayoutManager();

                if ((newState == RecyclerView.SCROLL_STATE_IDLE) && manager.getCurrentPosition() >= 0 && (manager.getCurrentPosition() == (manager.getItemCount() - 1))) {
                    loadNext();
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @OnClick({R.id.back, R.id.iv_right_two})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.iv_right_two:
                break;
        }
    }

    public void load() {
        showLoading();
        ItemApi.list(this, "3", "", "","", "", "", "", "", pageIndex + "","", new RequestPageCallback<List<MapiItemResult>>() {
            @Override
            public void success(Integer isNext, List<MapiItemResult> success) {
                hideLoading();
                swipeRefreshLayout.setRefreshing(false);
                counts = isNext;
                DebugLog.i("success.size=>"+success.size());
                if (success.isEmpty())
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

    private void loadNext() {
        if (counts == null || counts <= pageIndex) {
            MainToast.showShortToast("没有更多数据了");
            return;
        }
        pageIndex++;
        load();
    }

    public void refreshData() {
        if (null != mList) {
            mList.clear();
            pageIndex = 1;
            mAdapter.notifyDataSetChanged();
            load();
        }
    }

}
