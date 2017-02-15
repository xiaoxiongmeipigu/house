package com.yigu.house.activity.indent;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.yigu.house.R;
import com.yigu.house.adapter.TabFragmentAdapter;
import com.yigu.house.base.BaseActivity;
import com.yigu.house.fragment.indent.IndentCompleteFrag;
import com.yigu.house.fragment.indent.IndentUnCompleteFrag;
import com.yigu.house.fragment.prompt.FrompComFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IndentResultActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.center)
    TextView center;
    @Bind(R.id.tablayout)
    TabLayout tablayout;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    private List<String> list_title = new ArrayList<>();
    private List<Fragment> list = new ArrayList<>();
    TabFragmentAdapter mAdapter;

    IndentCompleteFrag indentCompleteFrag;
    IndentUnCompleteFrag indentUnCompleteFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indent_result);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        back.setImageResource(R.mipmap.back);
        center.setText("下单");

        indentCompleteFrag = new IndentCompleteFrag();
        indentUnCompleteFrag = new IndentUnCompleteFrag();

        list.add(indentCompleteFrag);
        list.add(indentUnCompleteFrag);

        list_title.add("已完成");
        list_title.add("未完成");

        tablayout.setTabMode(TabLayout.MODE_FIXED);
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tablayout.addTab(tablayout.newTab().setText(list_title.get(0)));
        tablayout.addTab(tablayout.newTab().setText(list_title.get(1)));

        mAdapter = new TabFragmentAdapter(getSupportFragmentManager(), list, list_title);
        viewpager.setAdapter(mAdapter);
        tablayout.setupWithViewPager(viewpager);

    }

    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }
}
