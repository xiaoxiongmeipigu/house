package com.yigu.house.fragment.prompt;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.yigu.commom.result.MapiItemResult;
import com.yigu.commom.util.DPUtil;
import com.yigu.commom.widget.MainToast;
import com.yigu.house.R;
import com.yigu.house.adapter.ItemAdapter;
import com.yigu.house.adapter.ItemGridAdapter;
import com.yigu.house.adapter.MainToolAdapter;
import com.yigu.house.base.BaseFrag;
import com.yigu.house.interfaces.RecyOnItemClickListener;
import com.yigu.house.util.ControllerUtil;
import com.yigu.house.widget.DividerListItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class FrompComFragment extends BaseFrag {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.gridRecyclerView)
    RecyclerView gridRecyclerView;

    ItemAdapter mAdapter;
    ItemGridAdapter itemGridAdapter;
    List<MapiItemResult> mList = new ArrayList<>();

    private Integer pageIndex = 1;
    private Integer pageSize = 8;
    private Integer counts;

    private boolean isGrid = false;

    public FrompComFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_prompt_com, container, false);
        ButterKnife.bind(this, view);
        initView();
        initGridView();
        if(isGrid){
            recyclerView.setVisibility(View.GONE);
            gridRecyclerView.setVisibility(View.VISIBLE);
        }else{
            recyclerView.setVisibility(View.VISIBLE);
            gridRecyclerView.setVisibility(View.GONE);
        }

        initListener();
        load();
        return view;
    }

    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new ItemAdapter(getActivity(), mList);
        recyclerView.setAdapter(mAdapter);

    }

    private void initGridView(){

        GridLayoutManager manager = new GridLayoutManager(getActivity(),2);
        gridRecyclerView.setLayoutManager(manager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        gridRecyclerView.setHasFixedSize(true);
        gridRecyclerView.addItemDecoration(new DividerListItemDecoration(getActivity(),OrientationHelper.HORIZONTAL, DPUtil.dip2px(8),getResources().getColor(R.color.background_gray)));
        gridRecyclerView.addItemDecoration(new DividerListItemDecoration(getActivity(),OrientationHelper.VERTICAL, DPUtil.dip2px(10),getResources().getColor(R.color.background_gray)));
        itemGridAdapter = new ItemGridAdapter(getActivity(), mList);
        gridRecyclerView.setAdapter(itemGridAdapter);
    }

    private void initListener() {
        mAdapter.setOnItemClickListener(new RecyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ControllerUtil.go2PromptDetail();
            }
        });

        itemGridAdapter.setOnItemClickListener(new RecyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ControllerUtil.go2PromptDetail();
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if ((newState == RecyclerView.SCROLL_STATE_IDLE) && manager.findLastVisibleItemPosition() > 0 && (manager.findLastVisibleItemPosition() == (manager.getItemCount() - 1))) {
                    loadNext();
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        gridRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if ((newState == RecyclerView.SCROLL_STATE_IDLE) && manager.findLastVisibleItemPosition() > 0 && (manager.findLastVisibleItemPosition() == (manager.getItemCount() - 1))) {
                    loadNext();
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });



    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void load() {

    }

    public void isGrid(boolean b){
        isGrid = b;
        if(null!=recyclerView&&null!=gridRecyclerView){
            if(isGrid){
                recyclerView.setVisibility(View.GONE);
                gridRecyclerView.setVisibility(View.VISIBLE);
            }else{
                recyclerView.setVisibility(View.VISIBLE);
                gridRecyclerView.setVisibility(View.GONE);
            }
        }

    }

    private void loadNext() {
        if (counts == null || counts == mList.size()) {
            MainToast.showShortToast("没有更多数据了");
            return;
        }
        showLoading();
        pageIndex++;
        load();
    }

    public void refreshData() {
        if (null != mList) {
            mList.clear();
            pageIndex = 0;
            mAdapter.notifyDataSetChanged();
            load();
        }
    }

    @OnClick({R.id.radio_clothes, R.id.radio_new, R.id.radio_star, R.id.radio_hot})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.radio_clothes:
                break;
            case R.id.radio_new:
                break;
            case R.id.radio_star:
                break;
            case R.id.radio_hot:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
