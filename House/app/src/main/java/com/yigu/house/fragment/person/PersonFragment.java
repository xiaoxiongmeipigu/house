package com.yigu.house.fragment.person;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yigu.house.R;
import com.yigu.house.activity.MainActivity;
import com.yigu.house.base.BaseFrag;
import com.yigu.house.util.ControllerUtil;
import com.yigu.house.widget.MainAlertDialog;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link BaseFrag} subclass.
 */
public class PersonFragment extends BaseFrag {

    MainAlertDialog exitDialog;

    public PersonFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_person, container, false);
        ButterKnife.bind(this, view);
        initView();
        initListener();
        return view;
    }

    private void initView() {
        exitDialog = new MainAlertDialog(getActivity());
        exitDialog.setLeftBtnText("退出").setRightBtnText("取消").setTitle("确认退出？");
    }

    private void initListener(){
        exitDialog.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userSP.clearUserData();
                Intent i = new Intent(getActivity(), MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("type", 3);
                startActivity(i);
                exitDialog.dismiss();
            }
        });

        exitDialog.setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitDialog.dismiss();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.addr_ll, R.id.indent_ll,R.id.ll_order,R.id.modifyPsd,R.id.exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addr_ll:
                ControllerUtil.go2AddrList();
                break;
            case R.id.indent_ll:
                ControllerUtil.go2IndentResul();
                break;
            case R.id.ll_order:
                ControllerUtil.go2OrderList();
                break;
            case R.id.modifyPsd:
                ControllerUtil.go2ModifyPsd();
                break;
            case R.id.exit:
                exitDialog.show();
                break;
        }
    }
}
