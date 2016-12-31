package com.yigu.house.util;


import com.yigu.commom.result.MapiResourceResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brain on 2016/7/29.
 */
public class JGJDataSource {


    public static final int TYPE_XIANHUO = 0x01;
    public static final int TYPE_XIANDAN = 0x02;
    public static final int TYPE_HUODONG = 0x03;
    public static final int TYPE_PINTUAN = 0x04;
    public static final int TYPE_DINGZHI = 0x05;
    public static final int TYPE_TUIHUO = 0x06;
    public static final int TYPE_VIP = 0x07;
    public static final int TYPE_MORE = 0x08;
    /**
     * 获取所长/站长功能菜单
     * @return
     */
    public static List<MapiResourceResult> getMainResource(){
        List<MapiResourceResult> list = new ArrayList<>();
        list.add(new MapiResourceResult(TYPE_XIANHUO,"现货"));
        list.add(new MapiResourceResult(TYPE_XIANDAN,"订单"));
        list.add(new MapiResourceResult(TYPE_HUODONG,"活动"));
        list.add(new MapiResourceResult(TYPE_PINTUAN,"拼单"));
        list.add(new MapiResourceResult(TYPE_DINGZHI,"来样定制"));
        list.add(new MapiResourceResult(TYPE_TUIHUO,"退货"));
        list.add(new MapiResourceResult(TYPE_VIP,"VIP"));
        list.add(new MapiResourceResult(TYPE_MORE,"更多"));
        return list;
    }
}
