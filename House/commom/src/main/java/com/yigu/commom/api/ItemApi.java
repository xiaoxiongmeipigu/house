package com.yigu.commom.api;

import android.app.Activity;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yigu.commom.result.MapiAddrResult;
import com.yigu.commom.result.MapiCarResult;
import com.yigu.commom.result.MapiItemResult;
import com.yigu.commom.result.MapiOrderDetailResult;
import com.yigu.commom.result.MapiOrderResult;
import com.yigu.commom.result.MapiResourceResult;
import com.yigu.commom.result.MapiUserResult;
import com.yigu.commom.util.DebugLog;
import com.yigu.commom.util.MapiUtil;
import com.yigu.commom.util.RequestCallback;
import com.yigu.commom.util.RequestExceptionCallback;
import com.yigu.commom.util.RequestPageCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by brain on 2017/2/24.
 */
public class ItemApi extends BasicApi{

    public static void main(Activity activity, final RequestCallback callback, final RequestExceptionCallback exceptionCallback){

        Map<String,String> params = new HashMap<>();
        MapiUtil.getInstance().call(activity,main,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                callback.success(json);
            }
        },new MapiUtil.MapiFailResponse(){
            @Override
            public void fail(Integer code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });

    }

    /**
     * 列表页
     * @param activity
     * @param type
     *              跳转列表页需要传递固定值 1=>现货 2=>拼单 3=>下单
     * @param filter
     *               以英文逗号隔开的筛选ID
     * @param color
     * @param title
     *              模糊搜索使用
     * @param kol
     * @param star
     * @param newStr
     * @param push
     * @param page
     *              默认为1
     * @param callback
     * @param exceptionCallback
     */
    public static void list(Activity activity,String type, String filter, String color, String title, String star, String kol, String newStr, String push, String page,String is_vip,
                            final RequestPageCallback callback, final RequestExceptionCallback exceptionCallback){

        Map<String,String> params = new HashMap<>();
        params.put("type",type);
        if(!TextUtils.isEmpty(filter))
            params.put("filter",filter);
        if(!TextUtils.isEmpty(color))
            params.put("color",color);
        params.put("title",title);
        if(!TextUtils.isEmpty(kol))
            params.put("kol",kol);
        if(!TextUtils.isEmpty(star))
             params.put("star",star);
        if(!TextUtils.isEmpty(newStr))
            params.put("new",newStr);
        if(!TextUtils.isEmpty(push))
            params.put("push",push);
        params.put("page",page);
        if(!TextUtils.isEmpty(is_vip))
            params.put("is_vip",is_vip);
        MapiUtil.getInstance().call(activity,list,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                List<MapiItemResult> result = JSONArray.parseArray(json.getJSONObject("data").getJSONArray("list").toJSONString(),MapiItemResult.class);
                Integer count = json.getJSONObject("data").getInteger("totalpage");
                if(null!=count){
                    callback.success(count,result);
                }

            }
        },new MapiUtil.MapiFailResponse(){
            @Override
            public void fail(Integer code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });

    }

    /**
     * 商品详情
     * @param activity
     * @param goods_id
     *                  商品ID
     * @param callback
     * @param exceptionCallback
     */
    public static void detail(Activity activity,String goods_id ,final RequestCallback callback, final RequestExceptionCallback exceptionCallback){

        Map<String,String> params = new HashMap<>();
        params.put("goods_id",goods_id);
        MapiUtil.getInstance().call(activity,detail,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                callback.success(json);
            }
        },new MapiUtil.MapiFailResponse(){
            @Override
            public void fail(Integer code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });

    }

    /**
     * 加入购物车
     * @param activity
     * @param goods_id
     * @param attr_id
     * @param callback
     * @param exceptionCallback
     */
    public static  void addCart(Activity activity,String goods_id,String attr_id ,final RequestCallback callback,final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("goods_id",goods_id);
        params.put("attr_id",attr_id);
        MapiUtil.getInstance().call(activity,addCart,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                callback.success(json);
            }
        },new MapiUtil.MapiFailResponse(){
            @Override
            public void fail(Integer code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });
    }

    /**
     * 购物车列表
     * @param activity
     * @param callback
     * @param exceptionCallback
     */
    public static void carlist(Activity activity,final RequestCallback callback,final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        MapiUtil.getInstance().call(activity,carlist,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                Gson gson = new Gson();
                List<MapiCarResult> result = gson.fromJson(json.getJSONArray("data").toJSONString(), new TypeToken<List<MapiCarResult>>(){}.getType());
                if(null==result){
                    result = new ArrayList<>();
                    callback.success(result);
                }else
                    callback.success(result);

            }
        },new MapiUtil.MapiFailResponse(){
            @Override
            public void fail(Integer code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });
    }

    /**
     * 编辑购物车
     * @param activity
     * @param cart_id
     * @param num
     * @param callback
     * @param exceptionCallback
     */
    public static void editcart(Activity activity,String cart_id,String num,final RequestCallback callback,final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("cart_id",cart_id);
        params.put("num",num);
        MapiUtil.getInstance().call(activity,editcart,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                callback.success(json);
            }
        },new MapiUtil.MapiFailResponse(){
            @Override
            public void fail(Integer code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });
    }

    /**
     * 获取快递列表
     * @param activity
     * @param callback
     * @param exceptionCallback
     */
    public static void express(Activity activity,final RequestCallback callback, final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        MapiUtil.getInstance().call(activity,express,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                List<MapiResourceResult> result = JSONArray.parseArray(json.getJSONArray("data").toJSONString(),MapiResourceResult.class);
                callback.success(result);
            }
        },new MapiUtil.MapiFailResponse(){
            @Override
            public void fail(Integer code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });
    }

    /**
     * 获取默认收货地址
     * @param activity
     * @param callback
     * @param exceptionCallback
     */
    public static void default_address(Activity activity,final RequestCallback callback, final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        MapiUtil.getInstance().call(activity,default_address,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                MapiAddrResult result = JSONObject.parseObject(json.getJSONObject("data").toJSONString(),MapiAddrResult.class);
                callback.success(result);
            }
        },new MapiUtil.MapiFailResponse(){

            @Override
            public void fail(Integer code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });
    }

    /**
     * 提交订单
     * @param activity
     * @param cart_ids
     * @param express_id
     * @param address_id
     * @param callback
     * @param exceptionCallback
     */
    public static void orderconfirmation(Activity activity,String cart_ids,String express_id,String address_id,String pay_type,
                                         final RequestCallback callback, final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("cart_ids",cart_ids);
        params.put("express_id",express_id);
        params.put("address_id",address_id);
        params.put("pay_type",pay_type);
        MapiUtil.getInstance().call(activity,orderconfirmation,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                callback.success(json);
            }
        },new MapiUtil.MapiFailResponse(){
            @Override
            public void fail(Integer code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });

    }

    /**
     * 获取订单列表
     * @param activity
     * @param page
     * @param status
     * @param callback
     * @param exceptionCallback
     */
    public static void orderList(Activity activity,String page,String status,final RequestPageCallback callback,final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("page",page);
        params.put("status",status);
        MapiUtil.getInstance().call(activity,orderList,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                List<MapiOrderResult> result = JSONArray.parseArray(json.getJSONObject("data").getJSONArray("list").toJSONString(),MapiOrderResult.class);
                Integer count = json.getJSONObject("data").getInteger("total");
                if(null!=count){
                    callback.success(count,result);
                }

            }
        },new MapiUtil.MapiFailResponse(){
            @Override
            public void fail(Integer code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });
    }


    /**
     * 获取订单支付数据
     * @param activity
     * @param order_id
     * @param pay_type
     * @param callback
     * @param exceptionCallback
     */
    public static void ordergetpaydata(Activity activity,String order_id,String pay_type,
                                         final RequestCallback callback, final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("order_id",order_id);
        params.put("pay_type",pay_type);
        MapiUtil.getInstance().call(activity,ordergetpaydata,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                callback.success(json);
            }
        },new MapiUtil.MapiFailResponse(){
            @Override
            public void fail(Integer code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });

    }

    /**
     * 删除订单
     * @param activity
     * @param order_id
     * @param callback
     * @param exceptionCallback
     */
    public static void orderdelete(Activity activity,String order_id,final RequestCallback callback, final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("order_id",order_id);
        MapiUtil.getInstance().call(activity,orderdelete,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                callback.success(json);
            }
        },new MapiUtil.MapiFailResponse(){
            @Override
            public void fail(Integer code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });
    }

    /**
     * 直接下单
     * @param activity
     * @param goods_id
     * @param attr_id
     * @param callback
     * @param exceptionCallback
     */
    public static void buynow(Activity activity,String goods_id,String attr_id,String is_exchange,final RequestCallback callback,final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("goods_id",goods_id);
        params.put("attr_id",attr_id);
        if(!TextUtils.isEmpty(is_exchange))
            params.put("is_exchange",is_exchange);
        MapiUtil.getInstance().call(activity,buynow,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                Gson gson = new Gson();
                List<MapiCarResult> result = gson.fromJson(json.getJSONArray("data").toJSONString(), new TypeToken<List<MapiCarResult>>(){}.getType());
                if(null==result){
                    result = new ArrayList<>();
                    callback.success(result);
                }else
                    callback.success(result);

            }
        },new MapiUtil.MapiFailResponse(){
            @Override
            public void fail(Integer code,String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });
    }

    /**
     * 购物车删除
     * @param activity
     * @param cart_id
     * @param callback
     * @param exceptionCallback
     */
    public static void cartdel(Activity activity,String cart_id,final RequestCallback callback,final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("cart_id",cart_id);
        MapiUtil.getInstance().call(activity,cartdel,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                callback.success(json);
            }
        },new MapiUtil.MapiFailResponse(){
            @Override
            public void fail(Integer code,String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });
    }

    /**
     * 我的下单列表
     * @param activity
     * @param page
     * @param callback
     * @param exceptionCallback
     */
    public static void ordersamplelist(Activity activity,String page, final RequestPageCallback callback, final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("page",page);
        MapiUtil.getInstance().call(activity,ordersamplelist,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                List<MapiOrderResult> result = JSONArray.parseArray(json.getJSONObject("data").getJSONArray("list").toJSONString(),MapiOrderResult.class);
                Integer count = json.getJSONObject("data").getInteger("total");
                if(null!=count){
                    callback.success(count,result);
                }

            }
        },new MapiUtil.MapiFailResponse(){
            @Override
            public void fail(Integer code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });
    }

    /**
     *样衣批量下单
     * @param activity
     * @param goods_id
     * @param num
     * @param sample_img
     * @param express_id
     * @param address_id
     * @param pay_type
     * @param callback
     * @param exceptionCallback
     */
    public static void sampleorderconfirmation(Activity activity,String goods_id,String num,String sample_img,String express_id,String address_id,String pay_type,
                                               final RequestCallback callback, final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("goods_id",goods_id);
        params.put("num",num);
        params.put("sample_img",sample_img);
        params.put("express_id",express_id);
        params.put("address_id",address_id);
        params.put("pay_type",pay_type);
        MapiUtil.getInstance().call(activity,sampleorderconfirmation,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                callback.success(json);
            }
        },new MapiUtil.MapiFailResponse(){
            @Override
            public void fail(Integer code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });

    }

    /**
     * 系统公告消息列表
     * @param activity
     * @param page
     * @param limit
     * @param callback
     * @param exceptionCallback
     */
    public static void message(Activity activity,String page,String limit,final RequestPageCallback callback, final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("page",page);
        params.put("limit",limit);
        MapiUtil.getInstance().call(activity,message,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                List<MapiItemResult> result = JSONArray.parseArray(json.getJSONObject("data").getJSONArray("list").toJSONString(),MapiItemResult.class);
                Integer count = json.getJSONObject("data").getInteger("total_page");
                if(null!=count){
                    callback.success(count,result);
                }

            }
        },new MapiUtil.MapiFailResponse(){
            @Override
            public void fail(Integer code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });
    }

    /**
     * 订单详情
     * @param activity
     * @param order_id
     * @param callback
     * @param exceptionCallback
     */
    public static void orderdetail(Activity activity,String order_id,final RequestCallback callback, final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("order_id",order_id);
        MapiUtil.getInstance().call(activity,orderdetail,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                MapiOrderDetailResult result = JSONObject.parseObject(json.getJSONObject("data").toJSONString(),MapiOrderDetailResult.class);
                callback.success(result);
            }
        },new MapiUtil.MapiFailResponse(){
            @Override
            public void fail(Integer code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });
    }

}
