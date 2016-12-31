package com.yigu.house.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.yigu.commom.result.IndexData;
import com.yigu.commom.result.MapiResourceResult;
import com.yigu.commom.util.DebugLog;
import com.yigu.house.R;
import com.yigu.house.adapter.MainAdapter;
import com.yigu.house.base.BaseActivity;
import com.yigu.house.base.BaseFrag;
import com.yigu.house.fragment.home.HomeFragment;
import com.yigu.house.fragment.person.PersonFragment;
import com.yigu.house.fragment.purcase.PurcaseFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Bind(R.id.radio_home)
    RadioButton radioHome;
    @Bind(R.id.radio_order)
    RadioButton radioOrder;
    @Bind(R.id.radio_person)
    RadioButton radioPerson;
    @Bind(R.id.content)
    FrameLayout content;

    private BaseFrag[] fragments;
    private int index = 0;
    private long exitTime = 0;

    private RadioButton[] buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        fragments = new BaseFrag[3];
        fragments[0] = new HomeFragment();
        fragments[1] = new PurcaseFragment();
        fragments[2] = new PersonFragment();

        buttons = new RadioButton[3];
        buttons[0] = radioHome;
        buttons[1] = radioOrder;
        buttons[2] = radioPerson;

        selectTab();

    }

    private void selectTab() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for (BaseFrag frag : fragments) {
            if (!frag.isAdded())
                transaction.add(R.id.content, frag);
            transaction.hide(frag);
        }
        transaction.show(fragments[index]);
        transaction.commitAllowingStateLoss();
    }

    @OnClick({R.id.radio_home, R.id.radio_order, R.id.radio_person})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.radio_home:
                index = 0;
                selectTab();
                break;
            case R.id.radio_order:
                index = 1;
                selectTab();
                fragments[index].load();
                break;
            case R.id.radio_person:
                index = 2;
                selectTab();
                fragments[index].load();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出应用", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
