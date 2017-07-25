package com.yigu.house.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.soundcloud.android.crop.Crop;
import com.yigu.commom.api.BasicApi;
import com.yigu.commom.api.CommonApi;
import com.yigu.commom.api.UserApi;
import com.yigu.commom.result.MapiImageResult;
import com.yigu.commom.util.DPUtil;
import com.yigu.commom.util.DebugLog;
import com.yigu.commom.util.FileUtil;
import com.yigu.commom.util.JGJBitmapUtils;
import com.yigu.commom.util.RequestCallback;
import com.yigu.commom.util.RequestExceptionCallback;
import com.yigu.commom.util.SMSUtils;
import com.yigu.commom.util.StringUtil;
import com.yigu.commom.widget.MainToast;
import com.yigu.house.R;
import com.yigu.house.base.BaseActivity;
import com.yigu.house.base.RequestCode;
import com.yigu.house.util.ControllerUtil;
import com.yigu.house.widget.PhotoDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CompanyRegisterActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.center)
    TextView center;
    @Bind(R.id.image_tv)
    TextView imageTv;
    @Bind(R.id.vender_name)
    EditText venderName;
    @Bind(R.id.user_name)
    EditText userName;
    @Bind(R.id.address)
    EditText address;
    @Bind(R.id.phone)
    EditText phone;
    @Bind(R.id.mark)
    EditText markEt;
    @Bind(R.id.request_code)
    TextView requestCode;
    @Bind(R.id.image)
    SimpleDraweeView image;

    /**
     * 短信验证倒计时--时长
     */
    private int i = 60;
    SMSUtils.EventHandler eventHandler;
    PhotoDialog photoDialog;
    ArrayList<MapiImageResult> imgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_register);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        back.setImageResource(R.mipmap.back);
        center.setText("公司申请");

        imgs = new ArrayList<>();

    }

    @OnClick({R.id.back, R.id.request_code,R.id.image,R.id.apply, R.id.reset,R.id.licence_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.request_code:
                String phoneStr = phone.getText().toString();

                if(TextUtils.isEmpty(phoneStr)){
                    MainToast.showShortToast("请输入手机号");
                    return;
                }

                if(!StringUtil.isMobile(phoneStr)){
                    MainToast.showShortToast("手机号格式不正确！");
                    return;
                }

                requestCode();
                break;
            case R.id.image:
                ControllerUtil.go2ShowImage(imgs.get(0),false);
                break;
            case R.id.apply:
                if(TextUtils.isEmpty(venderName.getText().toString())){
                    MainToast.showShortToast("请输入公司名称");
                    return;
                }
                if(TextUtils.isEmpty(userName.getText().toString())){
                    MainToast.showShortToast("请输入姓名");
                    return;
                }
                if(TextUtils.isEmpty(address.getText().toString())){
                    MainToast.showShortToast("请输入地址");
                    return;
                }
                if(TextUtils.isEmpty(phone.getText().toString())){
                    MainToast.showShortToast("请输入手机号");
                    return;
                }
                if(TextUtils.isEmpty(markEt.getText().toString())){
                    MainToast.showShortToast("请输入验证码");
                    return;
                }

                if(null==imgs||imgs.isEmpty()){
                    MainToast.showShortToast("请上传营业执照");
                    return;
                }

                showLoading();
                UserApi.user_register(this, "2", "", "", userName.getText().toString(), phone.getText().toString(), markEt.getText().toString(),
                        venderName.getText().toString(), address.getText().toString(), imgs.get(0).getPic(), new RequestCallback() {
                            @Override
                            public void success(Object success) {
                                hideLoading();
                                MainToast.showShortToast("正在审核，请耐心等待...");
                                finish();
                            }
                        }, new RequestExceptionCallback() {
                            @Override
                            public void error(Integer code, String message) {
                                hideLoading();
                                MainToast.showShortToast(message);
                            }
                        });

                break;
            case R.id.reset:

                venderName.setText("");
                userName.setText("");
                address.setText("");
                phone.setText("");
                markEt.setText("");
                imgs.clear();
                imageTv.setVisibility(View.VISIBLE);
                image.setImageURI("");
                image.setVisibility(View.GONE);
                break;
            case R.id.licence_ll:
                if (photoDialog == null)
                    photoDialog = new PhotoDialog(this, R.style.image_dialog_theme);
                photoDialog.setImagePath("daily_image.jpg");
                photoDialog.showDialog();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        DebugLog.i("requestCode=" + requestCode + "resultCode=" + resultCode);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RequestCode.CAMERA:
                    File picture = FileUtil.createFile(this, "daily_image.jpg", FileUtil.TYPE_IMAGE);
                    startPhotoZoom(Uri.fromFile(picture));
                    break;
                case RequestCode.PICTURE:
                    if (data != null)
                        startPhotoZoom(data.getData());
                    break;
                case Crop.REQUEST_CROP: //缩放以后
                    if (data != null) {
                        File picture2 = FileUtil.createFile(this, "daily_image_crop.jpg", FileUtil.TYPE_IMAGE);
                        String filename = picture2.getAbsolutePath();
//                        Bitmap bitmap = BitmapFactory.decodeFile(filename);
//                        JGJBitmapUtils.saveBitmap2file(bitmap, filename);
                        if (JGJBitmapUtils.rotateBitmapByDegree(filename, filename, JGJBitmapUtils.getBitmapDegree(filename))) {
                            uploadImage(picture2);
                        } else
                            DebugLog.i("图片保存失败");
                    }
                    break;
            }
        }
    }

    private void uploadImage(File file) {
        showLoading();
        CommonApi.uploadImage(this, file, new RequestCallback<MapiImageResult>() {
            @Override
            public void success(MapiImageResult success) {
                hideLoading();
                if (null != success) {
                    imgs.add(success);
                    image.setVisibility(View.VISIBLE);
                    imageTv.setVisibility(View.GONE);
//                    image.setImageURI(BasicApi.BASIC_IMAGE + Uri.parse(mList.get(0).getPATH()));

                    //创建将要下载的图片的URI
                    Uri imageUri = Uri.parse(imgs.get(0).getShow_pic());
                    ImageRequest request = ImageRequestBuilder.newBuilderWithSource(imageUri)
                            .setResizeOptions(new ResizeOptions(DPUtil.dip2px(70), DPUtil.dip2px(70)))
                            .build();
                    DraweeController controller = Fresco.newDraweeControllerBuilder()
                            .setImageRequest(request)
                            .setOldController( image.getController())
                            .setControllerListener(new BaseControllerListener<ImageInfo>())
                            .build();
                    image.setController(controller);

                }
            }
        }, new RequestExceptionCallback() {
            @Override
            public void error(Integer code, String message) {
                hideLoading();
                DebugLog.i(message);
            }
        });
    }

    /**
     * 裁剪图片
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Uri outUrl = Uri
                .fromFile(FileUtil.createFile(this, "daily_image_crop.jpg", FileUtil.TYPE_IMAGE));
        Crop.of(uri, outUrl).asSquare().withMaxSize(600, 600).start(this);
    }

    /**
     * 向服务器请求验证码
     */
    private void requestCode() {
        SMSUtils.requestCode(this,phone.getText().toString(),"1");
        // 把按钮变成不可点击，并且显示倒计时（正在获取）
        requestCode.setClickable(false);
        requestCode.setFocusableInTouchMode(false);
        requestCode.setBackgroundDrawable(getResources().getDrawable(R.drawable.rect_soild_dark_orange_round_15));
        requestCode.setText("重新发送(" + i + ")");
        initHandler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (; i > 0; i--) {
                    handler.sendEmptyMessage(-9);
                    if (i <= 0) {
                        i = 30;
                        break;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                handler.sendEmptyMessage(-8);
            }
        }).start();
    }

    private void initHandler(){
        eventHandler = new SMSUtils.EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                msg.what = -7;
                handler.sendMessage(msg);
            }
        };
        // 注册回调监听接口
        SMSUtils.registerEventHandler(eventHandler);
    }

    String mark = "";

    /**
     * 处理服务器返回的信息
     */
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case -9:
                    requestCode.setText("重新发送(" + i + ")");
                    break;
                case -8:
                    requestCode.setText("获取验证码");
                    requestCode.setClickable(true);
                    requestCode.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_pressed_round_15_color_orange));
                    i = 60;
                    break;
                case -7:
                    int event = msg.arg1;
                    int result = msg.arg2;
                    Object data = msg.obj;
                    DebugLog.e("event=" + event);
                    if (result == SMSUtils.RESULT_COMPLETE) {
                        if (event == SMSUtils.EVENT_GET_VERIFICATION_CODE) {
                            mark = (String) data;
                            MainToast.showShortToast("验证码已经发送");
                        }
                    }else if(result == SMSUtils.RESULT_ERROR){
                        if (event == SMSUtils.EVENT_GET_VERIFICATION_CODE_ERROR) {
                            MainToast.showShortToast((String) data);

                        }
                    }
                    break;
                default:
                    break;
            }

        }
    };

    @Override
    protected void onDestroy() {
        if(null!=eventHandler)
            SMSUtils.unregisterEventHandler(eventHandler);
//        if(null!=smsBroadcastReceiver)
//            unregisterReceiver(smsBroadcastReceiver);
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("list", imgs);
        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        imgs = (ArrayList<MapiImageResult>) savedInstanceState.getSerializable("list");
    }

}
