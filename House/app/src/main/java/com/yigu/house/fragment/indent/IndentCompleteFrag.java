package com.yigu.house.fragment.indent;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yigu.commom.result.IndexData;
import com.yigu.commom.result.MapiCarResult;
import com.yigu.commom.result.MapiItemResult;
import com.yigu.house.R;
import com.yigu.house.adapter.indent.IndentCompleteAdapter;
import com.yigu.house.adapter.purcase.PurcaseAdapter;
import com.yigu.house.base.BaseFrag;
import com.yigu.house.widget.BestSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2017/1/9.
 */
public class IndentCompleteFrag extends BaseFrag {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.swipeRefreshLayout)
    BestSwipeRefreshLayout swipeRefreshLayout;

    List<MapiCarResult> list = new ArrayList<>();
    List<IndexData> mList = new ArrayList<>();
    int count;
    IndentCompleteAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_indent_complete, container, false);
        ButterKnife.bind(this, view);
        initView();
        initListener();
        load();
        return view;
    }

    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new IndentCompleteAdapter(getActivity(), mList);
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
                for (int i=0;i<ware.getItems().size();i++) {
                    mList.add(new IndexData(count++,"item", ware.getItems().get(i)));
                }
                mList.add(new IndexData(count++,"divider", new Object()));
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
