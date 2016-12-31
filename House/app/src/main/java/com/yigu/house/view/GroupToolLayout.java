package com.yigu.house.view;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yigu.commom.util.DebugLog;
import com.yigu.house.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by brain on 2016/12/14.
 */
public class GroupToolLayout extends RelativeLayout {

    @Bind(R.id.market_price)
    TextView marketPrice;
    @Bind(R.id.selSizeLL)
    LinearLayout selSizeLL;

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
        load();
        initListener();
    }

    public void load() {

    }

    private void initListener() {

    }


    @OnClick(R.id.selSizeLL)
    public void onClick() {
        DebugLog.i("PromptToolLayout=>selSizeLL");
        if(null!=sizeOpenListener)
            sizeOpenListener.open();
    }

    public SizeOpenListener sizeOpenListener;

    public interface SizeOpenListener{
        void open();
    }

    public void setSizeOpenListener(SizeOpenListener sizeOpenListener){
        this.sizeOpenListener = sizeOpenListener;
    }




}
