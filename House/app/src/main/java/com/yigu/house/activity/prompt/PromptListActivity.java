package com.yigu.house.activity.prompt;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

import com.yigu.commom.result.MapiItemResult;
import com.yigu.commom.widget.MainToast;
import com.yigu.house.R;
import com.yigu.house.adapter.ItemAdapter;
import com.yigu.house.adapter.TabFragmentAdapter;
import com.yigu.house.base.BaseActivity;
import com.yigu.house.base.BaseFrag;
import com.yigu.house.fragment.prompt.FrompComFragment;
import com.yigu.house.interfaces.RecyOnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PromptListActivity extends BaseActivity {

    @Bind(R.id.clear_iv)
    ImageView clearIv;
    @Bind(R.id.search_et)
    EditText searchEt;
    @Bind(R.id.tablayout)
    TabLayout tablayout;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.checkbox)
    CheckBox checkBox;


    private List<String> list_title = new ArrayList<>();
    private List<Fragment> list = new ArrayList<>();
    TabFragmentAdapter mAdapter;

    FrompComFragment frompComFragment;
    FrompComFragment frompComFragment2;
    FrompComFragment frompComFragment3;
    FrompComFragment frompComFragment4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prompt_list);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initView() {

        frompComFragment = new FrompComFragment();
        frompComFragment2 = new FrompComFragment();
        frompComFragment3 = new FrompComFragment();
        frompComFragment4 = new FrompComFragment();
        list.add(frompComFragment);
        list.add(frompComFragment2);
        list.add(frompComFragment3);
        list.add(frompComFragment4);

        list_title.add("推荐");
        list_title.add("新品");
        list_title.add("网红款");
        list_title.add("明星款");
        tablayout.setTabMode(TabLayout.MODE_FIXED);
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tablayout.addTab(tablayout.newTab().setText(list_title.get(0)));
        tablayout.addTab(tablayout.newTab().setText(list_title.get(1)));
        tablayout.addTab(tablayout.newTab().setText(list_title.get(2)));
        tablayout.addTab(tablayout.newTab().setText(list_title.get(3)));
        mAdapter = new TabFragmentAdapter(getSupportFragmentManager(), list, list_title);
        viewpager.setAdapter(mAdapter);
        tablayout.setupWithViewPager(viewpager);

    }

    private void initListener() {
        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count,
                                          int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (charSequence.length() > 0) {
                    clearIv.setVisibility(View.VISIBLE);
                } else {
                    clearIv.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                for(Fragment baseFrag : list){
                    ((BaseFrag)baseFrag).isGrid(b);
                }

            }
        });

    }


    @OnClick({R.id.back, R.id.clear_iv, R.id.search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.clear_iv:
                searchEt.setText("");
                break;
            case R.id.search:
                break;
        }
    }






}
