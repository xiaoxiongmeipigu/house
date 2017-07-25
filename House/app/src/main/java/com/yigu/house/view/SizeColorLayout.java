package com.yigu.house.view;

import android.content.Context;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.yigu.commom.api.ItemApi;
import com.yigu.commom.result.MapiCarResult;
import com.yigu.commom.result.MapiCartItemResult;
import com.yigu.commom.result.MapiResourceResult;
import com.yigu.commom.result.MapiSizeResult;
import com.yigu.commom.util.DPUtil;
import com.yigu.commom.util.DebugLog;
import com.yigu.commom.util.RequestCallback;
import com.yigu.commom.util.RequestExceptionCallback;
import com.yigu.commom.widget.MainToast;
import com.yigu.house.R;
import com.yigu.house.adapter.TabFragmentAdapter;
import com.yigu.house.base.BaseActivity;
import com.yigu.house.fragment.prompt.FromptSizeFrag;
import com.yigu.house.util.ControllerUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by brain on 2016/12/17.
 */
public class SizeColorLayout extends RelativeLayout {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.price)
    TextView price;
    @Bind(R.id.image)
    SimpleDraweeView image;
    @Bind(R.id.tablayout)
    TabLayout tablayout;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.allNum)
    TextView allNum;
    @Bind(R.id.allPrice)
    TextView allPrice;

    private Context mContext;
    private View view;


    private List<String> list_title = new ArrayList<>();

    FromptSizeFrag sizeFrag1;
    FromptSizeFrag sizeFrag2;
    FromptSizeFrag sizeFrag3;
    FromptSizeFrag sizeFrag4;
    FromptSizeFrag sizeFrag5;
    FromptSizeFrag sizeFrag6;

    private List<Fragment> list = new ArrayList<>();

    BaseActivity activity;
    TabFragmentAdapter mAdapter;

    private List<MapiSizeResult> selList;

    String priceStr = "0";
    String attrIds = "";
    String goods_id = "";

    int terms = 0;

    public int getTerms() {
        return terms;
    }

    public void setTerms(int terms) {
        this.terms = terms;
    }

    public SizeColorLayout(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public SizeColorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public SizeColorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        if (isInEditMode())
            return;
        view = LayoutInflater.from(mContext).inflate(R.layout.layout_size_color, this);
        ButterKnife.bind(this, view);
        init();
    }

    private void init() {
        selList = new ArrayList<>();
        tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);

    }


    public void load(JSONObject jsonObject) {

        List<MapiSizeResult> sizeResults = JSONArray.parseArray(jsonObject.getJSONObject("data").getJSONArray("attr").toJSONString(), MapiSizeResult.class);
        priceStr = jsonObject.getJSONObject("data").getString("price");
        String titleStr = jsonObject.getJSONObject("data").getString("title");
        title.setText(TextUtils.isEmpty(titleStr) ? "" : titleStr);
        goods_id = jsonObject.getJSONObject("data").getString("goods_id");
        List<MapiResourceResult> result = JSONArray.parseArray(jsonObject.getJSONObject("data").getJSONArray("img").toJSONString(), MapiResourceResult.class);
        if (null != result && result.size() > 0) {
            //创建将要下载的图片的URI
            Uri imageUri = Uri.parse(result.get(0).getUrl());
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(imageUri)
                    .setResizeOptions(new ResizeOptions(DPUtil.dip2px(95), DPUtil.dip2px(95)))
                    .build();
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setImageRequest(request)
                    .setOldController(image.getController())
                    .setControllerListener(new BaseControllerListener<ImageInfo>())
                    .build();
            image.setController(controller);
        }


        if (null != sizeResults) {
            for (MapiSizeResult mapiSizeResult : sizeResults) {

                sizeFrag1 = new FromptSizeFrag();
                sizeFrag1.setBaiscPrice(priceStr);
                sizeFrag1.load(mapiSizeResult.getSize_list());
                sizeFrag1.setNumListener(new FromptSizeFrag.NumInterface() {
                    @Override
                    public void modify() {
                        getFragList();
                        if(null!=attrInterface)
                            attrInterface.getAttrs(getAttrStr());
                    }
                });
                list_title.add(TextUtils.isEmpty(mapiSizeResult.getColour()) ? "" : mapiSizeResult.getColour());
                list.add(sizeFrag1);
                tablayout.addTab(tablayout.newTab().setText(TextUtils.isEmpty(mapiSizeResult.getColour()) ? "" : mapiSizeResult.getColour()));


            }

        }
        mAdapter = new TabFragmentAdapter(activity.getSupportFragmentManager(), list, list_title);
        viewpager.setAdapter(mAdapter);
        tablayout.setupWithViewPager(viewpager);

    }

    public void setActivity(BaseActivity activity) {
        this.activity = activity;

    }

    private OnHideListener onHideListener;

    @OnClick({R.id.purcase, R.id.order,R.id.close_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.purcase:
                getAttrIds();
                if(TextUtils.isEmpty(attrIds)) {
                    MainToast.showShortToast("请选择商品");
                    return;
                }

                if(terms>getCounts()){
                    MainToast.showShortToast("您购买的商品数量未达到起批数量");
                    return;
                }

                DebugLog.i("attrIds:"+attrIds);
                activity.showLoading();
                ItemApi.addCart(activity, goods_id, attrIds, new RequestCallback() {
                    @Override
                    public void success(Object success) {
                        activity.hideLoading();
                        MainToast.showShortToast("成功加入进货单");
                        if (null != onHideListener)
                            onHideListener.hide();
                    }
                }, new RequestExceptionCallback() {
                    @Override
                    public void error(Integer code, String message) {
                        activity.hideLoading();
                        MainToast.showShortToast(message);
                    }
                });
                break;
            case R.id.order:
                getAttrIds();
                if(TextUtils.isEmpty(attrIds)) {
                    MainToast.showShortToast("请选择商品");
                    return;
                }

                if(terms>getCounts()){
                    MainToast.showShortToast("您购买的商品数量未达到起批数量");
                    return;
                }

                DebugLog.i("attrIds:"+attrIds);
                activity.showLoading();
                ItemApi.buynow(activity, goods_id, attrIds, "",new RequestCallback<List<MapiCarResult>>() {
                    @Override
                    public void success(List<MapiCarResult> success) {
                        activity.hideLoading();

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
                        activity.hideLoading();
                        MainToast.showShortToast(message);
                    }
                });
                break;
            case R.id.close_iv:
                if (null != onHideListener)
                    onHideListener.hide();
                break;
        }
    }

    public interface OnHideListener {
        void hide();
    }

    public void setOnHideListener(OnHideListener onHideListener) {
        this.onHideListener = onHideListener;
    }

    private void getFragList() {
        selList.clear();
        for (Fragment frag : list) {
            DebugLog.i("FromptSizeFrag:" + ((FromptSizeFrag) frag).getList().size());
            selList.addAll(((FromptSizeFrag) frag).getList());

        }

        int account = 0;
        double priceDouble = 0;

        String basicStr = TextUtils.isEmpty(priceStr) ? "0" : priceStr;
        double basicPrice = Double.parseDouble(basicStr);

        for (MapiSizeResult mapiSizeResult : selList) {

            int num = Integer.parseInt(mapiSizeResult.getNum());

            String addPrice = TextUtils.isEmpty(mapiSizeResult.getAdd_price()) ? "0" : mapiSizeResult.getAdd_price();
            double addPriceDouble = Double.parseDouble(addPrice);
            double singlePrice = basicPrice + addPriceDouble;

            DebugLog.i("num:" + num);
            DebugLog.i("addPrice:" + addPrice);
            account += num;
            priceDouble += num * singlePrice;

        }

        allNum.setText(account + "");
        allPrice.setText(priceDouble + "");
    }

    public Integer getCounts(){

        String numStr = TextUtils.isEmpty(allNum.getText())?"0":allNum.getText().toString();

        return Integer.parseInt(numStr);
    }

    public String getAttrIds() {
        attrIds = "";

        for(MapiSizeResult mapiSizeResult : selList){
            if(TextUtils.isEmpty(attrIds))
                attrIds = mapiSizeResult.getAttr_id() + ":" + mapiSizeResult.getNum();
            else
                attrIds += "," + mapiSizeResult.getAttr_id() + ":" + mapiSizeResult.getNum();
        }

        return attrIds;
    }

    public String getAttrStr(){
        String attrStr = "";
        for(MapiSizeResult mapiSizeResult : selList){
            if(TextUtils.isEmpty(attrStr))
                attrStr = mapiSizeResult.getColour()+":"+mapiSizeResult.getSize() + ":" + mapiSizeResult.getNum()+"件";
            else
                attrStr += ";" + mapiSizeResult.getColour()+":"+ mapiSizeResult.getSize() + ":" + mapiSizeResult.getNum()+"件";
        }
        return attrStr;
    }

    AttrInterface attrInterface;

    public interface AttrInterface{
        void getAttrs(String attrs);
    }

    public void setAttrListener(AttrInterface attrInterface){
        this.attrInterface = attrInterface;
    }

}
