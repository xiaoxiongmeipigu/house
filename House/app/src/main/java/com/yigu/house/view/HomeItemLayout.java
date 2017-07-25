package com.yigu.house.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.yigu.commom.application.AppContext;
import com.yigu.commom.result.MapiItemResult;
import com.yigu.commom.util.DPUtil;
import com.yigu.commom.widget.MainToast;
import com.yigu.house.R;
import com.yigu.house.activity.prompt.PromptListActivity;
import com.yigu.house.adapter.ItemAdapter;
import com.yigu.house.adapter.ItemGridAdapter;
import com.yigu.house.base.BaseActivity;
import com.yigu.house.interfaces.RecyOnItemClickListener;
import com.yigu.house.util.ControllerUtil;
import com.yigu.house.widget.DividerListItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by brain on 2016/9/23.
 */
public class HomeItemLayout extends RelativeLayout {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private Context mContext;
    private View view;
    ItemGridAdapter mAdapter;
    List<MapiItemResult> mList = new ArrayList<>();

    public HomeItemLayout(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public HomeItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public HomeItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        if (isInEditMode())
            return;
        view = LayoutInflater.from(mContext).inflate(R.layout.layout_home_item, this);
        ButterKnife.bind(this, view);

        GridLayoutManager manager = new GridLayoutManager(mContext, 2);
        recyclerView.setLayoutManager(manager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerListItemDecoration(mContext, OrientationHelper.VERTICAL, DPUtil.dip2px(4), getResources().getColor(R.color.background_gray)));
        mAdapter = new ItemGridAdapter(mContext, mList);
        recyclerView.setAdapter(mAdapter);

        initListener();
    }

    private void initListener() {
        mAdapter.setOnItemClickListener(new RecyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ControllerUtil.go2PromptDetail(mList.get(position).getGoods_id());
            }
        });

    }

    public void load(List<MapiItemResult> list) {
        refreshData();
        initData(list);
    }

    private void initData(List<MapiItemResult> list) {
        if(null==list||list.isEmpty())
            return;
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }


    public void refreshData() {
        if (null != mList) {
            mList.clear();
            mAdapter.notifyDataSetChanged();
        }
    }


    @OnClick(R.id.ll_more)
    public void onClick() {
        Intent intent = new Intent(AppContext.getInstance(), PromptListActivity.class);
        intent.putExtra("type","push");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }
}
