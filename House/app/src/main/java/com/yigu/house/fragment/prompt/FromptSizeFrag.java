package com.yigu.house.fragment.prompt;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yigu.commom.result.MapiSizeResult;
import com.yigu.house.R;
import com.yigu.house.adapter.ItemAdapter;
import com.yigu.house.adapter.prompt.PromptSizeAdapter;
import com.yigu.house.base.BaseFrag;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2016/12/17.
 */
public class FromptSizeFrag extends BaseFrag {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    PromptSizeAdapter mAdapter;
    private List<MapiSizeResult> sizeList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.frag_prompt_size, container, false);
        ButterKnife.bind(this, view);
        initView();
        initListener();
        load();
        return view;
    }

    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new PromptSizeAdapter(getActivity(), sizeList);
        recyclerView.setAdapter(mAdapter);
    }

    private void initListener() {

    }

    public void load(){
        sizeList.clear();
        MapiSizeResult sizeResult1 = new  MapiSizeResult("1200","2","188.00","S");
        MapiSizeResult sizeResult2 = new MapiSizeResult("5200","1","188.00","M");
        MapiSizeResult sizeResult3 = new MapiSizeResult("13400","1","188.00","L");
        MapiSizeResult sizeResult4 = new MapiSizeResult("3400","266","188.00","XL");
        MapiSizeResult sizeResult5 = new MapiSizeResult("345200","23","188.00","XLL");
        MapiSizeResult sizeResult6 = new MapiSizeResult("345500","22","188.00","XLL");
        sizeList.add(sizeResult1);
        sizeList.add(sizeResult2);
        sizeList.add(sizeResult3);
        sizeList.add(sizeResult4);
        sizeList.add(sizeResult5);
        sizeList.add(sizeResult6);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
