package com.yigu.house.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yigu.house.R;
import com.yigu.house.base.BaseActivity;
import com.yigu.house.util.ControllerUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.center)
    TextView center;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        back.setImageResource(R.mipmap.back);
        center.setText("申请");
    }

    @OnClick({R.id.back, R.id.ll_company, R.id.ll_personal})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.ll_company:
                ControllerUtil.go2CompanyRegister();
                break;
            case R.id.ll_personal:
                ControllerUtil.go2PersonalRegister();
                break;
        }
    }
}
