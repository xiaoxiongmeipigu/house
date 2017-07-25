package com.yigu.house.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yigu.commom.api.CommonApi;
import com.yigu.commom.api.ItemApi;
import com.yigu.commom.result.IndexData;
import com.yigu.commom.result.MapiAddrResult;
import com.yigu.commom.result.MapiResourceResult;
import com.yigu.commom.util.RequestCallback;
import com.yigu.commom.util.RequestExceptionCallback;
import com.yigu.commom.widget.MainToast;
import com.yigu.house.R;
import com.yigu.house.adapter.logistics.LogisticsAdapter;
import com.yigu.house.base.BaseActivity;
import com.yigu.house.interfaces.RecyOnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2017/1/9.
 */
public class LogisticsLayout extends LinearLayout {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.price_tv)
    TextView priceTv;

    private Context mContext;
    private View view;
    private BaseActivity activity;

    List<MapiResourceResult> list = new ArrayList<>();
    List<IndexData> mList = new ArrayList<>();
    int count = 0;

    LogisticsAdapter mAdapter;

    MapiAddrResult mapiAddrResult = null;


    public void setMapiAddrResult(MapiAddrResult mapiAddrResult) {
        this.mapiAddrResult = mapiAddrResult;
    }

    public LogisticsLayout(Context context) {
        super(context);
        mContext = context;
        activity = (BaseActivity) mContext;
        initView();
    }

    public LogisticsLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        activity = (BaseActivity) mContext;
        initView();
    }

    public LogisticsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        activity = (BaseActivity) mContext;
        initView();
    }

    private void initView() {
        if (isInEditMode())
            return;
        view = LayoutInflater.from(mContext).inflate(R.layout.layout_logistics, this);
        ButterKnife.bind(this, view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new LogisticsAdapter(mContext, mList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        load();
        initListener();
    }

    public void load() {
        mList.clear();
        list.clear();
        mAdapter.notifyDataSetChanged();
        activity.showLoading();
        ItemApi.express(activity, new RequestCallback<List<MapiResourceResult>>() {
            @Override
            public void success(List<MapiResourceResult> success) {
                activity.hideLoading();
                if (null == success || success.isEmpty())
                    return;
                list.addAll(success);
                count = 0;
                int pos = 0;
                if (list == null || list.size() == 0) {
                    count = 0;
                } else {
                    for (MapiResourceResult ware : list) {
                        pos++;
                        mList.add(new IndexData(count++, "item", ware));
                        if (pos < list.size())
                            mList.add(new IndexData(count++, "divider", new Object()));
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        }, new RequestExceptionCallback() {
            @Override
            public void error(Integer code, String message) {
                activity.hideLoading();
                MainToast.showShortToast(message);
            }
        });

    }

    private void initListener() {
        mAdapter.setOnItemClickListener(new RecyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                if (null == mapiAddrResult) {
                    MainToast.showShortToast("请选择地址");
                    return;
                }

                for (int i = 0; i < mList.size(); i++) {
                    IndexData indexData = mList.get(i);
                    if (indexData.getType().equals("item")) {
                        MapiResourceResult mapiResourceResult = (MapiResourceResult) indexData.getData();

                        if (i == position) {
                            mapiResourceResult.setSel(true);
                            express_id = mapiResourceResult.getE_id();
                            getexpressprice(mapiAddrResult.getAddress_id(),express_id);
                        } else
                            mapiResourceResult.setSel(false);
                    }
                }

                mAdapter.notifyDataSetChanged();

            }
        });
    }

    public interface OnSelExpressListener {
        void selExpress(String id, String postage);
    }

    private OnSelExpressListener onSelExpressListener;

    public void setOnSelExpressListener(OnSelExpressListener onSelExpressListener) {
        this.onSelExpressListener = onSelExpressListener;
    }

    private String express_id;

    public void refreshPrice(String address_id){
        if(!TextUtils.isEmpty(express_id)){
            getexpressprice(address_id,express_id);
        }
    }

    private void getexpressprice(String address_id,final String express_id){
        activity.showLoading();
        CommonApi.getexpressprice(activity, address_id, express_id, new RequestCallback<String>() {
            @Override
            public void success(String success) {
                activity.hideLoading();
                priceTv.setText("¥ "+ (TextUtils.isEmpty(success)?"0":success));
                if (null != onSelExpressListener)
                    onSelExpressListener.selExpress(express_id,success);
            }
        }, new RequestExceptionCallback() {
            @Override
            public void error(Integer code, String message) {
                activity.hideLoading();
                MainToast.showShortToast("运费计算失败，请以实际支付金额为准");
            }
        });
    }

}
