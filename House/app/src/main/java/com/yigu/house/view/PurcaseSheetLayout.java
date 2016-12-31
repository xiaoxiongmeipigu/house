package com.yigu.house.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.yigu.commom.util.RequestCallback;
import com.yigu.house.R;
import com.yigu.house.base.BaseActivity;

import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by brain on 2016/9/8.
 */
public class PurcaseSheetLayout extends RelativeLayout {
    @Bind(R.id.cut)
    TextView cut;
    @Bind(R.id.count)
    TextView count;
    @Bind(R.id.add)
    TextView add;
    private Context mContext;
    private View view;
    private int num = 1;

    String rec_id;
    BaseActivity activity;

    public PurcaseSheetLayout(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public PurcaseSheetLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public PurcaseSheetLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        if (isInEditMode())
            return;
        view = LayoutInflater.from(mContext).inflate(R.layout.layout_purcase_sheet, this);
        ButterKnife.bind(this, view);
        count.setText(num+"");
    }

    public void load() {

    }

    private void cut() {
        if(num-1<1){
            num = 1;
            count.setText(num+"");
        }else{
            count.setText(--num+"");
        }

    }

    private void add() {
        count.setText(++num+"");
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
        count.setText(num+"");
    }

    public void setRec_id(String rec_id) {
        this.rec_id = rec_id;
    }

    public void setActivity(BaseActivity activity) {
        this.activity = activity;
    }

    @OnClick({R.id.cut, R.id.count, R.id.add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cut:
                cut();
                break;
            case R.id.count:
                break;
            case R.id.add:
                add();
                break;
        }
    }

    NumInterface numInterface;

    public interface NumInterface{
        void modify(View view, int num, String price);
    }

    public void setNumListener(NumInterface numInterface){
        this.numInterface = numInterface;
    }

}
