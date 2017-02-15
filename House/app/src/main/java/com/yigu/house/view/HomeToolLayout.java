package com.yigu.house.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.yigu.commom.result.MapiResourceResult;
import com.yigu.commom.widget.MainToast;
import com.yigu.house.R;
import com.yigu.house.adapter.MainAdapter;
import com.yigu.house.adapter.MainToolAdapter;
import com.yigu.house.interfaces.RecyOnItemClickListener;
import com.yigu.house.util.ControllerUtil;
import com.yigu.house.util.JGJDataSource;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2016/8/31.
 */
public class HomeToolLayout extends RelativeLayout {
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    private Context mContext;
    private View view;

    List<MapiResourceResult> mList = new ArrayList<>();
    MainToolAdapter mAdapter;
    public HomeToolLayout(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public HomeToolLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public HomeToolLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        if (isInEditMode())
            return;
        view = LayoutInflater.from(mContext).inflate(R.layout.layout_home_tool, this);
        ButterKnife.bind(this, view);
        load();
        initListener();
    }

    public void load() {
        mList.clear();
        mList.addAll(JGJDataSource.getMainResource());
        GridLayoutManager manager = new GridLayoutManager(mContext, 4);
        recyclerView.setLayoutManager(manager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recyclerView.setHasFixedSize(true);
        mAdapter = new MainToolAdapter(mContext, mList);
        recyclerView.setAdapter(mAdapter);
    }

    private void initListener(){
        mAdapter.setOnItemClickListener(new RecyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position){
                    case 0://现货
                        ControllerUtil.go2PromptList();
                        break;
                    case 1:
                       ControllerUtil.go2IndentList();
                        break;
                    case 2:
                        MainToast.showShortToast("敬请期待");
                        break;
                    case 3://拼单
                        ControllerUtil.go2GroupList();
                        break;
                    case 4://来样定制
                        ControllerUtil.go2Custom();
                        break;
                    case 5:
                        MainToast.showShortToast("敬请期待");
                        break;
                    case 6:
                        MainToast.showShortToast("敬请期待");
                        break;
                    case 7:
                        MainToast.showShortToast("敬请期待");
                        break;
                }
            }
        });
    }

}
