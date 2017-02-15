package com.yigu.house.activity.purcase;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yigu.commom.result.IndexData;
import com.yigu.commom.result.MapiCarResult;
import com.yigu.commom.result.MapiItemResult;
import com.yigu.commom.util.DPUtil;
import com.yigu.commom.util.DebugLog;
import com.yigu.commom.widget.MainToast;
import com.yigu.house.R;
import com.yigu.house.adapter.purcase.PurcaseAdapter;
import com.yigu.house.base.BaseActivity;
import com.yigu.house.interfaces.AdapterSelListener;
import com.yigu.house.util.ControllerUtil;
import com.yigu.house.widget.BestSwipeRefreshLayout;
import com.yigu.house.widget.DividerListItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PurcaseActivity extends BaseActivity {


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

    List<IndexData> mList = new ArrayList<>();
    List<MapiCarResult> list = new ArrayList<>();
    int count;
    PurcaseAdapter mAdapter;
//    private ArrayList<MapiItemResult> delList;
    private ArrayList<MapiItemResult> selList;
    boolean isAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purcase);
        ButterKnife.bind(this);
        initView();
        initListener();
        load();
    }

    private void initView() {

//        delList = new ArrayList<>();
        selList = new ArrayList<>();

        back.setImageResource(R.mipmap.back);
        center.setText("进货单(0)");
        tvRight.setText("编辑");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
//        recyclerView.addItemDecoration(new DividerListItemDecoration(this, OrientationHelper.HORIZONTAL, DPUtil.dip2px(1), Color.parseColor("#eeeeee")));
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new PurcaseAdapter(this, mList);
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

    public void load(){

        MapiCarResult mapiCarResult = new MapiCarResult();
        MapiItemResult itemResult1 = new MapiItemResult();
        MapiItemResult itemResult2 = new MapiItemResult();
        MapiItemResult itemResult3 = new MapiItemResult();
        mapiCarResult.getItems().add(itemResult1);
        mapiCarResult.getItems().add(itemResult2);
        mapiCarResult.getItems().add(itemResult3);
        list.add(mapiCarResult);

        count = 0;
        if(list==null||list.size()==0){
            count = 0;
        }else{
            for (MapiCarResult ware : list) {
                mList.add(new IndexData(count++,"head",ware));
                for (int i=0;i<ware.getItems().size();i++) {
                    mList.add(new IndexData(count++,"item", ware.getItems().get(i)));
                }
                mList.add(new IndexData(count++,"divider", new Object()));
            }
        }
    }

    private void notifyAllStatus() {
        DebugLog.i("notifyAllStatus");
        selList.clear();
        isAll = true;
        for (IndexData indexData : mList) {
            if(indexData.getType().equals("head")){
                MapiCarResult mapiCartResult = (MapiCarResult) indexData.getData();
                if (!mapiCartResult.isSel()) {
                    DebugLog.i("notifyAllStatus==>head");
                    isAll = false;
                }
            }else if(indexData.getType().equals("item")){
                MapiItemResult mapiItemResult = (MapiItemResult) indexData.getData();
                if (!mapiItemResult.isSel()) {
                    DebugLog.i("notifyAllStatus==>item");
                    isAll = false;

                }else{
                    selList.add(mapiItemResult);
                }
            }
        }
//        all.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.sel_right, 0, 0, 0);
        if (isAll)
            all.setImageResource(R.mipmap.sel_right);
        else
            all.setImageResource(R.mipmap.sel);

    }

    @OnClick({R.id.all, R.id.order})
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
        }
    }

}
