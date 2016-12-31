package com.yigu.house.view;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yigu.commom.result.MapiSizeResult;
import com.yigu.house.R;
import com.yigu.house.adapter.ItemAdapter;
import com.yigu.house.adapter.TabFragmentAdapter;
import com.yigu.house.adapter.prompt.PromptSizeAdapter;
import com.yigu.house.base.BaseActivity;
import com.yigu.house.fragment.prompt.FrompComFragment;
import com.yigu.house.fragment.prompt.FromptSizeFrag;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by brain on 2016/12/17.
 */
public class SizeColorLayout extends RelativeLayout {

    @Bind(R.id.content)
    TextView content;
    @Bind(R.id.price)
    TextView price;
    @Bind(R.id.image)
    SimpleDraweeView image;
    @Bind(R.id.tablayout)
    TabLayout tablayout;
    @Bind(R.id.viewpager)
    ViewPager viewpager;

    private Context mContext;
    private View view;


    private List<String> list_title = new ArrayList<>();

    FromptSizeFrag sizeFrag1;
    FromptSizeFrag sizeFrag2;
    FromptSizeFrag sizeFrag3;
    FromptSizeFrag sizeFrag4;
    FromptSizeFrag sizeFrag5;
    FromptSizeFrag sizeFrag6;

    private List<Fragment> list = new ArrayList<>();

    BaseActivity activity;
    TabFragmentAdapter mAdapter;

    public SizeColorLayout(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public SizeColorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public SizeColorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        if (isInEditMode())
            return;
        view = LayoutInflater.from(mContext).inflate(R.layout.layout_size_color, this);
        ButterKnife.bind(this, view);
        init();
        load();
    }

    private void init(){

        sizeFrag1 = new FromptSizeFrag();
        sizeFrag2 = new FromptSizeFrag();
        sizeFrag3 = new FromptSizeFrag();
        sizeFrag4 = new FromptSizeFrag();
        sizeFrag5 = new FromptSizeFrag();
        sizeFrag6 = new FromptSizeFrag();
        list.add(sizeFrag1);
        list.add(sizeFrag2);
        list.add(sizeFrag3);
        list.add(sizeFrag4);
        list.add(sizeFrag5);
        list.add(sizeFrag6);

        list_title.add("白色");
        list_title.add("灰色");
        list_title.add("黑色");
        list_title.add("红色");
        list_title.add("紫色");
        list_title.add("蓝色");
        tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tablayout.addTab(tablayout.newTab().setText(list_title.get(0)));
        tablayout.addTab(tablayout.newTab().setText(list_title.get(1)));
        tablayout.addTab(tablayout.newTab().setText(list_title.get(2)));
        tablayout.addTab(tablayout.newTab().setText(list_title.get(3)));
        tablayout.addTab(tablayout.newTab().setText(list_title.get(4)));
        tablayout.addTab(tablayout.newTab().setText(list_title.get(5)));

    }


    public void load() {



    }

    public void setActivity(BaseActivity activity) {
        this.activity = activity;
        mAdapter = new TabFragmentAdapter(activity.getSupportFragmentManager(), list, list_title);
        viewpager.setAdapter(mAdapter);
        tablayout.setupWithViewPager(viewpager);
    }

    @OnClick(R.id.close_iv)
    public void onClick() {
        if (null != onHideListener)
            onHideListener.hide();
    }

    private OnHideListener onHideListener;

    public interface OnHideListener {
        void hide();
    }

    public void setOnHideListener(OnHideListener onHideListener) {
        this.onHideListener = onHideListener;
    }
}
