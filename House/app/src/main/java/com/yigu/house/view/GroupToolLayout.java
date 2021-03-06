package com.yigu.house.view;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yigu.commom.result.MapiItemResult;
import com.yigu.commom.util.DebugLog;
import com.yigu.commom.widget.MainToast;
import com.yigu.house.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;

/**
 * Created by brain on 2016/12/14.
 */
public class GroupToolLayout extends RelativeLayout {

    @Bind(R.id.market_price)
    TextView marketPrice;
    @Bind(R.id.selSizeLL)
    LinearLayout selSizeLL;
    @Bind(R.id.countdownView)
    CountdownView countdownView;
    @Bind(R.id.price)
    TextView price;
    @Bind(R.id.terms)
    TextView terms;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.sell_count)
    TextView sellCount;
    @Bind(R.id.location)
    TextView location;
    @Bind(R.id.size_tv)
    TextView sizeTv;

    private Context mContext;
    private View view;

    public GroupToolLayout(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public GroupToolLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public GroupToolLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        if (isInEditMode())
            return;
        view = LayoutInflater.from(mContext).inflate(R.layout.layout_group_tool, this);
        ButterKnife.bind(this, view);
        marketPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        initListener();
    }

    public void load(MapiItemResult mapiItemResult) {

        if (null != mapiItemResult) {
            long endTime = Long.parseLong(TextUtils.isEmpty(mapiItemResult.getDuration_time()) ? "0" : mapiItemResult.getDuration_time());
            countdownView.start(endTime);
            countdownView.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
                @Override
                public void onEnd(CountdownView cv) {
                    MainToast.showShortToast("结束了");
                }
            });
            title.setText(mapiItemResult.getTitle());
            price.setText(TextUtils.isEmpty(mapiItemResult.getPrice())?"0":mapiItemResult.getPrice());
            terms.setText(TextUtils.isEmpty(mapiItemResult.getTerms())?"1件起批":mapiItemResult.getTerms()+"件起批");
            sellCount.setText("已成交"+(TextUtils.isEmpty(mapiItemResult.getSell_count())?"0":mapiItemResult.getSell_count())+"笔");
//            expressFee.setText("运费 ¥"+(TextUtils.isEmpty(mapiItemResult.getExpress_fee())?"0":mapiItemResult.getExpress_fee()));
            location.setText("发货地："+(TextUtils.isEmpty(mapiItemResult.getLocation())?"":mapiItemResult.getLocation()));
//            oldPrice.setText(TextUtils.isEmpty(mapiItemResult.getOld_price())?"":"原价："+mapiItemResult.getOld_price());
            sizeTv.setText(TextUtils.isEmpty(mapiItemResult.getArrtStr())?"请选择规格":mapiItemResult.getArrtStr());
        }


    }

    private void initListener() {

    }


    @OnClick(R.id.selSizeLL)
    public void onClick() {
        DebugLog.i("PromptToolLayout=>selSizeLL");
        if (null != sizeOpenListener)
            sizeOpenListener.open();
    }

    public SizeOpenListener sizeOpenListener;

    public interface SizeOpenListener {
        void open();
    }

    public void setSizeOpenListener(SizeOpenListener sizeOpenListener) {
        this.sizeOpenListener = sizeOpenListener;
    }

}
