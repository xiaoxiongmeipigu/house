package com.yigu.house.adapter.order;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.yigu.commom.result.IndexData;
import com.yigu.commom.result.MapiCarResult;
import com.yigu.commom.result.MapiCartItemResult;
import com.yigu.commom.result.MapiOrderDetailResult;
import com.yigu.commom.result.MapiOrderResult;
import com.yigu.commom.util.DPUtil;
import com.yigu.house.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2017/6/22.
 */
public class OrderDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater inflater;
    private Context mContext;
    List<IndexData> mList = new ArrayList<>();

    public OrderDetailAdapter(Context context, List<IndexData> list) {
        inflater = LayoutInflater.from(context);
        this.mList = list;
        mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        String type = mList.get(position).getType();
        if (type.equals("divider")) {
            return 1;
        } else if (type.equals("head")) {
            return 2;
        } else if (type.equals("banlance_item")) {
            return 3;
        } else if (type.equals("banlance_head")) {
            return 4;
        } else if (type.equals("banlance_item_three")) {
            return 5;
        } else if (type.equals("order_detail_divider")) {
            return 6;
        }
        return 6;
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1:
                return new DividerViewHolder(inflater.inflate(R.layout.item_order_divider, parent, false));
            case 2:
                return new ItemHeadHolder(inflater.inflate(R.layout.item_order_detail_head, parent, false));
            case 3:
                return new BanlanceItemViewHolder(inflater.inflate(R.layout.item_balance_item, parent, false));
            case 4:
                return new BanlanceHeadViewHolder(inflater.inflate(R.layout.item_balance_head, parent, false));
            case 5:
                return new ItemThreeViewHolder(inflater.inflate(R.layout.item_balance_item_three, parent, false));
            case 6:
                return new OrderDetailDividerHolder(inflater.inflate(R.layout.layout_order_detail_divider, parent, false));
            default:
                return new OrderDetailDividerHolder(inflater.inflate(R.layout.layout_order_detail_divider, parent, false));
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BanlanceHeadViewHolder) {
            setBanlanceHead((BanlanceHeadViewHolder) holder, position);
        } else if (holder instanceof BanlanceItemViewHolder) {
            setBanlanceItem((BanlanceItemViewHolder) holder, position);
        } else if (holder instanceof ItemThreeViewHolder) {
            setItemThree((ItemThreeViewHolder) holder, position);
        } else if (holder instanceof ItemHeadHolder) {
            setHead((ItemHeadHolder) holder, position);
        }
    }

    class ItemHeadHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.status)
        TextView status;
        @Bind(R.id.express)
        TextView express;
        @Bind(R.id.express_no)
        TextView expressNo;
        @Bind(R.id.name_tv)
        TextView nameTv;
        @Bind(R.id.mobile)
        TextView mobile;
        @Bind(R.id.address)
        TextView address;
        @Bind(R.id.order_id)
        TextView orderId;
        @Bind(R.id.addtime)
        TextView addtime;
        @Bind(R.id.pay_type)
        TextView payType;
        @Bind(R.id.goods_price)
        TextView goodsPrice;
        @Bind(R.id.num)
        TextView num;
        @Bind(R.id.express_price)
        TextView expressPrice;
        @Bind(R.id.total_price)
        TextView totalPrice;
        @Bind(R.id.pay_divider)
        View payDivider;
        public ItemHeadHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class DividerViewHolder extends RecyclerView.ViewHolder {
        public DividerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class OrderDetailDividerHolder extends RecyclerView.ViewHolder {
        public OrderDetailDividerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class BanlanceHeadViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.image)
        SimpleDraweeView image;
        @Bind(R.id.title)
        TextView title;

        public BanlanceHeadViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class BanlanceItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.num)
        TextView num;
        @Bind(R.id.attr)
        TextView attr;
        @Bind(R.id.price)
        TextView price;

        public BanlanceItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ItemThreeViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.num)
        TextView num;
        @Bind(R.id.price)
        TextView price;

        public ItemThreeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void setHead(ItemHeadHolder holder, int position) {
        MapiOrderDetailResult mapiOrderResult = (MapiOrderDetailResult) mList.get(position).getData();
        if (null != mapiOrderResult) {
            holder.status.setText(TextUtils.isEmpty(mapiOrderResult.getStatus())?"":mapiOrderResult.getStatus());
            holder.express.setText("物流："+(TextUtils.isEmpty(mapiOrderResult.getExpress())?"":mapiOrderResult.getExpress()));
            if(TextUtils.isEmpty(mapiOrderResult.getExpress_no())){
                holder.expressNo.setVisibility(View.GONE);
            }else{
                holder.expressNo.setVisibility(View.VISIBLE);
                holder.expressNo.setText("物流单号："+(TextUtils.isEmpty(mapiOrderResult.getExpress_no())?"":mapiOrderResult.getExpress_no()));
            }

            holder.nameTv.setText("收货人："+(TextUtils.isEmpty(mapiOrderResult.getName())?"":mapiOrderResult.getName()));
            holder.mobile.setText(TextUtils.isEmpty(mapiOrderResult.getAddress())?"":mapiOrderResult.getAddress());
            holder.address.setText(TextUtils.isEmpty(mapiOrderResult.getAddress())?"":mapiOrderResult.getAddress());
            holder.orderId.setText("订单编号："+(TextUtils.isEmpty(mapiOrderResult.getOrder_id())?"":mapiOrderResult.getOrder_id()));
            holder.addtime.setText("下单时间："+(TextUtils.isEmpty(mapiOrderResult.getAddtime())?"":mapiOrderResult.getAddtime()));
            if(TextUtils.isEmpty(mapiOrderResult.getPay_type())){
                holder.payType.setVisibility(View.GONE);
                holder.payDivider.setVisibility(View.GONE);
            }else{
                holder.payType.setVisibility(View.VISIBLE);
                holder.payDivider.setVisibility(View.VISIBLE);
                holder.payType.setText("支付方式："+mapiOrderResult.getPay_type());
            }

            holder.goodsPrice.setText("¥"+(TextUtils.isEmpty(mapiOrderResult.getGoods_price())?"0":mapiOrderResult.getGoods_price()));
            holder.num.setText((TextUtils.isEmpty(mapiOrderResult.getNum())?"0":mapiOrderResult.getNum())+"件");
            holder.expressPrice.setText("¥"+(TextUtils.isEmpty(mapiOrderResult.getExpress_price())?"0":mapiOrderResult.getExpress_price()));
            holder.totalPrice.setText("¥ "+(TextUtils.isEmpty(mapiOrderResult.getTotal_price())?"":mapiOrderResult.getTotal_price()));



        }
    }

    private void setBanlanceHead(BanlanceHeadViewHolder holder, int position) {

        MapiCarResult ware = (MapiCarResult) mList.get(position).getData();

        //创建将要下载的图片的URI
        Uri imageUri = Uri.parse(TextUtils.isEmpty(ware.getPic_url())?"":ware.getPic_url());
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(imageUri)
                .setResizeOptions(new ResizeOptions(DPUtil.dip2px(78), DPUtil.dip2px(78)))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(holder.image.getController())
                .setControllerListener(new BaseControllerListener<ImageInfo>())
                .build();
        holder.image.setController(controller);

        holder.title.setText(TextUtils.isEmpty(ware.getName()) ? "" : ware.getName());

    }

    private void setBanlanceItem(BanlanceItemViewHolder holder, int position) {

        MapiCartItemResult result = (MapiCartItemResult) mList.get(position).getData();

        String count = TextUtils.isEmpty(result.getNum()) ? "1" : result.getNum();
        holder.num.setText("X " + count);

        String color = TextUtils.isEmpty(result.getColor()) ? "" : result.getColor();
        String size = TextUtils.isEmpty(result.getSize()) ? "" : result.getSize();

        holder.attr.setText("颜色：" + color + ";  尺码：" + size);

        holder.price.setText(TextUtils.isEmpty(result.getTotal_price()) ? "0" : result.getTotal_price());


    }

    private void setItemThree(ItemThreeViewHolder holder, int position) {

        MapiCartItemResult result = (MapiCartItemResult) mList.get(position).getData();
        String count = TextUtils.isEmpty(result.getNum()) ? "1" : result.getNum();
        holder.num.setText("X " + count);

        holder.price.setText(TextUtils.isEmpty(result.getTotal_price()) ? "0" : result.getTotal_price());


    }


}
