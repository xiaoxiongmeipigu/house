package com.yigu.house.util;

import android.content.Intent;

import com.yigu.commom.application.AppContext;
import com.yigu.commom.result.MapiCarResult;
import com.yigu.commom.result.MapiCartItemResult;
import com.yigu.commom.result.MapiImageResult;
import com.yigu.commom.result.MapiOrderResult;
import com.yigu.house.activity.BalanceActivity;
import com.yigu.house.activity.CompanyRegisterActivity;
import com.yigu.house.activity.CustomActivity;
import com.yigu.house.activity.ForgetPsdActivity;
import com.yigu.house.activity.GuideActivity;
import com.yigu.house.activity.LoginActivity;
import com.yigu.house.activity.MainActivity;
import com.yigu.house.activity.ModifyPsdActivity;
import com.yigu.house.activity.PersonalRegisterActivity;
import com.yigu.house.activity.RegisterActivity;
import com.yigu.house.activity.SelPayActivity;
import com.yigu.house.activity.ShowImageActivity;
import com.yigu.house.activity.addr.AddAddrActivity;
import com.yigu.house.activity.addr.AddrListActivity;
import com.yigu.house.activity.group.GroupDetailActivity;
import com.yigu.house.activity.group.GroupListActivity;
import com.yigu.house.activity.indent.IndentBalanceActivity;
import com.yigu.house.activity.indent.IndentDetailActivity;
import com.yigu.house.activity.indent.IndentListActivity;
import com.yigu.house.activity.indent.IndentOrderActivity;
import com.yigu.house.activity.indent.IndentResultActivity;
import com.yigu.house.activity.notice.NoticeActivity;
import com.yigu.house.activity.order.OrderDetailActivity;
import com.yigu.house.activity.order.OrderListActivity;
import com.yigu.house.activity.prompt.PromptDetailActivity;
import com.yigu.house.activity.prompt.PromptListActivity;
import com.yigu.house.activity.purcase.PurcaseActivity;
import com.yigu.house.activity.vip.VipDetailActivity;
import com.yigu.house.activity.vip.VipListActivity;
import com.yigu.house.activity.webview.WebviewControlActivity;
import com.yigu.house.adapter.group.GroupListAdapter;
import com.yigu.house.adapter.order.OrderListAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by brain on 2016/6/22.
 */
public class ControllerUtil {

    /**
     * 由主页进入现货
     */
    public static void go2PromptList(String search) {
        Intent intent = new Intent(AppContext.getInstance(), PromptListActivity.class);
        intent.putExtra("search",search);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 由现货进入详情
     */
    public static void go2PromptDetail(String goods_id) {
        Intent intent = new Intent(AppContext.getInstance(), PromptDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("goods_id",goods_id);
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
    public static void go2GroupDetail(String goods_id) {
        Intent intent = new Intent(AppContext.getInstance(), GroupDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("goods_id",goods_id);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 由拼单进入VIP详情
     */
    public static void go2VipDetail(String goods_id) {
        Intent intent = new Intent(AppContext.getInstance(), VipDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("goods_id",goods_id);
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
    public static void go2IndentDetail(String goods_id) {
        Intent intent = new Intent(AppContext.getInstance(), IndentDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("goods_id",goods_id);
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
     * 引导页
     */
    public static void go2Guide() {
        Intent intent = new Intent(AppContext.getInstance(), GuideActivity.class);
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
    public static void go2IndentBalance(MapiCarResult mapiCarResult) {
        Intent intent = new Intent(AppContext.getInstance(), IndentBalanceActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("item",mapiCarResult);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 结算
     */
    public static void go2Balance(ArrayList<MapiCartItemResult> itemList,ArrayList<MapiCarResult> carList) {
        Intent intent = new Intent(AppContext.getInstance(), BalanceActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("itemList",itemList);
        intent.putExtra("carList",carList);
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

    /**
     * 购物车
     */
    public static void go2Purcase() {
        Intent intent = new Intent(AppContext.getInstance(), PurcaseActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 查看图片
     */
    public static void go2ShowImage(MapiImageResult mapiImageResult,boolean isDel) {
        Intent intent = new Intent(AppContext.getInstance(), ShowImageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("item",mapiImageResult);
        intent.putExtra("isDel",isDel);
        AppContext.getInstance().startActivity(intent);
    }


    /**
     * 由主页进入Vip
     */
    public static void go2VipList(String search) {
        Intent intent = new Intent(AppContext.getInstance(), VipListActivity.class);
        intent.putExtra("search",search);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 选择支付方式
     */
    public static void go2SelPay(MapiOrderResult mapiOrderResult) {
        Intent intent = new Intent(AppContext.getInstance(), SelPayActivity.class);
        intent.putExtra("item",mapiOrderResult);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 进入我的订单
     */
    public static void go2OrderList(int  pos) {
        Intent intent = new Intent(AppContext.getInstance(), OrderListActivity.class);
        intent.putExtra("fragIndex",pos);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 我的下单列表
     */
    public static void go2IndentOrder() {
        Intent intent = new Intent(AppContext.getInstance(), IndentOrderActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 活动
     */
    public static void go2Notice() {
        Intent intent = new Intent(AppContext.getInstance(), NoticeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 订单详情
     */
    public static void go2OrderDetail(String order_id) {
        Intent intent = new Intent(AppContext.getInstance(), OrderDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("order_id",order_id);
        AppContext.getInstance().startActivity(intent);
    }

}
