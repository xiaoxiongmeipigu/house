package com.yigu.house.activity;

import android.os.Bundle;
import android.view.View;

import com.yigu.commom.result.MapiUserResult;
import com.yigu.house.R;
import com.yigu.house.base.BaseActivity;
import com.yigu.house.util.ControllerUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

    }

    @OnClick({R.id.login, R.id.forget, R.id.register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                MapiUserResult userResult = new MapiUserResult();
                userResult.setUSER_ID("测试");
                userSP.saveUserBean(userResult);
                ControllerUtil.go2Main();
                finish();
                break;
            case R.id.forget:
                ControllerUtil.go2ForgetPsd();
                break;
            case R.id.register:
                ControllerUtil.go2Register();
                break;
        }
    }
}
