package com.yigu.house.fragment.prompt;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.yigu.commom.result.MapiItemResult;
import com.yigu.commom.result.MapiResourceResult;
import com.yigu.commom.util.DPUtil;
import com.yigu.commom.widget.MainToast;
import com.yigu.house.R;
import com.yigu.house.adapter.ItemAdapter;
import com.yigu.house.adapter.ItemGridAdapter;
import com.yigu.house.base.BaseFrag;
import com.yigu.house.interfaces.RecyOnItemClickListener;
import com.yigu.house.util.ControllerUtil;
import com.yigu.house.widget.DividerListItemDecoration;
import com.yigu.house.widget.FilterPopWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class FrompComFragment extends BaseFrag {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.gridRecyclerView)
    RecyclerView gridRecyclerView;

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
    public FrompComFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_prompt_com, container, false);
        ButterKnife.bind(this, view);
        initView();
        initGridView();
        if (isGrid) {
            recyclerView.setVisibility(View.GONE);
            gridRecyclerView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            gridRecyclerView.setVisibility(View.GONE);
        }

        initListener();
        load();
        return view;
    }

    private void initView() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new ItemAdapter(getActivity(), mList);
        recyclerView.setAdapter(mAdapter);

        list.clear();
        MapiResourceResult mapiResourceResult = new MapiResourceResult();
        mapiResourceResult.setName("毛衣");
        list.add(mapiResourceResult);
        MapiResourceResult mapiResourceResult2 = new MapiResourceResult();
        mapiResourceResult2.setName("明星款线衣");
        MapiResourceResult mapiResourceResult3 = new MapiResourceResult();
        mapiResourceResult3.setName("青少年T恤");
        list.add(mapiResourceResult2);
        list.add(mapiResourceResult3);
        topPopWindow = new FilterPopWindow(getActivity(), 0, list, R.style.PopupWindowAnimation);

        topPopWindow.setOnPopItemClickListener(new RecyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                if (null != view) {
                    if (postion >= 0) {
                        radioClothes.setText(list.get(postion).getName());
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
        mapiResourceResult5.setName("黑色");
        MapiResourceResult mapiResourceResult6 = new MapiResourceResult();
        mapiResourceResult6.setName("白色");
        MapiResourceResult mapiResourceResult7 = new MapiResourceResult();
        mapiResourceResult7.setName("藏青色");
        MapiResourceResult mapiResourceResult8 = new MapiResourceResult();
        mapiResourceResult8.setName("红色");
        MapiResourceResult mapiResourceResult9 = new MapiResourceResult();
        mapiResourceResult9.setName("黄色");
        MapiResourceResult mapiResourceResult17 = new MapiResourceResult();
        mapiResourceResult17.setName("绿色");
        MapiResourceResult mapiResourceResult18 = new MapiResourceResult();
        mapiResourceResult18.setName("橙色");
        MapiResourceResult mapiResourceResult19 = new MapiResourceResult();
        mapiResourceResult19.setName("紫色");
        colorList.add(mapiResourceResult5);
        colorList.add(mapiResourceResult6);
        colorList.add(mapiResourceResult7);
        colorList.add(mapiResourceResult8);
        colorList.add(mapiResourceResult9);
        colorList.add(mapiResourceResult17);
        colorList.add(mapiResourceResult18);
        colorList.add(mapiResourceResult19);

        colorPopWindow = new FilterPopWindow(getActivity(), 0, colorList, R.style.PopupWindowAnimation);

        colorPopWindow.setOnPopItemClickListener(new RecyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                if (null != view) {
                    if (postion >= 0) {
                        radioNew.setText(colorList.get(postion).getName());
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
        mapiResourceResult10.setName("布料");
        styleList.add(mapiResourceResult10);
        MapiResourceResult mapiResourceResult11  = new MapiResourceResult();
        mapiResourceResult11.setName("毛线");
        MapiResourceResult mapiResourceResult12 = new MapiResourceResult();
        mapiResourceResult12.setName("涤纶");
        styleList.add(mapiResourceResult11);
        styleList.add(mapiResourceResult12);

        stylePopWindow = new FilterPopWindow(getActivity(), 0, styleList, R.style.PopupWindowAnimation);

        stylePopWindow.setOnPopItemClickListener(new RecyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                if (null != view) {
                    if (postion >= 0) {
                        radioStar.setText(styleList.get(postion).getName());
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
        mapiResourceResult13.setName("春");
        MapiResourceResult mapiResourceResult14 = new MapiResourceResult();
        mapiResourceResult14.setName("夏");
        MapiResourceResult mapiResourceResult15 = new MapiResourceResult();
        mapiResourceResult15.setName("秋");
        MapiResourceResult mapiResourceResult16 = new MapiResourceResult();
        mapiResourceResult16.setName("冬");
        tianqiList.add(mapiResourceResult13);
        tianqiList.add(mapiResourceResult14);
        tianqiList.add(mapiResourceResult15);
        tianqiList.add(mapiResourceResult16);
        tianqiPopWindow = new FilterPopWindow(getActivity(), 0, tianqiList, R.style.PopupWindowAnimation);

        tianqiPopWindow.setOnPopItemClickListener(new RecyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                if (null != view) {
                    if (postion >= 0) {
                        radioHot.setText(tianqiList.get(postion).getName());
                    } else {
                        radioHot.setText("季节");
                    }
                    refreshData();

                }
                bgColor.setVisibility(View.GONE);
            }
        });

    }

    private void initGridView() {

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        gridRecyclerView.setLayoutManager(manager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        gridRecyclerView.setHasFixedSize(true);
        gridRecyclerView.addItemDecoration(new DividerListItemDecoration(getActivity(), OrientationHelper.HORIZONTAL, DPUtil.dip2px(8), getResources().getColor(R.color.background_gray)));
        gridRecyclerView.addItemDecoration(new DividerListItemDecoration(getActivity(), OrientationHelper.VERTICAL, DPUtil.dip2px(10), getResources().getColor(R.color.background_gray)));
        itemGridAdapter = new ItemGridAdapter(getActivity(), mList);
        gridRecyclerView.setAdapter(itemGridAdapter);
    }

    private void initListener() {
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

    @Override
    public void onResume() {
        super.onResume();
    }

    public void load() {


    }

    public void isGrid(boolean b) {
        isGrid = b;
        if (null != recyclerView && null != gridRecyclerView) {
            if (isGrid) {
                recyclerView.setVisibility(View.GONE);
                gridRecyclerView.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setVisibility(View.VISIBLE);
                gridRecyclerView.setVisibility(View.GONE);
            }
        }

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

    @OnClick({R.id.radio_clothes, R.id.radio_new, R.id.radio_star, R.id.radio_hot})
    public void onClick(View view) {
        switch (view.getId()) {
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
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
