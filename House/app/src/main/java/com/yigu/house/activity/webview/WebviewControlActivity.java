package com.yigu.house.activity.webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yigu.commom.api.BasicApi;
import com.yigu.commom.application.ExitApplication;
import com.yigu.commom.util.DebugLog;
import com.yigu.commom.util.StringUtil;
import com.yigu.house.R;
import com.yigu.house.base.BaseActivity;
import com.yigu.house.util.webview.WebChromeClientImpl;
import com.yigu.house.util.webview.WebViewUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/2/18.
 */
public class WebviewControlActivity extends BaseActivity {

    @Bind(R.id.webview)
    WebView webview;
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.center)
    TextView center;
    @Bind(R.id.lay_header)
    RelativeLayout layHeader;
    @Bind(R.id.iv_right_two)
    ImageView ivRightTwo;

    private String title;
//    private WebBroadCast webBroadCast;

    String linkUrl = "";

    AudioManager mAudioManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_webview);
        ExitApplication.getInstance().addActivity(this);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        initView();
    }


    @SuppressLint({"JavascriptInterface", "AddJavascriptInterface"})
    private void initView() {
        boolean isShare = getIntent().getBooleanExtra("isShare", false);
        this.title = getIntent().getStringExtra("title");
        back.setImageResource(R.mipmap.back);
        if(TextUtils.isEmpty(title))
            layHeader.setVisibility(View.GONE);
        else
             center.setText(title);

        linkUrl = getIntent().getStringExtra("url");
        DebugLog.i("linkUrl=" + linkUrl);




        WebSettings webSetting = webview.getSettings();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
            webSetting.setMixedContentMode(WebSettings.LOAD_NO_CACHE);
        webSetting.setAllowFileAccess(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setDatabaseEnabled(true);
        webSetting.setAppCacheEnabled(true);
        webSetting.setBuiltInZoomControls(false);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webview.addJavascriptInterface(new WebViewUtil(this, webview), "app");
        loadData();


    }

    private void loadData() {

        webview.loadUrl(linkUrl, WebViewUtil.getWebviewHeader());//加载网页
        webview.setWebViewClient(new WebViewClient() {

            /*@Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                WebViewUtil.shouldOverrideUrlLoading(WebviewControlActivity.this, view, url);
            }*/

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                DebugLog.i("shouldOverrideUrlLoading=");
//                return WebViewUtil.shouldOverrideUrlLoading(WebviewControlActivity.this, view, url);
                // 在APP内部打开链接，不要调用系统浏览器
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                DebugLog.i("title=" + view.getTitle());
//                center.setText(view.getTitle());
            }
        });
        webview.setWebChromeClient(new WebChromeClientImpl() {
            @Override
            public void onReceivedTitle(WebView view, String newTitle) {
                super.onReceivedTitle(view, title);
                DebugLog.i("onReceivedTilt=" + newTitle);
                if (StringUtil.isEmpty(title)) {
//                    center.setText(newTitle);
                }
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webview.canGoBack()) {
            webview.goBack();
            return true;
        } else {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick(R.id.back)
    void back() {
        if (webview.canGoBack())
            webview.goBack();
        else {
            finish();
        }
    }


    boolean isPause = false;

    @Override
    protected void onRestart() {
        super.onRestart();
        webview.reload();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isPause = false;
    }
    @Override
    protected void onPause() {
        super.onPause();
        isPause = true;
        requestAudioFocus();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        webview.destroy();
        mAudioManager.abandonAudioFocus(audioFocusChangeListener);
    }

    private void requestAudioFocus() {
        int result = mAudioManager.requestAudioFocus(audioFocusChangeListener,
                AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            DebugLog.i("audio focus been granted");
        }
    }
    private AudioManager.OnAudioFocusChangeListener audioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            DebugLog.i("focusChange: " + focusChange);
            if (isPause && focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                requestAudioFocus();
            }
        }
    };

}
