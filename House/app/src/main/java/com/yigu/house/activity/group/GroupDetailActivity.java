package com.yigu.house.activity.group;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lzy.widget.VerticalSlide;
import com.yigu.commom.api.BasicApi;
import com.yigu.commom.api.ItemApi;
import com.yigu.commom.result.MapiCarResult;
import com.yigu.commom.result.MapiCartItemResult;
import com.yigu.commom.util.DebugLog;
import com.yigu.commom.util.RequestCallback;
import com.yigu.commom.util.RequestExceptionCallback;
import com.yigu.commom.widget.MainToast;
import com.yigu.house.R;
import com.yigu.house.base.BaseActivity;
import com.yigu.house.fragment.group.GroupDetailFragment;
import com.yigu.house.fragment.group.GroupInfoFragment;
import com.yigu.house.fragment.prompt.PromptDetailFragment;
import com.yigu.house.fragment.prompt.PromptInfoFragment;
import com.yigu.house.util.AnimationUtil;
import com.yigu.house.util.ControllerUtil;
import com.yigu.house.view.SizeColorLayout;

import java.util.ArrayList;
import java.util.List;

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

    String goods_id = "";

    int terms = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_detail);
        ButterKnife.bind(this);
        if(null!=goods_id)
            goods_id = getIntent().getStringExtra("goods_id");
        if(!TextUtils.isEmpty(goods_id)){
            initView();
            initListener();
            load();
        }

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

    private void load(){
        showLoading() ;
        ItemApi.detail(this, goods_id, new RequestCallback<JSONObject>() {
            @Override
            public void success(JSONObject success) {
                hideLoading();
                String termsStr =  success.getJSONObject("data").getString("terms");
                terms = Integer.parseInt(termsStr);
                sizeColorLayout.setTerms(terms);
                topFragment.load(success);
                String goods_id = success.getJSONObject("data").getString("goods_id");
                bottomFragment.setWebUrl(BasicApi.BASIC_URL+BasicApi.detaildesc+goods_id);
                bottomFragment.load();
                sizeColorLayout.load(success);
            }
        }, new RequestExceptionCallback() {
            @Override
            public void error(Integer code, String message) {
                hideLoading();
                MainToast.showShortToast(message);
            }
        });
    }

    private void initListener(){
        sizeColorLayout.setOnHideListener(new SizeColorLayout.OnHideListener() {
            @Override
            public void hide() {
                hideAlbum();
                fab.setVisibility(View.VISIBLE);
            }
        });
        sizeColorLayout.setAttrListener(new SizeColorLayout.AttrInterface() {
            @Override
            public void getAttrs(String attrs) {
                if(null!=topFragment)
                    topFragment.setSelSize(attrs);
            }
        });
    }

    @OnClick({R.id.back, R.id.iv_right_two,R.id.fab,R.id.purcase,R.id.order})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.iv_right_two:
                ControllerUtil.go2Purcase();
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
            case R.id.purcase:
                String attrIds = sizeColorLayout.getAttrIds();
                DebugLog.i("attrIds:"+attrIds);
                if(TextUtils.isEmpty(attrIds)){
                    MainToast.showShortToast("请选择商品");
                    return;
                }

                if(terms>sizeColorLayout.getCounts()){
                    MainToast.showShortToast("您购买的商品数量未达到起批数量");
                    return;
                }

                DebugLog.i("attrIds:"+attrIds);
                showLoading();
                ItemApi.addCart(this, goods_id, attrIds, new RequestCallback() {
                    @Override
                    public void success(Object success) {
                        hideLoading();
                        MainToast.showShortToast("成功加入进货单");
                    }
                }, new RequestExceptionCallback() {
                    @Override
                    public void error(Integer code, String message) {
                        hideLoading();
                        MainToast.showShortToast(message);
                    }
                });
                break;
            case R.id.order:
                String attrIds2 = sizeColorLayout.getAttrIds();
                if(TextUtils.isEmpty(attrIds2)){
                    MainToast.showShortToast("请选择商品");
                    return;
                }

                if(terms>sizeColorLayout.getCounts()){
                    MainToast.showShortToast("您购买的商品数量未达到起批数量");
                    return;
                }

                DebugLog.i("attrIds:"+attrIds2);
                showLoading();
                ItemApi.buynow(this, goods_id, attrIds2,"", new RequestCallback<List<MapiCarResult>>() {
                    @Override
                    public void success(List<MapiCarResult> success) {
                        hideLoading();

                        ArrayList<MapiCartItemResult> cartItemResults = new ArrayList<>();
                        ArrayList<MapiCarResult> carResults = new ArrayList<>();
                        if(!success.isEmpty()){
                            carResults.addAll(success);
                            for(MapiCarResult carResult : success){
                                for(MapiCartItemResult cartItemResult : carResult.getList()){
                                    cartItemResults.add(cartItemResult);
                                }
                            }

                            ControllerUtil.go2Balance(cartItemResults,carResults);

                        }

                    }
                }, new RequestExceptionCallback() {
                    @Override
                    public void error(Integer code, String message) {
                        hideLoading();
                        MainToast.showShortToast(message);
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
