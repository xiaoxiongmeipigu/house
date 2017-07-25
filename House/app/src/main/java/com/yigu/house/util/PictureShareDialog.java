package com.yigu.house.util;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.yigu.house.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/6/28.
 */
public class PictureShareDialog extends Dialog {
    @Bind(R.id.cancel)
    TextView mCancel;
    @Bind(R.id.wechat_friend)
    TextView mWechatFriend;

    private Context mContext;

    public PictureShareDialog(Context context) {
        super(context, R.style.dialog_theme);
        mContext = context;
        initView();
    }

    private void initView() {
        setContentView(R.layout.dialog_picture_share);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.cancel,R.id.root_view})//, R.id.wechat_friend
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                dismiss();
                break;
            case R.id.wechat_friend:
                // TODO: 2016/6/28  朋友圈 ,AppId不正确，需要去微信公众平台申请
                WeixinShareManager wsm = WeixinShareManager.getInstance(mContext);
                wsm.shareByWeixin(wsm.new ShareContentText("ds"), WeixinShareManager.WEIXIN_SHARE_TYPE_FRENDS);
//                WeixinShareManager.getInstance(mContext).shareByWeixin(new ("ds"), WeixinShareManager.WEIXIN_SHARE_TYPE_FRENDS);
                break;
            case R.id.root_view:
                dismiss();
                break;
        }
    }
}
