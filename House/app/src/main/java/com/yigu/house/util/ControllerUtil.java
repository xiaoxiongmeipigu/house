package com.yigu.house.util;

import android.content.Intent;

import com.yigu.commom.application.AppContext;
import com.yigu.house.activity.BalanceActivity;
import com.yigu.house.activity.CompanyRegisterActivity;
import com.yigu.house.activity.CustomActivity;
import com.yigu.house.activity.ForgetPsdActivity;
import com.yigu.house.activity.LoginActivity;
import com.yigu.house.activity.MainActivity;
import com.yigu.house.activity.ModifyPsdActivity;
import com.yigu.house.activity.PersonalRegisterActivity;
import com.yigu.house.activity.RegisterActivity;
import com.yigu.house.activity.addr.AddAddrActivity;
import com.yigu.house.activity.addr.AddrListActivity;
import com.yigu.house.activity.group.GroupDetailActivity;
import com.yigu.house.activity.group.GroupListActivity;
import com.yigu.house.activity.indent.IndentBalanceActivity;
import com.yigu.house.activity.indent.IndentDetailActivity;
import com.yigu.house.activity.indent.IndentListActivity;
import com.yigu.house.activity.indent.IndentResultActivity;
import com.yigu.house.activity.order.OrderListActivity;
import com.yigu.house.activity.prompt.PromptDetailActivity;
import com.yigu.house.activity.prompt.PromptListActivity;
import com.yigu.house.adapter.group.GroupListAdapter;


/**
 * Created by brain on 2016/6/22.
 */
public class ControllerUtil {

    /**
     * 由主页进入现货
     */
    public static void go2PromptList() {
        Intent intent = new Intent(AppContext.getInstance(), PromptListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 由现货进入详情
     */
    public static void go2PromptDetail() {
        Intent intent = new Intent(AppContext.getInstance(), PromptDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 由主页进入拼单
     */
    public static void go2GroupList() {
        Intent intent = new Intent(AppContext.getInstance(), GroupListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 由拼单进入详情
     */
    public static void go2GroupDetail() {
        Intent intent = new Intent(AppContext.getInstance(), GroupDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 由主页进入来样定制
     */
    public static void go2Custom() {
        Intent intent = new Intent(AppContext.getInstance(),CustomActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 由个人进入地址
     */
    public static void go2AddrList() {
        Intent intent = new Intent(AppContext.getInstance(), AddrListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 添加地址
     */
    public static void go2AddAddr() {
        Intent intent = new Intent(AppContext.getInstance(), AddAddrActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 由主页进入订单
     */
    public static void go2IndentList() {
        Intent intent = new Intent(AppContext.getInstance(), IndentListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 由订单进入详情
     */
    public static void go2IndentDetail() {
        Intent intent = new Intent(AppContext.getInstance(), IndentDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 登录
     */
    public static void go2Login() {
        Intent intent = new Intent(AppContext.getInstance(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 首页
     */
    public static void go2Main() {
        Intent intent = new Intent(AppContext.getInstance(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 注册
     */
    public static void go2Register() {
        Intent intent = new Intent(AppContext.getInstance(), RegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 由注册进入个人申请
     */
    public static void go2PersonalRegister() {
        Intent intent = new Intent(AppContext.getInstance(), PersonalRegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 由注册进入公司申请
     */
    public static void go2CompanyRegister() {
        Intent intent = new Intent(AppContext.getInstance(), CompanyRegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 由现货进入详情
     */
    public static void go2ForgetPsd() {
        Intent intent = new Intent(AppContext.getInstance(),ForgetPsdActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 我的下单
     */
    public static void go2IndentResul() {
        Intent intent = new Intent(AppContext.getInstance(), IndentResultActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 批量结算
     */
    public static void go2IndentBalance() {
        Intent intent = new Intent(AppContext.getInstance(), IndentBalanceActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 结算
     */
    public static void go2Balance() {
        Intent intent = new Intent(AppContext.getInstance(), BalanceActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 订单列表
     */
    public static void go2OrderList() {
        Intent intent = new Intent(AppContext.getInstance(), OrderListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 修改密码
     */
    public static void go2ModifyPsd() {
        Intent intent = new Intent(AppContext.getInstance(), ModifyPsdActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

}
