package com.yigu.house.activity.prompt;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yigu.commom.api.CommonApi;
import com.yigu.commom.api.ItemApi;
import com.yigu.commom.result.MapiItemResult;
import com.yigu.commom.result.MapiResourceResult;
import com.yigu.commom.util.DPUtil;
import com.yigu.commom.util.DebugLog;
import com.yigu.commom.util.RequestCallback;
import com.yigu.commom.util.RequestExceptionCallback;
import com.yigu.commom.util.RequestPageCallback;
import com.yigu.commom.widget.MainToast;
import com.yigu.house.R;
import com.yigu.house.activity.webview.WebviewControlActivity;
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
    private Integer counts;

    private boolean isGrid = false;

    FilterPopWindow topPopWindow;
    FilterPopWindow materialPopWindow;
    FilterPopWindow stylePopWindow;
    FilterPopWindow seasonPopWindow;

    List<MapiResourceResult> list = new ArrayList<>();
    List<MapiResourceResult> materialList = new ArrayList<>();
    List<MapiResourceResult> styleList = new ArrayList<>();
    List<MapiResourceResult> seasonList = new ArrayList<>();

    String[] filterStrs = new String[4];
    String filters = "";
    String push = "1";
    String newStr = "0";
    String star = "0";
    String kol = "0";

    String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prompt_list);
        ButterKnife.bind(this);
        if(null!=getIntent()){
            String search = getIntent().getStringExtra("search");
            type = getIntent().getStringExtra("type");
            if(!TextUtils.isEmpty(search))
                searchEt.setText(search);

        }

        initView();
        initGridView();
        initListener();
        filterStrs = new String[4];
        loadBar();
        load();
    }

    @Override
    protected void onResume() {
        super.onResume();
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


        if(null!=type){
            if(type.equals("push")){
                tablayout.getTabAt(0).select();
                push = "1";
                newStr = "0";
                kol = "0";
                star = "0";
            }else if(type.equals("new")){
                tablayout.getTabAt(1).select();
                push = "0";
                newStr = "1";
                kol = "0";
                star = "0";
            }else if(type.equals("kol")){
                tablayout.getTabAt(2).select();
                push = "0";
                newStr = "0";
                kol = "1";
                star = "0";
            }else if(type.equals("star")){
                tablayout.getTabAt(3).select();
                push = "0";
                newStr = "0";
                kol = "0";
                star = "1";
            }
        }


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new ItemAdapter(this, mList);
        recyclerView.setAdapter(mAdapter);

        topPopWindow = new FilterPopWindow(this, 0, list, R.style.PopupWindowAnimation);
        stylePopWindow = new FilterPopWindow(this, 0, styleList, R.style.PopupWindowAnimation);
        materialPopWindow = new FilterPopWindow(this, 0, materialList, R.style.PopupWindowAnimation);
        seasonPopWindow = new FilterPopWindow(this, 0, seasonList, R.style.PopupWindowAnimation);

    }

    private void initGridView() {

        GridLayoutManager manager = new GridLayoutManager(this, 2);
        gridRecyclerView.setLayoutManager(manager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        gridRecyclerView.setHasFixedSize(true);
        gridRecyclerView.addItemDecoration(new DividerListItemDecoration(this, OrientationHelper.VERTICAL, DPUtil.dip2px(4), getResources().getColor(R.color.background_gray)));
        itemGridAdapter = new ItemGridAdapter(this, mList);
        gridRecyclerView.setAdapter(itemGridAdapter);
    }

    private void loadBar(){
        showLoading();
        CommonApi.bar(this, new RequestCallback<JSONObject>() {
            @Override
            public void success(JSONObject success) {
                hideLoading();
                List<MapiResourceResult> leis = JSONArray.parseArray(success.getJSONObject("data").getJSONArray("category").toJSONString(),MapiResourceResult.class);
                List<MapiResourceResult> styles = JSONArray.parseArray(success.getJSONObject("data").getJSONArray("style").toJSONString(),MapiResourceResult.class);
                List<MapiResourceResult> caizhis = JSONArray.parseArray(success.getJSONObject("data").getJSONArray("material").toJSONString(),MapiResourceResult.class);
                List<MapiResourceResult> seasons = JSONArray.parseArray(success.getJSONObject("data").getJSONArray("season").toJSONString(),MapiResourceResult.class);

                if(null!=leis&&leis.size()>0){
                    list.clear();
                    list.addAll(leis);
                    topPopWindow.refreshData(list);
                }

                if(null!=styles&&styles.size()>0){
                    styleList.clear();
                    styleList.addAll(styles);
                    stylePopWindow.refreshData(styleList);
                }

                if(null!=caizhis&&caizhis.size()>0){
                    materialList.clear();
                    materialList.addAll(caizhis);
                    materialPopWindow.refreshData(materialList);
                }

                if(null!=seasons&&seasons.size()>0){
                    seasonList.clear();
                    seasonList.addAll(seasons);
                    seasonPopWindow.refreshData(seasonList);
                }



            }
        }, new RequestExceptionCallback() {
            @Override
            public void error(Integer code, String message) {
                hideLoading();
                MainToast.showShortToast(message);
            }
        });
    }

    private void initListener() {

        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==0){
                    push = "1";
                    newStr = "0";
                    kol = "0";
                    star = "0";

                }else if(tab.getPosition()==1){
                    push = "0";
                    newStr = "1";
                    kol = "0";
                    star = "0";
                }else if(tab.getPosition()==2){
                    push = "0";
                    newStr = "0";
                    kol = "1";
                    star = "0";
                }else if(tab.getPosition()==3){
                    push = "0";
                    newStr = "0";
                    kol = "0";
                    star = "1";
                }
                refreshData();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        listSwipeRefreshLayout.setBestRefreshListener(new BestSwipeRefreshLayout.BestRefreshListener() {
            @Override
            public void onBestRefresh() {
                refreshData();
            }
        });

        girdSwipeRefreshLayout.setBestRefreshListener(new BestSwipeRefreshLayout.BestRefreshListener() {
            @Override
            public void onBestRefresh() {
                refreshData();
            }
        });

        seasonPopWindow.setOnPopItemClickListener(new RecyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                if (null != view) {
                    if (postion >= 0) {
                        filterStrs[3] = list.get(postion).getId();
                        radioHot.setText(seasonList.get(postion).getName());
                    } else {
                        filterStrs[3] = "";
                        radioHot.setText("季节");
                    }
                    refreshData();

                }
                bgColor.setVisibility(View.GONE);
            }
        });

        materialPopWindow.setOnPopItemClickListener(new RecyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                if (null != view) {
                    if (postion >= 0) {
                        filterStrs[2] = list.get(postion).getId();
                        radioStar.setText(materialList.get(postion).getName());
                    } else {
                        filterStrs[2] = "";
                        radioStar.setText("材质");
                    }
                    refreshData();
                }
                bgColor.setVisibility(View.GONE);
            }
        });

        stylePopWindow.setOnPopItemClickListener(new RecyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                if (null != view) {
                    if (postion >= 0) {
                        filterStrs[1] = list.get(postion).getId();
                        radioNew.setText(styleList.get(postion).getName());
                    } else {
                        filterStrs[1] = "";
                        radioNew.setText("风格");
                    }
                    refreshData();
                }
                bgColor.setVisibility(View.GONE);
            }
        });

        topPopWindow.setOnPopItemClickListener(new RecyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                if (null != view) {
                    if (postion >= 0) {
                        filterStrs[0] = list.get(postion).getId();
                        radioClothes.setText(list.get(postion).getName());
                    } else {
                        filterStrs[0] = "";
                        radioClothes.setText("类目");
                    }
                    refreshData();
                }
                bgColor.setVisibility(View.GONE);
            }
        });

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

                refreshData();
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
                        listSwipeRefreshLayout.setVisibility(View.VISIBLE);
                        girdSwipeRefreshLayout.setVisibility(View.GONE);
                    } else {
                        listSwipeRefreshLayout.setVisibility(View.GONE);
                        girdSwipeRefreshLayout.setVisibility(View.VISIBLE);
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
                ControllerUtil.go2PromptDetail(mList.get(position).getGoods_id());
            }
        });

        itemGridAdapter.setOnItemClickListener(new RecyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ControllerUtil.go2PromptDetail(mList.get(position).getGoods_id());
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if ((newState == RecyclerView.SCROLL_STATE_IDLE) && manager.findLastVisibleItemPosition() >= 0 && (manager.findLastVisibleItemPosition() == (manager.getItemCount() - 1))) {
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

                if ((newState == RecyclerView.SCROLL_STATE_IDLE) && manager.findLastVisibleItemPosition() >= 0 && (manager.findLastVisibleItemPosition() == (manager.getItemCount() - 1))) {
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
        if (counts == null || counts <= pageIndex) {
            MainToast.showShortToast("没有更多数据了");
            return;
        }
        pageIndex++;
        load();
    }

    public void refreshData() {
        if (null != mList) {
            mList.clear();
            pageIndex = 1;
            mAdapter.notifyDataSetChanged();
            itemGridAdapter.notifyDataSetChanged();
            load();
        }
    }

    public void load() {
        filters = "";
        for(String filter : filterStrs){
            if(TextUtils.isEmpty(filters)) {
                if(!TextUtils.isEmpty(filter))
                    filters = filter;
            }else{
                if(!TextUtils.isEmpty(filter))
                    filters += ","+filter;
            }
        }
        showLoading();
        ItemApi.list(this, "1", filters, "", searchEt.getText().toString(), star, kol, newStr, push, pageIndex + "","0", new RequestPageCallback<List<MapiItemResult>>() {
            @Override
            public void success(Integer isNext, List<MapiItemResult> success) {
                hideLoading();
                girdSwipeRefreshLayout.setRefreshing(false);
                listSwipeRefreshLayout.setRefreshing(false);
               /* if (isGrid) {
                    girdSwipeRefreshLayout.setRefreshing(false);
                } else {
                    listSwipeRefreshLayout.setRefreshing(false);
                }*/
                counts = isNext;
                DebugLog.i("success.size=>"+success.size());
                if (success.isEmpty())
                    return;
                mList.addAll(success);
                DebugLog.i("mList.size=>"+mList.size());
                mAdapter.notifyDataSetChanged();
                itemGridAdapter.notifyDataSetChanged();
            }
        }, new RequestExceptionCallback() {
            @Override
            public void error(Integer code, String message) {
                hideLoading();
                girdSwipeRefreshLayout.setRefreshing(false);
                listSwipeRefreshLayout.setRefreshing(false);
                /*if (isGrid) {
                    girdSwipeRefreshLayout.setRefreshing(false);
                } else {
                    listSwipeRefreshLayout.setRefreshing(false);
                }*/
                MainToast.showShortToast(message);
            }
        });

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
                if(null!=topPopWindow){
                    topPopWindow.showPopupWindow(view);
                    bgColor.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.radio_new:
                if(null!=stylePopWindow){
                    stylePopWindow.showPopupWindow(view);
                    bgColor.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.radio_star:
                if(null!=materialPopWindow){
                    materialPopWindow.showPopupWindow(view);
                    bgColor.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.radio_hot:
                if(null!=seasonPopWindow){
                    seasonPopWindow.showPopupWindow(view);
                    bgColor.setVisibility(View.VISIBLE);
                }
                break;
//            case R.id.search:
//                break;
        }
    }

}
