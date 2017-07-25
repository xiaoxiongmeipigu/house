package com.yigu.house.fragment.group;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lzy.widget.vertical.VerticalRecyclerView;
import com.yigu.commom.result.IndexData;
import com.yigu.commom.result.MapiItemResult;
import com.yigu.commom.result.MapiResourceResult;
import com.yigu.commom.util.DebugLog;
import com.yigu.commom.widget.MainToast;
import com.yigu.house.R;
import com.yigu.house.adapter.prompt.PromptInfoAdapter;
import com.yigu.house.base.BaseFrag;
import com.yigu.house.widget.PromotionDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupInfoFragment extends BaseFrag {


    @Bind(R.id.recyclerView)
    VerticalRecyclerView recyclerView;

    private final static String SCROLL = "SCROLL";
    private final static String TOOL_GROUP = "TOOL_GROUP";
    private final static String ITEM = "ITEM";
    private final static String ITEM_GROUP = "ITEM_GROUP";

    List<IndexData> mList = new ArrayList<>();

    PromptInfoAdapter mAdapter;

    MapiItemResult mapiItemResult;
    List<MapiResourceResult> result;

    public GroupInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_group_info, container, false);
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
        mAdapter = new PromptInfoAdapter(getActivity(), mList);
        recyclerView.setAdapter(mAdapter);

        mapiItemResult = new MapiItemResult();

    }

    private void initListener() {
        mAdapter.setSizeOpenListener(new PromptInfoAdapter.SizeOpenListener() {
            @Override
            public void open() {
                DebugLog.i("PromptInfoFragment=>selSizeLL");
                if(null!=sizeOpenListener)
                    sizeOpenListener.open();
            }
        });
    }

    public void load(JSONObject jsonObject) {

        mList.clear();
        result = JSONArray.parseArray(jsonObject.getJSONObject("data").getJSONArray("img").toJSONString(),MapiResourceResult.class);
        List<MapiItemResult> similar = JSONArray.parseArray(jsonObject.getJSONObject("data").getJSONArray("similar").toJSONString(),MapiItemResult.class);

        String goods_id = jsonObject.getJSONObject("data").getString("goods_id");
        String title = jsonObject.getJSONObject("data").getString("title");
        String price = jsonObject.getJSONObject("data").getString("price");
        String terms = jsonObject.getJSONObject("data").getString("terms");
        String sell_count = jsonObject.getJSONObject("data").getString("sell_count");
        String location = jsonObject.getJSONObject("data").getString("location");
        String express_fee = jsonObject.getJSONObject("data").getString("express_fee");
        String old_price = jsonObject.getJSONObject("data").getString("old_price");
        String duration_time = jsonObject.getJSONObject("data").getString("duration_time");
        mapiItemResult.setGoods_id(goods_id);
        mapiItemResult.setTitle(title);
        mapiItemResult.setPrice(price);
        mapiItemResult.setTerms(terms);
        mapiItemResult.setSell_count(sell_count);
        mapiItemResult.setLocation(location);
        mapiItemResult.setExpress_fee(express_fee);
        mapiItemResult.setOld_price(old_price);
        mapiItemResult.setDuration_time(duration_time);
        if(null!=result){
            mList.add(new IndexData(0, SCROLL,result));
        }else{
            mList.add(new IndexData(0, SCROLL, new ArrayList<MapiResourceResult>()));
        }
        mList.add(new IndexData(1, TOOL_GROUP, mapiItemResult));
        if(null!=result){
            mList.add(new IndexData(2, ITEM_GROUP, similar));
        }else{
            mList.add(new IndexData(2, ITEM_GROUP, new ArrayList<MapiResourceResult>()));
        }

        Collections.sort(mList);
        mAdapter.notifyDataSetChanged();

      /*  mList.clear();
        mList.add(new IndexData(0, SCROLL, new ArrayList<MapiResourceResult>()));
        mList.add(new IndexData(1, TOOL_GROUP, new ArrayList<MapiResourceResult>()));
        mList.add(new IndexData(2, ITEM, new ArrayList<MapiResourceResult>()));
        Collections.sort(mList);
        mAdapter.notifyDataSetChanged();*/
    }

    public void setSelSize(String attrStr){
        if(null!=mapiItemResult&&null!=mList){
            for(IndexData indexData : mList){
                if(indexData.getType().equals(TOOL_GROUP)) {
                    MapiItemResult itemResult = (MapiItemResult) indexData.getData();
                    itemResult.setArrtStr(attrStr);
                    if(null!=mAdapter){
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }
        }

    }

    @Override
    public void goTop() {
        DebugLog.i("info==>goTop");
        recyclerView.goTop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public SizeOpenListener sizeOpenListener;

    public interface SizeOpenListener{
        void open();
    }

    public void setSizeOpenListener(SizeOpenListener sizeOpenListener){
        this.sizeOpenListener = sizeOpenListener;
    }

    /**
     * 显示推广弹框
     *
     */
    private void showPromotDialog() {
        PromotionDialog dialog = new PromotionDialog(getActivity());
        dialog.setParams(result,mapiItemResult.getTitle());
        dialog.show();
    }

    @OnClick(R.id.down_iv)
    public void onClick() {
        if(null!=result){
            showPromotDialog();
        }else
            MainToast.showShortToast("暂无数据");

    }

}
