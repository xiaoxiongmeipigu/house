package com.yigu.house.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.yigu.house.R;
import com.yigu.house.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.center)
    TextView center;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        back.setImageResource(R.mipmap.back);
        center.setText("来样定制");
    }

    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }
}
