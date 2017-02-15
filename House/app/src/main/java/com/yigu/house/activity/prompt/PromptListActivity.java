package com.yigu.house.activity.prompt;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yigu.commom.result.MapiItemResult;
import com.yigu.commom.result.MapiResourceResult;
import com.yigu.commom.util.DPUtil;
import com.yigu.commom.widget.MainToast;
import com.yigu.house.R;
import com.yigu.house.adapter.ItemAdapter;
import com.yigu.house.adapter.ItemGridAdapter;
import com.yigu.house.adapter.TabFragmentAdapter;
import com.yigu.house.base.BaseActivity;
import com.yigu.house.base.BaseFrag;
import com.yigu.house.fragment.prompt.FrompComFragment;
import com.yigu.house.interfaces.RecyOnItemClickListener;
import com.yigu.house.util.ControllerUtil;
import com.yigu.house.widget.BestSwipeRefreshLayout;
import com.yigu.house.widget.DividerListItemDecoration;
import com.yigu.house.widget.FilterPopWindow;

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
//    @Bind(R.id.viewpager)
//    ViewPager viewpager;
    @Bind(R.id.checkbox)
    CheckBox checkBox;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.gridRecyclerView)
    RecyclerView gridRecyclerView;
    @Bind(R.id.root_view)
    RelativeLayout root_view;


    private List<String> list_title = new ArrayList<>();
