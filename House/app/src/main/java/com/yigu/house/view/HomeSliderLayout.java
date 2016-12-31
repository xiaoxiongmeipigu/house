package com.yigu.house.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.yigu.commom.result.MapiResourceResult;
import com.yigu.commom.util.DPUtil;
import com.yigu.house.R;
import com.yigu.house.adapter.ShopPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2016/8/30.
 */
public class HomeSliderLayout extends RelativeLayout {
    @Bind(R.id.index_viewpager)
    ViewPager indexViewpager;
    @Bind(R.id.guide_dot)
    LinearLayout guideDot;

    private Context mContext;
    private View view;
    List<View> sliderViewList;

    public HomeSliderLayout(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public HomeSliderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public HomeSliderLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        if (isInEditMode())
            return;
        view = LayoutInflater.from(mContext).inflate(R.layout.layout_home_slider, this);
        ButterKnife.bind(this, view);
    }

    public void load(List<MapiResourceResult> list) {
        if(null!=list){//&&list.size()>0
            sliderViewList = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                SimpleDraweeView view = (SimpleDraweeView) LayoutInflater.from(mContext).inflate(R.layout.layout_draweeview,null);

                if(i==3){
                    view.setImageResource(R.mipmap.banner_two);
                }else{
                //创建将要下载的图片的URI
                Uri imageUri = Uri.parse("");
                ImageRequest request = ImageRequestBuilder.newBuilderWithSource(imageUri)
                        .setResizeOptions(new ResizeOptions(DPUtil.dip2px(375), DPUtil.dip2px(208)))
                        .build();
                DraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setImageRequest(request)
                        .setOldController(view.getController())
                        .setControllerListener(new BaseControllerListener<ImageInfo>())
                        .build();
                view.setController(controller);



                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });}
                sliderViewList.add(view);



            }
            ShopPagerAdapter sliderAdapter = new ShopPagerAdapter(sliderViewList);
            indexViewpager.setAdapter(sliderAdapter);
            guideDot.removeAllViews();
            for (int i = 0; i < sliderViewList.size(); i++) {
                ImageView imageView = new ImageView(mContext);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DPUtil.dip2px(8), DPUtil.dip2px(8));
                params.setMargins(DPUtil.dip2px(6), 0, DPUtil.dip2px(6), DPUtil.dip2px(6));
                imageView.setLayoutParams(params);
                imageView.setBackgroundResource(R.drawable.selector_item_dot);
                guideDot.addView(imageView);
            }
            guideDot.getChildAt(0).setSelected(true);
            indexViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    for (int i = 0; i < sliderViewList.size(); i++) {
                        if (position == i)
                            guideDot.getChildAt(i).setSelected(true);
                        else
                            guideDot.getChildAt(i).setSelected(false);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }

            });
        }

    }

}
