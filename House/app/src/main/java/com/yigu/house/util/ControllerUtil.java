package com.yigu.house.util;

import android.content.Intent;

import com.yigu.commom.application.AppContext;
import com.yigu.house.activity.CustomActivity;
import com.yigu.house.activity.addr.AddAddrActivity;
import com.yigu.house.activity.addr.AddrListActivity;
import com.yigu.house.activity.group.GroupDetailActivity;
import com.yigu.house.activity.group.GroupListActivity;
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

}
