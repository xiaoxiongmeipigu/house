package com.yigu.house.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.yigu.commom.api.CommonApi;
import com.yigu.commom.util.DPUtil;
import com.yigu.commom.util.RequestCallback;
import com.yigu.commom.util.RequestExceptionCallback;
import com.yigu.house.R;
import com.yigu.house.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by brain on 2017/4/21.
 */
public class IndentAboutDialog extends Dialog {

    @Bind(R.id.cancel)
    ImageView cancel;
    @Bind(R.id.content)
    TextView content;
    private BaseActivity mainActivity;

    public IndentAboutDialog(Context context, int theme) {
        super(context, theme);
        mainActivity = (BaseActivity) context;
        initView();
    }

    private void initView() {
        setContentView(R.layout.dialog_indent_about);
        ButterKnife.bind(this);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = DPUtil.getScreenMetrics(mainActivity).x; //设置宽度
        lp.height = (int) (DPUtil.getScreenMetrics(mainActivity).y / 1.5);
        getWindow().setAttributes(lp);
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setWindowAnimations(R.style.popup_slide_in);
        this.setCanceledOnTouchOutside(true);
        loadData();
    }

    private void loadData() {
        CommonApi.sampleinfo(mainActivity, new RequestCallback<String>() {
            @Override
            public void success(String success) {
                content.setText(success);
            }
        }, new RequestExceptionCallback() {
            @Override
            public void error(Integer code, String message) {

            }
        });

    }

    @OnClick(R.id.cancel)
    void cancelVip() {
        dismiss();
    }

}
