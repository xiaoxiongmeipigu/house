package com.yigu.house.activity.indent;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yigu.commom.result.MapiItemResult;
import com.yigu.commom.util.DPUtil;
import com.yigu.commom.widget.circlelayoutmanager.CenterScrollListener;
import com.yigu.commom.widget.circlelayoutmanager.ScrollZoomLayoutManager;
import com.yigu.house.R;
import com.yigu.house.adapter.indent.IndentListAdapter;
import com.yigu.house.base.BaseActivity;
import com.yigu.house.interfaces.RecyOnItemClickListener;
import com.yigu.house.util.ControllerUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IndentListActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.center)
    TextView center;
    @Bind(R.id.iv_right_two)
    ImageView ivRightTwo;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    ScrollZoomLayoutManager scrollZoomLayoutManager;
    IndentListAdapter mAdapter;
    List<MapiItemResult> mList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indent_list);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initView() {
        center.setText("下单");
        back.setImageResource(R.mipmap.back);
        ivRightTwo.setImageResource(R.mipmap.purcase_icon);

        mList = new ArrayList<>();
        scrollZoomLayoutManager = new ScrollZoomLayoutManager(this, DPUtil.dip2px(10));
        recyclerView.addOnScrollListener(new CenterScrollListener());
        recyclerView.setLayoutManager(scrollZoomLayoutManager);
        recyclerView.setHasFixedSize(true);
        mAdapter = new IndentListAdapter(this,mList);
        recyclerView.setAdapter(mAdapter);
    }

    private void initListener(){
        mAdapter.setOnItemClickListener(new RecyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ControllerUtil.go2IndentDetail();
            }
        });
    }

    @OnClick({R.id.back, R.id.iv_right_two})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.iv_right_two:
                break;
        }
    }
}
