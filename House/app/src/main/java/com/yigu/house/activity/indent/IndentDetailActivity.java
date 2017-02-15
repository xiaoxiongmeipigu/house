package com.yigu.house.activity.indent;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yigu.commom.result.IndexData;
import com.yigu.commom.result.MapiResourceResult;
import com.yigu.house.R;
import com.yigu.house.adapter.prompt.PromptInfoAdapter;
import com.yigu.house.base.BaseActivity;
import com.yigu.house.util.ControllerUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IndentDetailActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.center)
    TextView center;
    @Bind(R.id.iv_right_two)
    ImageView ivRightTwo;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private final static String SCROLL = "SCROLL";
    private final static String TOOL_INDENT = "TOOL_INDENT";
    List<IndexData> mList = new ArrayList<>();

    PromptInfoAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indent_detail);
        ButterKnife.bind(this);
        initView();
        load();
    }

    private void initView() {
        back.setImageResource(R.mipmap.back);
        center.setText("样衣详情");
//        ivRightTwo.setImageResource(R.mipmap.purcase_icon);
//        ivRightTwo.setVisibility(View.VISIBLE);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new PromptInfoAdapter(this, mList);
        recyclerView.setAdapter(mAdapter);

    }

    public void load(){
        mList.clear();
        mList.add(new IndexData(0, SCROLL, new ArrayList<MapiResourceResult>()));
        mList.add(new IndexData(1, TOOL_INDENT, new ArrayList<MapiResourceResult>()));
        Collections.sort(mList);
        mAdapter.notifyDataSetChanged();
    }


    @OnClick({R.id.back,R.id.order})//, R.id.iv_right_two
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
//            case R.id.iv_right_two:
//                break;
            case R.id.order:

            break;
        }
    }
}
