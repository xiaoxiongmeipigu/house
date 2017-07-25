package com.yigu.house.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yigu.commom.result.MapiItemResult;
import com.yigu.house.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2017/1/5.
 */
public class IndentToolLayout extends RelativeLayout {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.price)
    TextView price;
    @Bind(R.id.date)
    TextView date;

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
        initListener();
    }

    public void load(MapiItemResult mapiItemResult) {
        if (null != mapiItemResult) {
            title.setText(mapiItemResult.getTitle());
            price.setText(TextUtils.isEmpty(mapiItemResult.getPrice())?"0":mapiItemResult.getPrice());

            if(TextUtils.isEmpty(mapiItemResult.getOutput_time())||"0".equals(mapiItemResult.getOutput_time())){
                date.setText("当天出样");
            }else{
                date.setText(mapiItemResult.getOutput_time()+"天出样");
            }

        }
    }

    private void initListener() {

    }


}
