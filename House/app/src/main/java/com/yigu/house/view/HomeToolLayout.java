package com.yigu.house.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.meiqia.meiqiasdk.util.MQIntentBuilder;
import com.yigu.commom.application.AppContext;
import com.yigu.commom.result.MapiResourceResult;
import com.yigu.commom.util.DebugLog;
import com.yigu.commom.widget.MainToast;
import com.yigu.house.R;
import com.yigu.house.activity.webview.WebviewControlActivity;
import com.yigu.house.adapter.MainAdapter;
import com.yigu.house.adapter.MainToolAdapter;
import com.yigu.house.base.BaseActivity;
import com.yigu.house.interfaces.RecyOnItemClickListener;
import com.yigu.house.util.ControllerUtil;
import com.yigu.house.util.CountDownTimerUtil;
import com.yigu.house.util.JGJDataSource;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2016/8/31.
 */
public class HomeToolLayout extends RelativeLayout {
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.headline_layout)
    TextView headlineLayout;//头条

    private Context mContext;
    private View view;
    private Integer i = 0;
    BaseActivity activity;

    List<MapiResourceResult> mList;
    List<MapiResourceResult> headList;
    MainToolAdapter mAdapter;

    private boolean isStop = false;

    CountDownTimerUtil countDownTimerUtil;

    public HomeToolLayout(Context context) {
        super(context);
        mContext = context;
        activity = (BaseActivity) mContext;
        initView();
    }

    public HomeToolLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        activity = (BaseActivity) mContext;
        initView();
    }

    public HomeToolLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        activity = (BaseActivity) mContext;
        initView();
    }

    private void initView() {
        if (isInEditMode())
            return;
        view = LayoutInflater.from(mContext).inflate(R.layout.layout_home_tool, this);
        ButterKnife.bind(this, view);
        mList = new ArrayList<>();
        headList = new ArrayList<>();
        initData();
        initListener();

    }

    public void load(List<MapiResourceResult> data) {
        if(null!=countDownTimerUtil) {
            i=0;
            countDownTimerUtil.cancel();
        }
        headList.addAll(data);//JGJDataSource.getHeadResource()
        updateHeadLine(headList);
    }

    public void initData() {
        mList.clear();
        mList.addAll(JGJDataSource.getMainResource());
        GridLayoutManager manager = new GridLayoutManager(mContext, 4);
        recyclerView.setLayoutManager(manager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recyclerView.setHasFixedSize(true);
        mAdapter = new MainToolAdapter(mContext, mList);
        recyclerView.setAdapter(mAdapter);

    }

    private void initListener(){
        mAdapter.setOnItemClickListener(new RecyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position){
                    case 0://现货
                        ControllerUtil.go2PromptList("");
                        break;
                    case 1:
                       ControllerUtil.go2IndentList();
                        break;
                    case 2://拼单
                        ControllerUtil.go2GroupList();

                        break;
                    case 3:
                        ControllerUtil.go2Notice();
                        break;
                    case 4://vip
                        if(activity.checkVip())
                            ControllerUtil.go2VipList("");
                        else
                            MainToast.showShortToast("请升级为VIP");
                        break;
                    case 5://来样定制
                        ControllerUtil.go2Custom();
                        break;

                    case 6://售后
                       /* String url="mqqwpa://im/chat?chat_type=wpa&uin=3313104371";
                        try {
                            mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                        } catch (Exception e) {
                            MainToast.showShortToast("未安装QQ或安装的版本不支持");
                            // 未安装手Q或安装的版本不支持    showToast("未安装手Q或安装的版本不支持");
                        }*/

                        Intent intent = new MQIntentBuilder(mContext).build();
                        mContext.startActivity(intent);

                        break;
                    case 7:
                        MainToast.showShortToast("敬请期待");
                        break;
                }
            }
        });
    }

    /**
     * 更新袋鼠头条数据
     *
     * @param list
     */
    private void updateHeadLine(final List<MapiResourceResult> list) {
        try {
            if (null==list||list.isEmpty())
                return;
            if (i == list.size()) {
                i = 0;
            }
            headlineLayout.setText(list.get(i).getName());
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.item_order_animate_in);
            headlineLayout.startAnimation(animation);
            headlineLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
//                if (i<list.size()&&list.get(i).getLinkUrl().length() > 0)
//                    ControllerUtil.go2WebView(list.get(i).getLinkUrl(), null, true);
                }
            });

            if(null!=countDownTimerUtil)
                countDownTimerUtil.start();
            else{
                countDownTimerUtil = new CountDownTimerUtil(5 * 1000, 1000){

                    @Override
                    public void onTick(long millisUntilFinished) {
                        if(null==mContext||null==activity||activity.isFinishing()||getVisibility()==GONE) {
                            DebugLog.i("CountDownTimerUtil==cancel");
                            cancel();
                        }
                    }

                    @Override
                    public void onFinish() {
                        i++;
                        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.item_order_animate_out);
                        headlineLayout.startAnimation(animation);
                        headlineLayout.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateHeadLine(list);
                            }
                        }, 500);
                    }
                }.start();
            }



        /*new CountDownTimer() {

            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {


            }
        }.start();*/
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
    }
}
