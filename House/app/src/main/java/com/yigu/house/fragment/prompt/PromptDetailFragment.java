package com.yigu.house.fragment.prompt;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.ProgressBar;

import com.lzy.widget.vertical.VerticalWebView;
import com.yigu.commom.util.DebugLog;
import com.yigu.house.R;
import com.yigu.house.base.BaseFrag;
import com.yigu.house.util.webview.WebViewUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PromptDetailFragment extends BaseFrag {


    @Bind(R.id.webView)
    VerticalWebView webView;
    @Bind(R.id.progressbar)
    ProgressBar progressbar;

    public PromptDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_prompt_detail, container, false);
        ButterKnife.bind(this, view);
        initView();
        initListener();
        load();
        return view;
    }

    public void initView() {

        WebSettings webSetting = webView.getSettings();
        webSetting.setAllowFileAccess(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setDatabaseEnabled(true);
        webSetting.setAppCacheEnabled(true);
        webSetting.setBuiltInZoomControls(false);
        webView.addJavascriptInterface(new WebViewUtil(getActivity(), webView), "app");


    }

    private void initListener() {

    }

    @Override
    public void load() {
        progressbar.setVisibility(View.GONE);
        webView.loadUrl("http://www.rrdshop.com.cn/", WebViewUtil.getWebviewHeader());//加载网页webview.loadUrl(linkUrl, WebViewUtil.getWebviewHeader());//加载网页
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
