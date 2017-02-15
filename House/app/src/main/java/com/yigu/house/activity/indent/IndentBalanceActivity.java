package com.yigu.house.activity.indent;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yigu.house.R;
import com.yigu.house.base.BaseActivity;
import com.yigu.house.view.LogisticsLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IndentBalanceActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.center)
    TextView center;
    @Bind(R.id.image)
    SimpleDraweeView image;
    @Bind(R.id.logisticLayout)
    LogisticsLayout logisticLayout;
    @Bind(R.id.price)
    TextView price;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.addr_ll)
    LinearLayout addrLL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indent_balance);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        back.setImageResource(R.mipmap.back);
        center.setText("结算");
        addrLL.setFocusable(true);
        addrLL.setFocusableInTouchMode(true);
        addrLL.requestFocus();
    }

    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }
}
