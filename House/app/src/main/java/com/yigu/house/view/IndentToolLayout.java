package com.yigu.house.view;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.yigu.house.R;

import butterknife.ButterKnife;

/**
 * Created by brain on 2017/1/5.
 */
public class IndentToolLayout extends RelativeLayout{

    private Context mContext;
    private View view;

    public IndentToolLayout(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public IndentToolLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public IndentToolLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        if (isInEditMode())
            return;
        view = LayoutInflater.from(mContext).inflate(R.layout.layout_indent_tool, this);
        ButterKnife.bind(this, view);
        load();
        initListener();
    }

    public void load() {

    }

    private void initListener() {

    }


}
