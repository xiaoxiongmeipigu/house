package com.yigu.house.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.polites.android.GestureImageView;
import com.yigu.commom.result.MapiImageResult;
import com.yigu.house.R;
import com.yigu.house.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShowImageActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.image_zoom_progressbar)
    ProgressBar imageZoomProgressbar;
    @Bind(R.id.pager_img)
    GestureImageView pagerImg;
    @Bind(R.id.tv_right)
    TextView tvRight;


    DisplayImageOptions options = null;

    MapiImageResult mapiImageResult;
    boolean isDel = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);
        ButterKnife.bind(this);

        if (null != getIntent()) {
            mapiImageResult = (MapiImageResult) getIntent().getSerializableExtra("item");
            isDel = getIntent().getBooleanExtra("isDel", false);
        }
        if (null != mapiImageResult) {
            initView();
            initOptions();
        }

    }

    private void initView() {
        back.setImageResource(R.mipmap.back);
        if (isDel) {
            tvRight.setText("删除");
            tvRight.setVisibility(View.VISIBLE);
        } else {
            tvRight.setText("");
            tvRight.setVisibility(View.GONE);
        }

        String img = "";
        if(isDel)
            img = mapiImageResult.getImg();
        else
            img = mapiImageResult.getShow_pic();

        pagerImg.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ImageLoader.getInstance().displayImage(
                img, pagerImg, options, new ImageLoadingListener() {

                    @Override
                    public void onLoadingCancelled(String arg0, View arg1) {
                        imageZoomProgressbar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
                        imageZoomProgressbar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
                        imageZoomProgressbar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadingStarted(String arg0, View arg1) {
                        imageZoomProgressbar.setVisibility(View.VISIBLE);
                    }

                }
        );

    }

    private void initOptions() {
        options = new DisplayImageOptions.Builder()
                //				.showImageOnLoading(R.drawable.design_default)
                .showImageForEmptyUri(R.mipmap.img_big_default)
                .showImageOnFail(R.mipmap.img_big_default)
                .resetViewBeforeLoading(true)
                .imageScaleType(ImageScaleType.NONE)//EXACTLY
                .considerExifParams(true)
                .displayer(new SimpleBitmapDisplayer())//Bitmap.Config.RGB_565
                .bitmapConfig(Bitmap.Config.ARGB_8888).cacheInMemory(true).cacheOnDisc(true).build();
    }

    @Override
    protected void onDestroy() {
        //退出当前页面清除内存
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.clearMemoryCache();
        super.onDestroy();
    }

    @OnClick({R.id.back, R.id.tv_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_right:
                setResult(RESULT_OK);
                finish();
                break;
        }
    }
}
