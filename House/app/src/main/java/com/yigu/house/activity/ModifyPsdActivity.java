package com.yigu.house.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yigu.commom.api.UserApi;
import com.yigu.commom.util.RequestCallback;
import com.yigu.commom.util.RequestExceptionCallback;
import com.yigu.commom.widget.MainToast;
import com.yigu.house.R;
import com.yigu.house.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ModifyPsdActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.center)
    TextView center;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.old_password)
    EditText oldPassword;
    @Bind(R.id.new_password)
    EditText newPassword;
    @Bind(R.id.new_two_password)
    EditText newTwoPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_psd);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        back.setImageResource(R.mipmap.back);
        center.setText("修改密码");
        tvRight.setText("确认");
    }

    @OnClick({R.id.back, R.id.tv_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_right:
                String oldPsd = oldPassword.getText().toString();
                String newPsd = newPassword.getText().toString();
                String newTwoPsd = newTwoPassword.getText().toString();

                if(TextUtils.isEmpty(oldPsd)){
                    MainToast.showShortToast("请输入您的旧密码");
                    return;
                }

                if(TextUtils.isEmpty(newPsd)){
                    MainToast.showShortToast("请输入您的新密码");
                    return;
                }

                if(TextUtils.isEmpty(newTwoPsd)){
                    MainToast.showShortToast("请再次输入您的新密码");
                    return;
                }

                if(!newPsd.equals(newTwoPsd)){
                    MainToast.showShortToast("两次新密码输入不一致");
                    return;
                }

                modify(oldPsd,newPsd);

                break;
        }
    }

    private void modify(String oldPsd,String newPsd){
        showLoading();
        UserApi.modify_password(this, oldPsd, newPsd, new RequestCallback() {
            @Override
            public void success(Object success) {
                hideLoading();
                MainToast.showShortToast("密码修成成功");
                finish();
            }
        }, new RequestExceptionCallback() {
            @Override
            public void error(Integer code, String message) {
                hideLoading();
                MainToast.showShortToast(message);
            }
        });
    }

}
