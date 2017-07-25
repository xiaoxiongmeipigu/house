package com.yigu.house.fragment.person;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yigu.commom.api.UserApi;
import com.yigu.commom.application.AppContext;
import com.yigu.commom.result.MapiUserResult;
import com.yigu.commom.util.RequestCallback;
import com.yigu.commom.util.RequestExceptionCallback;
import com.yigu.commom.widget.MainToast;
import com.yigu.house.R;
import com.yigu.house.activity.MainActivity;
import com.yigu.house.base.BaseFrag;
import com.yigu.house.broadcast.ReceiverAction;
import com.yigu.house.util.ControllerUtil;
import com.yigu.house.util.JpushUtil;
import com.yigu.house.widget.MainAlertDialog;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link BaseFrag} subclass.
 */
public class PersonFragment extends BaseFrag {

    @Bind(R.id.shop_name)
    TextView shopName;
    @Bind(R.id.vip)
    TextView vip;

    MainAlertDialog exitDialog;
    MainAlertDialog vipDialog;


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

        if (userSP.getUserBean().getType().equals("1"))
            shopName.setText(TextUtils.isEmpty(userSP.getUserBean().getShop_name()) ? "" : userSP.getUserBean().getShop_name());
        else if (userSP.getUserBean().getType().equals("2"))
            shopName.setText(TextUtils.isEmpty(userSP.getUserBean().getVender_name()) ? "" : userSP.getUserBean().getVender_name());
        exitDialog = new MainAlertDialog(getActivity());
        exitDialog.setLeftBtnText("退出").setRightBtnText("取消").setTitle("确认退出？");

        vipDialog = new MainAlertDialog(getActivity());
        vipDialog.setLeftBtnText("确认").setRightBtnText("取消").setTitle("申请成为VIP，可享受的福利：\n" +
                "      . 可以使用互调功能\n" +
                "      . VIP专属现货批发  ");

    }

    @Override
    public void onResume() {
        super.onResume();
        notifyVip();
    }

    private void initListener() {
        exitDialog.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userSP.clearUserData();

                JpushUtil.getInstance().setAlias("");
                JpushUtil.getInstance().stopPush(AppContext.getInstance());

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

        vipDialog.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vipapply();
                vipDialog.dismiss();
            }
        });

        vipDialog.setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vipDialog.dismiss();
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.addr_ll, R.id.indent_ll, R.id.ll_order, R.id.modifyPsd, R.id.exit, R.id.vip})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addr_ll:
                ControllerUtil.go2AddrList();
                break;
            case R.id.indent_ll:
                ControllerUtil.go2IndentOrder();
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
            case R.id.vip:
                if (userSP.getUserBean().getIs_vip().equals("0"))
                    vipDialog.show();
                break;
        }
    }

    private void vipapply() {
        showLoading();
        UserApi.vipapply(getActivity(), new RequestCallback() {
            @Override
            public void success(Object success) {
                hideLoading();

                MapiUserResult mapiUserResult = userSP.getUserBean();
                mapiUserResult.setIs_vip("2");
                userSP.saveUserBean(mapiUserResult);
                notifyVip();
                MainToast.showShortToast("审核中，请耐心等待...");

            }
        }, new RequestExceptionCallback() {
            @Override
            public void error(Integer code, String message) {
                hideLoading();
                MainToast.showShortToast(message);
            }
        });
    }

    public void notifyVip(){
        if (null!=userSP.getUserBean()&&userSP.getUserBean().getIs_vip().equals("2")) {
            vip.setVisibility(View.VISIBLE);
            vip.setText("审核中");
        }else if(null!=userSP.getUserBean()&&userSP.getUserBean().getIs_vip().equals("0")){
            vip.setVisibility(View.VISIBLE);
            vip.setText("升级");
        }else if(null!=userSP.getUserBean()&&userSP.getUserBean().getIs_vip().equals("1")){
            vip.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != exitDialog && exitDialog.isShowing()) {
            exitDialog.dismiss();
            exitDialog = null;
        }
        if (null != vipDialog && vipDialog.isShowing()) {
            vipDialog.dismiss();
            vipDialog = null;
        }
    }

}
