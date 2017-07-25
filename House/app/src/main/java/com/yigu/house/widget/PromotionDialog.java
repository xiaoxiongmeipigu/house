package com.yigu.house.widget;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.yigu.commom.result.MapiResourceResult;
import com.yigu.commom.util.FXBitmapUtils;
import com.yigu.house.R;
import com.yigu.house.util.PictureShareDialog;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/6/28.
 */
public class PromotionDialog extends Dialog {
    @Bind(R.id.picture_share_layout)
    RelativeLayout mPictureShareLayout;
    @Bind(R.id.cancel)
    TextView mCancel;

    private Context mContext;
    private String type;
    List<MapiResourceResult> params;
    private String content;

    public PromotionDialog(Context context) {
        super(context, R.style.dialog_theme);
        mContext = context;
        initView();
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setParams(List<MapiResourceResult> params,String content) {
        this.params = params;
        this.content = content;
    }

    private void initView() {
        setContentView(R.layout.dialog_picture_word);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.picture_share_layout,R.id.cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.picture_share_layout:
                downloadPicture();
                break;
            case R.id.cancel:
                dismiss();
                break;
        }
    }

    private void downloadPicture() {

        copyContent(content);//new StringBuilder().append(result.getContent()).append(result.getUrl()).toString()
        savePicture(params);
        showPictureDialog();

    }

    private void copyContent(String content) {
        ClipboardManager cmb = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);//获取剪贴板服务
        ClipData clip = ClipData.newPlainText("content", content);
        cmb.setPrimaryClip(clip);
    }

    private void savePicture(List<MapiResourceResult> images) {
        RequestQueue mQueue = Volley.newRequestQueue(mContext);
        for (MapiResourceResult image : images) {
            ImageRequest imageRequest = new ImageRequest(
                    image.getUrl(),
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap response) {
                            FXBitmapUtils.saveImageToGallery(mContext, response);
                        }
                    }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            mQueue.add(imageRequest);
        }
    }

    private void showPictureDialog() {
        PictureShareDialog shareDialog = new PictureShareDialog(mContext);
        shareDialog.show();
        dismiss();
    }
}
