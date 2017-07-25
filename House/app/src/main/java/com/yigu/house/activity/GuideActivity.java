package com.yigu.house.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yigu.commom.util.DPUtil;
import com.yigu.house.R;
import com.yigu.house.base.BaseActivity;
import com.yigu.house.util.ControllerUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuideActivity extends BaseActivity {

    @Bind(R.id.viewPager)
    ViewPager mViewPager;
    @Bind(R.id.guide_dot)
    LinearLayout mGuideDot;
    @Bind(R.id.button_layout)
    RelativeLayout mButtonLayout;

    private List<View> imgLists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initView() {
        int[] imgs = {R.drawable.guide_one, R.drawable.guide_two, R.drawable.guide_three, R.drawable.guide_four};
        for (int img : imgs) {
            View slideLayout = LayoutInflater.from(this).inflate(R.layout.layout_guide_item, null);
            SimpleDraweeView slideImage = (SimpleDraweeView) slideLayout.findViewById(R.id.guide_item_img);
            slideImage.setImageURI(Uri.parse("res:///" + img));
            imgLists.add(slideLayout);
        }
//        mViewPager.setPageTransformer(true, new DepthPagerTransformer());//动画
        GudiePagerAdapter gudiePagerAdapter = new GudiePagerAdapter();
        mViewPager.setAdapter(gudiePagerAdapter);
        drawDot();
    }

    /**
     * 轮播点
     */
    private void drawDot() {
        for (int i = 0; i < imgLists.size(); i++) {
            ImageView imageView = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(18, 18);
            params.setMargins(DPUtil.dip2px(4), 0, DPUtil.dip2px(4), 50);
            imageView.setLayoutParams(params);
            imageView.setBackgroundResource(R.drawable.selector_item_dot);
            mGuideDot.addView(imageView);
        }
        mGuideDot.getChildAt(0).setSelected(true);
    }

    private void initListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 3) {
                    mButtonLayout.setVisibility(View.VISIBLE);
                } else {
                    mButtonLayout.setVisibility(View.GONE);
                }
                for (int i = 0; i < imgLists.size(); i++) {
                    if (position == i)
                        mGuideDot.getChildAt(i).setSelected(true);
                    else
                        mGuideDot.getChildAt(i).setSelected(false);
                }

            }


            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick(R.id.button_layout)
    public void onClick() {

        String versionCode = getversionCode();
        if (versionCode != null) {
            userSP.saveUserGuide(versionCode + "");
        }
        ControllerUtil.go2Main();
        finish();
    }

    class GudiePagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return imgLists.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imgLists.get(position));
            return imgLists.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imgLists.get(position));
        }
    }

}
