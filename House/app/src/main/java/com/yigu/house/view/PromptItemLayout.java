package com.yigu.house.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.yigu.commom.result.MapiItemResult;
import com.yigu.commom.util.DebugLog;
import com.yigu.commom.widget.MainToast;
import com.yigu.house.R;
import com.yigu.house.adapter.prompt.PromptInfoAdapter;
import com.yigu.house.adapter.prompt.PromptItemAdapter;
import com.yigu.house.interfaces.RecyOnItemClickListener;
import com.yigu.house.util.ControllerUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2016/12/14.
 */
public class PromptItemLayout extends RelativeLayout {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    private Context mContext;
    private View view;

    PromptItemAdapter mAdapter;
    List<MapiItemResult> mList;

    int fromType = 0;

    public PromptItemLayout(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public PromptItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public PromptItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        if (isInEditMode())
            return;
        view = LayoutInflater.from(mContext).inflate(R.layout.layout_prompt_item, this);
        ButterKnife.bind(this, view);
        mList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        mAdapter = new PromptItemAdapter(mContext,mList);
        recyclerView.setAdapter(mAdapter);
        initListener();
    }

    public void load(List<MapiItemResult> list,int fromType) {
        this.fromType = fromType;
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    private void initListener() {
        mAdapter.setOnItemClickListener(new RecyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(fromType==0){
                    ControllerUtil.go2PromptDetail(mList.get(position).getGoods_id());
                }else if(fromType==1){
                    ControllerUtil.go2GroupDetail(mList.get(position).getGoods_id());
                }else if(fromType==2){
                    ControllerUtil.go2IndentDetail(mList.get(position).getGoods_id());
                }else if(fromType==3){
                    ControllerUtil.go2VipDetail(mList.get(position).getGoods_id());
                }
            }
        });
    }

}