//    private List<Fragment> list = new ArrayList<>();
//    TabFragmentAdapter mAdapter;
//
//    FrompComFragment frompComFragment;
//    FrompComFragment frompComFragment2;
//    FrompComFragment frompComFragment3;
//    FrompComFragment frompComFragment4;

    ItemAdapter mAdapter;
    ItemGridAdapter itemGridAdapter;
    List<MapiItemResult> mList = new ArrayList<>();
    @Bind(R.id.radio_clothes)
    RadioButton radioClothes;
    @Bind(R.id.radio_new)
    RadioButton radioNew;
    @Bind(R.id.radio_star)
    RadioButton radioStar;
    @Bind(R.id.radio_hot)
    RadioButton radioHot;
    @Bind(R.id.bg_color)
    View bgColor;
    @Bind(R.id.listSwipeRefreshLayout)
    BestSwipeRefreshLayout listSwipeRefreshLayout;
    @Bind(R.id.girdSwipeRefreshLayout)
    BestSwipeRefreshLayout girdSwipeRefreshLayout;

    private Integer pageIndex = 1;
    private Integer pageSize = 8;
    private Integer counts;

    private boolean isGrid = false;

    FilterPopWindow topPopWindow;
    FilterPopWindow colorPopWindow;
    FilterPopWindow stylePopWindow;
    FilterPopWindow tianqiPopWindow;

    List<MapiResourceResult> list = new ArrayList<>();
    List<MapiResourceResult> colorList = new ArrayList<>();
    List<MapiResourceResult> styleList = new ArrayList<>();
    List<MapiResourceResult> tianqiList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prompt_list);
        ButterKnife.bind(this);
        initView();
        initGridView();
        initListener();
    }

    private void initView() {

//        frompComFragment = new FrompComFragment();
//        frompComFragment2 = new FrompComFragment();
//        frompComFragment3 = new FrompComFragment();
//        frompComFragment4 = new FrompComFragment();
//        list.add(frompComFragment);
//        list.add(frompComFragment2);
//        list.add(frompComFragment3);
//        list.add(frompComFragment4);

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
//        mAdapter = new TabFragmentAdapter(getSupportFragmentManager(), list, list_title);
//        viewpager.setAdapter(mAdapter);
//        tablayout.setupWithViewPager(viewpager);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new ItemAdapter(this, mList);
        recyclerView.setAdapter(mAdapter);

        list.clear();
        MapiResourceResult mapiResourceResult = new MapiResourceResult();
        mapiResourceResult.setNAME("毛衣");
        list.add(mapiResourceResult);
        MapiResourceResult mapiResourceResult2 = new MapiResourceResult();
        mapiResourceResult2.setNAME("明星款线衣");
        MapiResourceResult mapiResourceResult3 = new MapiResourceResult();
        mapiResourceResult3.setNAME("青少年T恤");
        list.add(mapiResourceResult2);
        list.add(mapiResourceResult3);
        topPopWindow = new FilterPopWindow(this, 0, list, R.style.PopupWindowAnimation);

        topPopWindow.setOnPopItemClickListener(new RecyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                if (null != view) {
                    if (postion >= 0) {
                        radioClothes.setText(list.get(postion).getNAME());
                    } else {
                        radioClothes.setText("类目");
                    }
                    refreshData();
                }
                bgColor.setVisibility(View.GONE);
            }
        });

        colorList.clear();
        MapiResourceResult mapiResourceResult5 = new MapiResourceResult();
        mapiResourceResult5.setNAME("黑色");
        MapiResourceResult mapiResourceResult6 = new MapiResourceResult();
        mapiResourceResult6.setNAME("白色");
        MapiResourceResult mapiResourceResult7 = new MapiResourceResult();
        mapiResourceResult7.setNAME("藏青色");
        MapiResourceResult mapiResourceResult8 = new MapiResourceResult();
        mapiResourceResult8.setNAME("红色");
        MapiResourceResult mapiResourceResult9 = new MapiResourceResult();
        mapiResourceResult9.setNAME("黄色");
        MapiResourceResult mapiResourceResult17 = new MapiResourceResult();
        mapiResourceResult17.setNAME("绿色");
        MapiResourceResult mapiResourceResult18 = new MapiResourceResult();
        mapiResourceResult18.setNAME("橙色");
        MapiResourceResult mapiResourceResult19 = new MapiResourceResult();
        mapiResourceResult19.setNAME("紫色");
        colorList.add(mapiResourceResult5);
        colorList.add(mapiResourceResult6);
        colorList.add(mapiResourceResult7);
        colorList.add(mapiResourceResult8);
        colorList.add(mapiResourceResult9);
        colorList.add(mapiResourceResult17);
        colorList.add(mapiResourceResult18);
        colorList.add(mapiResourceResult19);

        colorPopWindow = new FilterPopWindow(this, 0, colorList, R.style.PopupWindowAnimation);

        colorPopWindow.setOnPopItemClickListener(new RecyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                if (null != view) {
                    if (postion >= 0) {
                        radioNew.setText(colorList.get(postion).getNAME());
                    } else {
                        radioNew.setText("颜色");
                    }
                    refreshData();
                }
                bgColor.setVisibility(View.GONE);
            }
        });

        styleList.clear();
        MapiResourceResult mapiResourceResult10 = new MapiResourceResult();
        mapiResourceResult10.setNAME("布料");
        styleList.add(mapiResourceResult10);
        MapiResourceResult mapiResourceResult11  = new MapiResourceResult();
        mapiResourceResult11.setNAME("毛线");
        MapiResourceResult mapiResourceResult12 = new MapiResourceResult();
        mapiResourceResult12.setNAME("涤纶");
        styleList.add(mapiResourceResult11);
        styleList.add(mapiResourceResult12);

        stylePopWindow = new FilterPopWindow(this, 0, styleList, R.style.PopupWindowAnimation);

        stylePopWindow.setOnPopItemClickListener(new RecyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                if (null != view) {
                    if (postion >= 0) {
                        radioStar.setText(styleList.get(postion).getNAME());
                    } else {
                        radioStar.setText("材质");
                    }
                    refreshData();
                }
                bgColor.setVisibility(View.GONE);
            }
        });

        tianqiList.clear();
        MapiResourceResult mapiResourceResult13 = new MapiResourceResult();
        mapiResourceResult13.setNAME("春");
        MapiResourceResult mapiResourceResult14 = new MapiResourceResult();
        mapiResourceResult14.setNAME("夏");
        MapiResourceResult mapiResourceResult15 = new MapiResourceResult();
        mapiResourceResult15.setNAME("秋");
        MapiResourceResult mapiResourceResult16 = new MapiResourceResult();
        mapiResourceResult16.setNAME("冬");
        tianqiList.add(mapiResourceResult13);
        tianqiList.add(mapiResourceResult14);
        tianqiList.add(mapiResourceResult15);
        tianqiList.add(mapiResourceResult16);
        tianqiPopWindow = new FilterPopWindow(this, 0, tianqiList, R.style.PopupWindowAnimation);

        tianqiPopWindow.setOnPopItemClickListener(new RecyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                if (null != view) {
                    if (postion >= 0) {
                        radioHot.setText(tianqiList.get(postion).getNAME());
                    } else {
                        radioHot.setText("季节");
                    }
                    refreshData();

                }
                bgColor.setVisibility(View.GONE);
            }
        });

        listSwipeRefreshLayout.setBestRefreshListener(new BestSwipeRefreshLayout.BestRefreshListener() {
            @Override
            public void onBestRefresh() {
                listSwipeRefreshLayout.setRefreshing(false);
            }
        });

        girdSwipeRefreshLayout.setBestRefreshListener(new BestSwipeRefreshLayout.BestRefreshListener() {
            @Override
            public void onBestRefresh() {
                girdSwipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private void initGridView() {

        GridLayoutManager manager = new GridLayoutManager(this, 2);
        gridRecyclerView.setLayoutManager(manager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        gridRecyclerView.setHasFixedSize(true);
        gridRecyclerView.addItemDecoration(new DividerListItemDecoration(this, OrientationHelper.HORIZONTAL, DPUtil.dip2px(8), getResources().getColor(R.color.background_gray)));
        gridRecyclerView.addItemDecoration(new DividerListItemDecoration(this, OrientationHelper.VERTICAL, DPUtil.dip2px(10), getResources().getColor(R.color.background_gray)));
        itemGridAdapter = new ItemGridAdapter(this, mList);
        gridRecyclerView.setAdapter(itemGridAdapter);
    }

    private void initListener() {
        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count,
                                          int after) {
                searchEt.performClick();
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

        searchEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {//EditorInfo.IME_ACTION_SEARCH、EditorInfo.IME_ACTION_SEND等分别对应EditText的imeOptions属性
                    //TODO回车键按下时要执行的操作
                    closeKeybord(PromptListActivity.this);
                }
                return true;
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isGrid = b;
                if (null != recyclerView && null != gridRecyclerView) {
                    if (isGrid) {
                        listSwipeRefreshLayout.setVisibility(View.GONE);
                        girdSwipeRefreshLayout.setVisibility(View.VISIBLE);
                    } else {
                        listSwipeRefreshLayout.setVisibility(View.VISIBLE);
                        girdSwipeRefreshLayout.setVisibility(View.GONE);
                    }
                }

               /* for(Fragment baseFrag : list){
                    ((BaseFrag)baseFrag).isGrid(b);
                }*/

            }
        });

        mAdapter.setOnItemClickListener(new RecyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ControllerUtil.go2PromptDetail();
            }
        });

        itemGridAdapter.setOnItemClickListener(new RecyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ControllerUtil.go2PromptDetail();
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if ((newState == RecyclerView.SCROLL_STATE_IDLE) && manager.findLastVisibleItemPosition() > 0 && (manager.findLastVisibleItemPosition() == (manager.getItemCount() - 1))) {
                    loadNext();
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        gridRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if ((newState == RecyclerView.SCROLL_STATE_IDLE) && manager.findLastVisibleItemPosition() > 0 && (manager.findLastVisibleItemPosition() == (manager.getItemCount() - 1))) {
                    loadNext();
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

    }

    private void loadNext() {
        if (counts == null || counts == mList.size()) {
            MainToast.showShortToast("没有更多数据了");
            return;
        }
        showLoading();
        pageIndex++;
        load();
    }

    public void refreshData() {
        if (null != mList) {
            mList.clear();
            pageIndex = 0;
            mAdapter.notifyDataSetChanged();
            load();
        }
    }

    public void load() {


    }



    @OnClick({R.id.back, R.id.clear_iv,R.id.radio_clothes, R.id.radio_new, R.id.radio_star, R.id.radio_hot})//, R.id.search
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.clear_iv:
                searchEt.setText("");
                break;
            case R.id.radio_clothes:
                topPopWindow.showPopupWindow(view);
                bgColor.setVisibility(View.VISIBLE);
                break;
            case R.id.radio_new:
                colorPopWindow.showPopupWindow(view);
                bgColor.setVisibility(View.VISIBLE);
                break;
            case R.id.radio_star:
                stylePopWindow.showPopupWindow(view);
                bgColor.setVisibility(View.VISIBLE);
                break;
            case R.id.radio_hot:
                tianqiPopWindow.showPopupWindow(view);
                bgColor.setVisibility(View.VISIBLE);
                break;
//            case R.id.search:
//                break;
        }
    }






}
