package com.yigu.house.fragment.purcase;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yigu.commom.result.IndexData;
import com.yigu.commom.result.MapiCarResult;
import com.yigu.commom.result.MapiItemResult;
import com.yigu.commom.util.DebugLog;
import com.yigu.house.R;
import com.yigu.house.adapter.purcase.PurcaseAdapter;
import com.yigu.house.base.BaseFrag;
import com.yigu.house.interfaces.AdapterSelListener;
import com.yigu.house.util.ControllerUtil;
import com.yigu.house.widget.BestSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link BaseFrag} subclass.
 */
public class PurcaseFragment extends BaseFrag {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.center)
    TextView center;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.swipeRefreshLayout)
    BestSwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.price)
    TextView price;
    @Bind(R.id.all)
    ImageView all;
    @Bind(R.id.allPriceLL)
    LinearLayout allPriceLL;
    @Bind(R.id.order)
    TextView order;

    List<IndexData> mList = new ArrayList<>();
    List<MapiCarResult> list = new ArrayList<>();
    int count;
    PurcaseAdapter mAdapter;
    //    private ArrayList<MapiItemResult> delList;
    private ArrayList<MapiItemResult> selList;
    boolean isAll;

    public PurcaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_purcase, container, false);
        ButterKnife.bind(this, view);
        initView();
        initListener();
        load();
        return view;
    }

    private void initView() {

        //        delList = new ArrayList<>();
        selList = new ArrayList<>();

        center.setText("进货单(0)");
        tvRight.setText("编辑");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
//        recyclerView.addItemDecoration(new DividerListItemDecoration(this, OrientationHelper.HORIZONTAL, DPUtil.dip2px(1), Color.parseColor("#eeeeee")));
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new PurcaseAdapter(getActivity(), mList);
        recyclerView.setAdapter(mAdapter);

    }

    private void initListener() {
        swipeRefreshLayout.setBestRefreshListener(new BestSwipeRefreshLayout.BestRefreshListener() {
            @Override
            public void onBestRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        mAdapter.setOnAdapterSelListener(new AdapterSelListener() {
            @Override
            public void isAll() {
                notifyAllStatus();
            }

            @Override
            public void notifyParentStatus(int position) {
                MapiCarResult ware = (MapiCarResult) mList.get(position).getData();
                ware.setSel(!ware.isSel());
                for (MapiItemResult item : ware.getItems()) {
                    item.setSel(ware.isSel());
                }
                mAdapter.notifyDataSetChanged();
                notifyAllStatus();
            }

            @Override
            public void notifyChildStatus(int position) {
                MapiItemResult item = (MapiItemResult) mList.get(position).getData();
                item.setSel(!item.isSel());
                mAdapter.notifyDataSetChanged();
                notifyAllStatus();
            }

            @Override
            public void notifyChildNum(int position, int num) {

            }
        });

    }

    public void load() {
        list.clear();
        mList.clear();
        MapiCarResult mapiCarResult = new MapiCarResult();
        MapiItemResult itemResult1 = new MapiItemResult();
        MapiItemResult itemResult2 = new MapiItemResult();
        MapiItemResult itemResult3 = new MapiItemResult();
        mapiCarResult.getItems().add(itemResult1);
        mapiCarResult.getItems().add(itemResult2);
        mapiCarResult.getItems().add(itemResult3);

        MapiCarResult mapiCarResult2 = new MapiCarResult();
        MapiItemResult itemResul4 = new MapiItemResult();
        mapiCarResult2.getItems().add(itemResul4);
        list.add(mapiCarResult);
        list.add(mapiCarResult2);
        int heatCount=0;
        count = 0;
        if (list == null || list.size() == 0) {
            count = 0;
        } else {
            for (MapiCarResult ware : list) {
                heatCount++;
                mList.add(new IndexData(count++, "head", ware));
                for (int i = 0; i < ware.getItems().size(); i++) {
                    mList.add(new IndexData(count++, "item", ware.getItems().get(i)));
                }
                if (heatCount <= list.size() - 1)
                    mList.add(new IndexData(count++, "divider", new Object()));
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void notifyAllStatus() {
        DebugLog.i("notifyAllStatus");
        selList.clear();
        isAll = true;
        for (IndexData indexData : mList) {
            if (indexData.getType().equals("head")) {
                MapiCarResult mapiCartResult = (MapiCarResult) indexData.getData();
                if (!mapiCartResult.isSel()) {
                    DebugLog.i("notifyAllStatus==>head");
                    isAll = false;
                }
            } else if (indexData.getType().equals("item")) {
                MapiItemResult mapiItemResult = (MapiItemResult) indexData.getData();
                if (!mapiItemResult.isSel()) {
                    DebugLog.i("notifyAllStatus==>item");
                    isAll = false;

                } else {
                    selList.add(mapiItemResult);
                }
            }
        }

        if (isAll)
            all.setImageResource(R.mipmap.sel_right);
        else
            all.setImageResource(R.mipmap.sel);

    }

    @OnClick({R.id.all, R.id.order,R.id.tv_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.all:
                for (IndexData indexData : mList) {
                    if(indexData.getType().equals("head")){
                        MapiCarResult mapiCartResult = (MapiCarResult) indexData.getData();
                        mapiCartResult.setSel(!isAll);

                    }else if(indexData.getType().equals("item")){
                        MapiItemResult mapiItemResult = (MapiItemResult) indexData.getData();
                        mapiItemResult.setSel(!isAll);

                    }
                }
                isAll = !isAll;
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.order:
                ControllerUtil.go2Balance();
                break;
            case R.id.tv_right:
                if("编辑".equals(tvRight.getText().toString())){
                    tvRight.setText("完成");
                    order.setText("删除");
                    allPriceLL.setVisibility(View.INVISIBLE);
                    mAdapter.setDel(true);
                }else if("完成".equals(tvRight.getText().toString())){
                    tvRight.setText("编辑");
                    order.setText("结算");
                    allPriceLL.setVisibility(View.VISIBLE);
                    mAdapter.setDel(false);
                }
                selList.clear();
                for (IndexData indexData : mList) {
                    if(indexData.getType().equals("head")){
                        MapiCarResult mapiCartResult = (MapiCarResult) indexData.getData();
                        mapiCartResult.setSel(false);

                    }else if(indexData.getType().equals("item")){
                        MapiItemResult mapiItemResult = (MapiItemResult) indexData.getData();
                        mapiItemResult.setSel(false);

                    }
                }
                isAll = false;
                mAdapter.notifyDataSetChanged();
                break;
        }
    }
}
