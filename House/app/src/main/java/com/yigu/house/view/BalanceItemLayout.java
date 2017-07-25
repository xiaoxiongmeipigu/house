package com.yigu.house.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.yigu.commom.result.IndexData;
import com.yigu.commom.result.MapiCarResult;
import com.yigu.commom.result.MapiItemResult;
import com.yigu.house.R;
import com.yigu.house.adapter.BalanceItemAdapter;
import com.yigu.house.adapter.ItemAdapter;
import com.yigu.house.adapter.purcase.PurcaseAdapter;
import com.yigu.house.interfaces.RecyOnItemClickListener;
import com.yigu.house.util.ControllerUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2017/4/13.
 */
public class BalanceItemLayout extends RelativeLayout {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private Context mContext;
    private View view;
    int count;
    List<IndexData> mList = new ArrayList<>();
    ArrayList<MapiCarResult> list = new ArrayList<>();
    BalanceItemAdapter mAdapter;

    public BalanceItemLayout(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public BalanceItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public BalanceItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        if (isInEditMode())
            return;
        view = LayoutInflater.from(mContext).inflate(R.layout.layout_balance_item, this);
        ButterKnife.bind(this, view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
//        recyclerView.addItemDecoration(new DividerListItemDecoration(this, OrientationHelper.HORIZONTAL, DPUtil.dip2px(1), Color.parseColor("#eeeeee")));
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new BalanceItemAdapter(mContext, mList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        initListener();
    }

    private void initListener() {

    }

    public void load(List<MapiCarResult> list) {
        refreshData();
        initData(list);
    }

    public void refreshData() {
        if (null != mList) {
            mList.clear();
            mAdapter.notifyDataSetChanged();
        }
    }

    private void initData(List<MapiCarResult> data) {
        if (null == data || data.isEmpty())
            return;
        list.addAll(data);
        int heatCount=0;
        count = 0;
        if (list == null || list.size() == 0) {
            count = 0;
        } else {
            for (MapiCarResult ware : list) {
                heatCount++;
                mList.add(new IndexData(count++, "head", ware));
                for (int i = 0; i < ware.getList().size(); i++) {
                    if("1".equals(ware.getGoods_type())||"2".equals(ware.getGoods_type()))
                        mList.add(new IndexData(count++, "item", ware.getList().get(i)));
                    else if("3".equals(ware.getGoods_type()))
                        mList.add(new IndexData(count++, "item_three", ware.getList().get(i)));
                }
                if (heatCount <= list.size() - 1)
                    mList.add(new IndexData(count++, "divider", new Object()));
            }
        }
        mAdapter.notifyDataSetChanged();
    }

}
