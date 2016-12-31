package com.yigu.house.fragment.group;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.lzy.widget.vertical.VerticalWebView;
import com.yigu.house.R;
import com.yigu.house.base.BaseFrag;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupDetailFragment extends BaseFrag {


    @Bind(R.id.webView)
    VerticalWebView webView;
    @Bind(R.id.progressbar)
    ProgressBar progressbar;

    public GroupDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_group_detail, container, false);
        ButterKnife.bind(this, view);
        initView();
        initListener();
        load();
        return view;
    }

    public void initView() {
        if (null != webView) {
            progressbar.setVisibility(View.GONE);
            webView.loadUrl("http://www.rrdshop.com.cn/");//https://github.com/jeasonlzy0216
        }
    }

    private void initListener() {

    }

    @Override
    public void load() {

    }

    @Override
    public void goTop() {
        if(null!=webView)
            webView.goTop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
