package com.yigu.commom.api;

import android.app.Activity;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yigu.commom.result.MapiCarResult;
import com.yigu.commom.result.MapiItemResult;
import com.yigu.commom.result.MapiResourceResult;
import com.yigu.commom.result.MapiUserResult;
import com.yigu.commom.util.DebugLog;
import com.yigu.commom.util.MapiUtil;
import com.yigu.commom.util.RequestCallback;
import com.yigu.commom.util.RequestExceptionCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by brain on 2016/6/16.
 */
public class UserApi extends BasicApi{

    public static void login(Activity activity, String phone, String pwd,final RequestCallback callback, final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("phone",phone);
        params.put("pwd",pwd);
        MapiUtil.getInstance().call(activity,loginUrl,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                MapiUserResult result = JSONObject.parseObject(json.getJSONObject("data").toJSONString(),MapiUserResult.class);
                callback.success(result);
            }
        },new MapiUtil.MapiFailResponse(){

            @Override
            public void fail(Integer code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });
    }

   /* public static void userRegister(Activity activity,){

    }*/

    /**
     * 获取运营类型列表
     * @param activity
     * @param callback
     * @param exceptionCallback
     */
    public static void businesslist(Activity activity,final RequestCallback callback, final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        MapiUtil.getInstance().call(activity,businesslist,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                List<MapiResourceResult> result = JSONArray.parseArray(json.getJSONArray("data").toJSONString(),MapiResourceResult.class);
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
     * 获取验证码
     * @param activity
     * @param phone
     * @param callback
     * @param exceptionCallback
     */
    public static void getverify(Activity activity,String phone,String type,final RequestCallback callback, final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("mobile",phone);
        params.put("type",type);
        MapiUtil.getInstance().call(activity,getverify,params,new MapiUtil.MapiSuccessResponse(){
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
     * 注册
     * @param activity
     * @param type
     * @param business_type
     * @param shop_name
     * @param user_name
     * @param phone
     * @param mark
     * @param vender_name
     * @param address
     * @param business_licence
     * @param callback
     * @param exceptionCallback
     */
    public static void user_register(Activity activity,String type,String business_type,String shop_name,String user_name,String phone,String mark,String vender_name,
                                     String address,String business_licence,final RequestCallback callback,final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("type",type);
        if(!TextUtils.isEmpty(business_type))
            params.put("business_type",business_type);
        if(!TextUtils.isEmpty(shop_name))
            params.put("shop_name",shop_name);
        if(!TextUtils.isEmpty(user_name))
            params.put("user_name",user_name);
        if(!TextUtils.isEmpty(phone))
            params.put("phone",phone);
        if(!TextUtils.isEmpty(mark))
            params.put("mark",mark);
        if(!TextUtils.isEmpty(vender_name))
            params.put("vender_name",vender_name);
        if(!TextUtils.isEmpty(address))
             params.put("address",address);
        if(!TextUtils.isEmpty(business_licence))
            params.put("business_licence",business_licence);
        DebugLog.i(params.toString());
        MapiUtil.getInstance().call(activity,user_register,params,new MapiUtil.MapiSuccessResponse(){
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
     *修改密码
     * @param activity
     * @param phone
     * @param pwd
     * @param mark
     * @param callback
     * @param exceptionCallback
     */
    public static void changephone(Activity activity,String phone,String pwd,String mark,final RequestCallback callback,final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("phone",phone);
        params.put("pwd",pwd);
        params.put("mark",mark);
        MapiUtil.getInstance().call(activity,changephone,params,new MapiUtil.MapiSuccessResponse(){
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
     *忘记密码
     * @param activity
     * @param phone
     * @param pwd
     * @param mark
     * @param callback
     * @param exceptionCallback
     */
    public static void forgetpasswd(Activity activity,String phone,String pwd,String mark,final RequestCallback callback,final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("mobile",phone);
        params.put("pwd",pwd);
        params.put("mark",mark);
        MapiUtil.getInstance().call(activity,forgetpasswd,params,new MapiUtil.MapiSuccessResponse(){
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
     * 申请VIP
     * @param activity
     * @param callback
     * @param exceptionCallback
     */
    public static void vipapply(Activity activity,final RequestCallback callback,final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        MapiUtil.getInstance().call(activity,vipapply,params,new MapiUtil.MapiSuccessResponse(){
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
     * 修改密码
     * @param activity
     * @param old_password
     * @param new_password
     * @param callback
     * @param exceptionCallback
     */
    public static void modify_password(Activity activity,String old_password,String new_password,final RequestCallback callback,final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("old_password",old_password);
        params.put("new_password",new_password);
        MapiUtil.getInstance().call(activity,modify_password,params,new MapiUtil.MapiSuccessResponse(){
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

}
