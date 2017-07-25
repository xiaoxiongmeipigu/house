package com.yigu.house.fragment.prompt;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yigu.commom.result.MapiSizeResult;
import com.yigu.commom.util.DebugLog;
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

    private  String price = "";
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
        if(TextUtils.isEmpty(price))
            price = "0";
        mAdapter.setBaiscPrice(price);
        recyclerView.setAdapter(mAdapter);
    }

    private void initListener() {
        mAdapter.setNumListener(new PromptSizeAdapter.NumInterface() {
            @Override
            public void modify(View view, int num, int position) {
                sizeList.get(position).setNum(num+"");
                if(null!=numInterface)
                    numInterface.modify();
            }
        });
    }

    public void load(List<MapiSizeResult> list){
        if(null!=list){
            sizeList.clear();
            sizeList.addAll(list);
        }
    }

    public void setBaiscPrice(String price){
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    NumInterface numInterface;

    public interface NumInterface{
        void modify();
    }

    public void setNumListener(NumInterface numInterface){
        this.numInterface = numInterface;
    }

    public List<MapiSizeResult> getList(){
        List<MapiSizeResult> list = new ArrayList<>();
        for(MapiSizeResult mapiSizeResult : sizeList){
            DebugLog.i("getList:"+mapiSizeResult.getNum());
            String numStr = TextUtils.isEmpty(mapiSizeResult.getNum())?"0":mapiSizeResult.getNum();
            Integer num = Integer.parseInt(numStr);
            if(null!=num&&num>0)
                list.add(mapiSizeResult);
        }
        return list;
    }

}
