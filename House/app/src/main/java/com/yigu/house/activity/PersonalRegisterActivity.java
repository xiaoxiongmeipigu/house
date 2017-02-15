package com.yigu.house.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yigu.house.R;
import com.yigu.house.base.BaseActivity;
import com.yigu.house.widget.ItemDialog;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonalRegisterActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.center)
    TextView center;
    @Bind(R.id.request_code)
    TextView requestCode;

    ItemDialog itemDialog;
    @Bind(R.id.type_tv)
    TextView typeTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_register);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initView() {
        back.setImageResource(R.mipmap.back);
        center.setText("个人申请");

        if (itemDialog == null)
            itemDialog = new ItemDialog(this, R.style.image_dialog_theme);

    }

    private void initListener() {
        itemDialog.setDialogItemClickListner(new ItemDialog.DialogItemClickListner() {
            @Override
            public void onItemClick(View view, int type) {
                if (type == 0)
                    typeTv.setText("淘宝商家");
                if (type == 1)
                    typeTv.setText("微商");

            }
        });
    }

    @OnClick({R.id.back, R.id.request_code, R.id.apply, R.id.reset,R.id.type_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.request_code:
                break;
            case R.id.apply:
                break;
            case R.id.reset:
                break;
            case R.id.type_tv:
                itemDialog.showDialog();
                break;
        }
    }
}
