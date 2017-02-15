package com.yigu.house.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.yigu.commom.result.IndexData;
import com.yigu.commom.result.MapiItemResult;
import com.yigu.house.R;
import com.yigu.house.adapter.logistics.LogisticsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2017/1/9.
 */
public class LogisticsLayout extends LinearLayout {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private Context mContext;
    private View view;

    List<MapiItemResult> list = new ArrayList<>();
    List<IndexData> mList = new ArrayList<>();
    int count = 0;

    LogisticsAdapter mAdapter;

    public LogisticsLayout(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public LogisticsLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public LogisticsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        if (isInEditMode())
            return;
        view = LayoutInflater.from(mContext).inflate(R.layout.layout_logistics, this);
        ButterKnife.bind(this, view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new LogisticsAdapter(mContext, mList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        load();
        initListener();
    }

    public void load() {
        mList.clear();
        list.clear();
        MapiItemResult itemResult = new MapiItemResult();
        MapiItemResult itemResult2 = new MapiItemResult();
        itemResult.setName("顺丰");
        itemResult.setPrice("20");

        itemResult2.setName("圆通");
        itemResult2.setPrice("20");

        list.add(itemResult);
        list.add(itemResult2);
        count = 0;
        if (list == null || list.size() == 0) {
            count = 0;
        } else {
            for (MapiItemResult ware : list) {
                mList.add(new IndexData(count++, "item", ware));
                mList.add(new IndexData(count++, "divider", new Object()));
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    private void initListener() {

    }

}
