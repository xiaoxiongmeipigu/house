package com.yigu.house.fragment.prompt;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzy.widget.vertical.VerticalRecyclerView;
import com.yigu.commom.result.IndexData;
import com.yigu.commom.result.MapiResourceResult;
import com.yigu.commom.util.DebugLog;
import com.yigu.house.R;
import com.yigu.house.adapter.MainAdapter;
import com.yigu.house.adapter.prompt.PromptInfoAdapter;
import com.yigu.house.base.BaseFrag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PromptInfoFragment extends BaseFrag {


    @Bind(R.id.recyclerView)
    VerticalRecyclerView recyclerView;

    private final static String SCROLL = "SCROLL";
    private final static String TOOL = "TOOL";
    private final static String ITEM = "ITEM";
    List<IndexData> mList = new ArrayList<>();

    PromptInfoAdapter mAdapter;

    public PromptInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_prompt_info, container, false);
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

    @Override
    public void load() {
        mList.clear();
        mList.add(new IndexData(0, SCROLL, new ArrayList<MapiResourceResult>()));
        mList.add(new IndexData(1, TOOL, new ArrayList<MapiResourceResult>()));
        mList.add(new IndexData(2, ITEM, new ArrayList<MapiResourceResult>()));
        Collections.sort(mList);
        mAdapter.notifyDataSetChanged();
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

}
