package com.yigu.house.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.yigu.commom.api.UserApi;
import com.yigu.commom.result.MapiUserResult;
import com.yigu.commom.util.RequestCallback;
import com.yigu.commom.util.RequestExceptionCallback;
import com.yigu.commom.widget.MainToast;
import com.yigu.house.R;
import com.yigu.house.base.BaseActivity;
import com.yigu.house.util.ControllerUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @Bind(R.id.phone)
    EditText phone;
    @Bind(R.id.pwd)
    EditText pwd;

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
                if (TextUtils.isEmpty(phone.getText().toString())) {
                    MainToast.showShortToast("请输入手机号");
                    return;
                }
                if (TextUtils.isEmpty(pwd.getText().toString())) {
                    MainToast.showShortToast("请输入密码");
                    return;
                }
                showLoading();
                UserApi.login(this, phone.getText().toString(), pwd.getText().toString(), new RequestCallback<MapiUserResult>() {
                    @Override
                    public void success(MapiUserResult success) {
                        hideLoading();
                        MainToast.showShortToast("登录成功");
                        userSP.saveUserBean(success);
                        ControllerUtil.go2Main();
                        finish();
                    }
                }, new RequestExceptionCallback() {
                    @Override
                    public void error(Integer code, String message) {
                        hideLoading();
                        MainToast.showShortToast(message);
                    }
                });

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
