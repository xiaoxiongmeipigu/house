package com.yigu.house.activity.group;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.widget.VerticalSlide;
import com.yigu.commom.util.DebugLog;
import com.yigu.house.R;
import com.yigu.house.base.BaseActivity;
import com.yigu.house.fragment.group.GroupDetailFragment;
import com.yigu.house.fragment.group.GroupInfoFragment;
import com.yigu.house.fragment.prompt.PromptDetailFragment;
import com.yigu.house.fragment.prompt.PromptInfoFragment;
import com.yigu.house.util.AnimationUtil;
import com.yigu.house.view.SizeColorLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GroupDetailActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.center)
    TextView center;
    @Bind(R.id.iv_right_two)
    ImageView ivRightTwo;
    @Bind(R.id.first)
    FrameLayout first;
    @Bind(R.id.second)
    FrameLayout second;
    @Bind(R.id.verticalSlide)
    VerticalSlide verticalSlide;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.sizeColorLayout)
    SizeColorLayout sizeColorLayout;
    @Bind(R.id.bg_view)
    View bg_view;

    private GroupInfoFragment topFragment;
    private GroupDetailFragment bottomFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_detail);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initView() {

        back.setImageResource(R.mipmap.back);
        center.setText("商品详情");
        ivRightTwo.setVisibility(View.VISIBLE);
        ivRightTwo.setImageResource(R.mipmap.purcase_icon);


        sizeColorLayout.setActivity(this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        topFragment = new GroupInfoFragment();
        bottomFragment = new GroupDetailFragment();

        topFragment.setSizeOpenListener(new GroupInfoFragment.SizeOpenListener() {
            @Override
            public void open() {
                DebugLog.i("PromptDetailActivity=>selSizeLL");
                album();
            }
        });

        transaction.replace(R.id.first, topFragment);

        transaction.replace(R.id.second, bottomFragment);

        verticalSlide.setOnShowNextPageListener(new VerticalSlide.OnShowNextPageListener() {
            @Override
            public void onShowNextPage() {
                ((GroupDetailFragment) bottomFragment).initView();
            }
        });

        transaction.commit();




    }

    private void initListener(){
        sizeColorLayout.setOnHideListener(new SizeColorLayout.OnHideListener() {
            @Override
            public void hide() {
                hideAlbum();
                fab.setVisibility(View.VISIBLE);
            }
        });
    }

    @OnClick({R.id.back, R.id.iv_right_two,R.id.fab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.iv_right_two:
                break;
            case R.id.fab:
                /**
                 * 返回顶部分三步
                 * 1.第二页滚动到第二页的顶部
                 * 2.VerticalSlide从第二页返回第一页
                 * 3.第一页滚动到第一页的顶部
                 * OnGoTopListener 表示第一页滚动到顶部 的方法,这个由于采用什么布局,库内部并不知道,所以一般是自己实现
                 * 也可以不实现,直接传null
                 */
                bottomFragment.goTop();
                verticalSlide.goTop(new VerticalSlide.OnGoTopListener() {
                    @Override
                    public void goTop() {
                        topFragment.goTop();
                    }
                });
                break;
        }
    }

    private void album() {
        if (sizeColorLayout.getVisibility() == View.GONE) {
            popAlbum();
            fab.setVisibility(View.GONE);
        } else {
            hideAlbum();
            fab.setVisibility(View.VISIBLE);
        }
    }

    /** 弹出 */
    private void popAlbum() {
        sizeColorLayout.setVisibility(View.VISIBLE);
        new AnimationUtil(this, R.anim.translate_up_current)
                .setLinearInterpolator().startAnimation(sizeColorLayout);
        bg_view.setVisibility(View.VISIBLE);
    }

    /** 隐藏 */
    private void hideAlbum() {
        new AnimationUtil(this, R.anim.translate_down)
                .setLinearInterpolator().startAnimation(sizeColorLayout);
        sizeColorLayout.setVisibility(View.GONE);
        bg_view.setVisibility(View.GONE);
    }

}
