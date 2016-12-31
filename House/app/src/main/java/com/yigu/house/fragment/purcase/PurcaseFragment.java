package com.yigu.house.fragment.purcase;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yigu.commom.result.IndexData;
import com.yigu.commom.result.MapiCarResult;
import com.yigu.commom.result.MapiItemResult;
import com.yigu.house.R;
import com.yigu.house.adapter.purcase.PurcaseAdapter;
import com.yigu.house.base.BaseFrag;
import com.yigu.house.widget.BestSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link BaseFrag} subclass.
 */
public class PurcaseFragment extends BaseFrag {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.center)
    TextView center;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.swipeRefreshLayout)
    BestSwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.price)
    TextView price;

    List<IndexData> mList = new ArrayList<>();
    List<MapiCarResult> list = new ArrayList<>();
    int count;
    PurcaseAdapter mAdapter;

    public PurcaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_purcase, container, false);
        ButterKnife.bind(this, view);
        initView();
        initListener();
        load();
        return view;
    }

    private void initView() {

        center.setText("进货单(0)");
        tvRight.setText("编辑");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
//        recyclerView.addItemDecoration(new DividerListItemDecoration(this, OrientationHelper.HORIZONTAL, DPUtil.dip2px(1), Color.parseColor("#eeeeee")));
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new PurcaseAdapter(getActivity(), mList);
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
        list.clear();
        mList.clear();
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
                mList.add(new IndexData(count++,"head",ware));
                for (int i=0;i<ware.getItems().size();i++) {
                    mList.add(new IndexData(count++,"item", ware.getItems().get(i)));
                }
                if(count<=list.size()-1)
                mList.add(new IndexData(count++,"divider", new Object()));
            }
        }
        mAdapter.notifyDataSetChanged();
    }

}
