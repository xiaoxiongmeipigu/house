package com.yigu.house.adapter.order;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import com.yigu.commom.result.MapiOrderResult;
import com.yigu.commom.util.DPUtil;
import com.yigu.house.R;
import com.yigu.house.interfaces.OrderDeelListener;
import com.yigu.house.interfaces.RecyOnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2017/2/7.
 */
public class OrderListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater inflater;
    private Context mContext;
    List<IndexData> mList = new ArrayList<>();

    private OrderDeelListener orderDeelListener;

    public void setOrderDeelListener(OrderDeelListener orderDeelListener) {
        this.orderDeelListener = orderDeelListener;
    }

    private RecyOnItemClickListener itemClickListener;

    public void setRecyOnItemClickListener(RecyOnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public OrderListAdapter(Context context, List<IndexData> list) {
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
        } else if (type.equals("item")) {
            return 3;
        } else if (type.equals("bottom")) {
            return 4;
        } else if (type.equals("send_bottom")) {
            return 5;
        } else if (type.equals("receive_bottom")) {
            return 6;
        } else if (type.equals("banlance_head")) {
            return 7;
        } else if (type.equals("banlance_item")) {
            return 8;
        } else if (type.equals("banlance_item_three")) {
            return 9;
        }
        return 1;
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
                return new ItemHeadHolder(inflater.inflate(R.layout.item_order_head, parent, false));
            case 3:
                return new ItemViewHolder(inflater.inflate(R.layout.item_order_item, parent, false));
            case 4:
                return new ItemBottomHolder(inflater.inflate(R.layout.item_order_bottom, parent, false));
            case 5:
                return new ItemSendBottomHolder(inflater.inflate(R.layout.item_order_send_bottom, parent, false));
            case 6:
                return new ItemReceiveBottomHolder(inflater.inflate(R.layout.item_order_receive_bottom, parent, false));
            case 7:
                return new BanlanceHeadViewHolder(inflater.inflate(R.layout.item_balance_head, parent, false));
            case 8:
                return new BanlanceItemViewHolder(inflater.inflate(R.layout.item_balance_item, parent, false));
            case 9:
                return new ItemThreeViewHolder(inflater.inflate(R.layout.item_balance_item_three, parent, false));
            default:
                return new DividerViewHolder(inflater.inflate(R.layout.item_order_divider, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            setItem((ItemViewHolder) holder, position);
        } else if (holder instanceof ItemHeadHolder) {
            setHead((ItemHeadHolder) holder, position);
        } else if (holder instanceof ItemBottomHolder) {
            setBottom((ItemBottomHolder) holder, position);
        } else if (holder instanceof ItemReceiveBottomHolder) {
            setReceiveBottom((ItemReceiveBottomHolder) holder, position);
        } else if (holder instanceof ItemSendBottomHolder) {
            setSendBottom((ItemSendBottomHolder) holder, position);
        } else if (holder instanceof BanlanceHeadViewHolder) {
            setBanlanceHead((BanlanceHeadViewHolder) holder, position);
        } else if (holder instanceof BanlanceItemViewHolder) {
            setBanlanceItem((BanlanceItemViewHolder) holder, position);
        } else if (holder instanceof ItemThreeViewHolder) {
            setItemThree((ItemThreeViewHolder) holder, position);
        }
    }

    class ItemHeadHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.order_id)
        TextView orderId;
        @Bind(R.id.order_date)
        TextView orderDate;

        public ItemHeadHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ItemThreeViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.num)
        TextView num;
        @Bind(R.id.price)
        TextView price;
        @Bind(R.id.root_view)
        LinearLayout rootView;
        public ItemThreeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ItemBottomHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.delete)
        TextView delete;
        @Bind(R.id.pay)
        TextView pay;
        @Bind(R.id.goods_total)
        TextView goodsTotal;
        @Bind(R.id.total_amount)
        TextView totalAmount;
        @Bind(R.id.express_fee)
        TextView expressFee;

        public ItemBottomHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ItemSendBottomHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.goods_total)
        TextView goodsTotal;
        @Bind(R.id.total_amount)
        TextView totalAmount;
        @Bind(R.id.express_fee)
        TextView expressFee;

        public ItemSendBottomHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ItemReceiveBottomHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.receive)
        TextView receive;

        public ItemReceiveBottomHolder(View itemView) {
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

    private void setItem(ItemViewHolder holder, int position) {
    }

    private void setHead(ItemHeadHolder holder, int position) {
        MapiOrderResult mapiOrderResult = (MapiOrderResult) mList.get(position).getData();
        if (null != mapiOrderResult) {
            holder.orderId.setText(mapiOrderResult.getOrder_id());
            holder.orderDate.setText(mapiOrderResult.getOrder_date());
        }
    }

    private void setBottom(ItemBottomHolder holder, int position) {

        MapiOrderResult mapiOrderResult = (MapiOrderResult) mList.get(position).getData();
        if (null != mapiOrderResult) {
            holder.totalAmount.setText(mapiOrderResult.getTotal_amount());
            holder.goodsTotal.setText(String.format("共%s件商品", mapiOrderResult.getGoods_total()));
            holder.expressFee.setText(String.format("(包含运费 ¥ %s)", mapiOrderResult.getExpress_fee()));
            holder.delete.setTag(position);
            holder.pay.setTag(position);
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = (int) view.getTag();
                    if (null != orderDeelListener)
                        orderDeelListener.del(view, pos);
                }
            });
            holder.pay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = (int) view.getTag();
                    if (null != orderDeelListener)
                        orderDeelListener.pay(view, pos);
                }
            });
        }


    }

    private void setSendBottom(ItemSendBottomHolder holder, int position) {
        MapiOrderResult mapiOrderResult = (MapiOrderResult) mList.get(position).getData();
        if (null != mapiOrderResult) {
            holder.totalAmount.setText(mapiOrderResult.getTotal_amount());
            holder.goodsTotal.setText(String.format("共%s件商品", mapiOrderResult.getGoods_total()));
            holder.expressFee.setText(String.format("(包含运费 ¥ %s)", mapiOrderResult.getExpress_fee()));
        }
    }

    private void setReceiveBottom(ItemReceiveBottomHolder holder, int position) {
        holder.receive.setTag(position);
        holder.receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (int) view.getTag();

            }
        });
    }

    class BanlanceHeadViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.image)
        SimpleDraweeView image;
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.root_view)
        LinearLayout rootView;

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
        @Bind(R.id.root_view)
        LinearLayout rootView;
        public BanlanceItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void setBanlanceHead(final BanlanceHeadViewHolder holder, int position) {

        MapiCarResult ware = (MapiCarResult) mList.get(position).getData();

        holder.rootView.setTag(position);
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (int) view.getTag();
                if (null != itemClickListener)
                    itemClickListener.onItemClick(view, pos);
            }
        });

        //创建将要下载的图片的URI
        Uri imageUri = Uri.parse(TextUtils.isEmpty(ware.getImg())?"":ware.getImg());
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(imageUri)
                .setResizeOptions(new ResizeOptions(DPUtil.dip2px(78), DPUtil.dip2px(78)))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(holder.image.getController())
                .setControllerListener(new BaseControllerListener<ImageInfo>())
                .build();
        holder.image.setController(controller);

        holder.title.setText(TextUtils.isEmpty(ware.getTitle()) ? "" : ware.getTitle());

    }

    private void setBanlanceItem(BanlanceItemViewHolder holder, int position) {

        MapiCartItemResult result = (MapiCartItemResult) mList.get(position).getData();

        holder.rootView.setTag(position);
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (int) view.getTag();
                if (null != itemClickListener)
                    itemClickListener.onItemClick(view, pos);
            }
        });

        String count = TextUtils.isEmpty(result.getCount()) ? "1" : result.getCount();
        holder.num.setText("X " + count);

        String color = TextUtils.isEmpty(result.getColor()) ? "" : result.getColor();
        String size = TextUtils.isEmpty(result.getSize()) ? "" : result.getSize();

        holder.attr.setText("颜色：" + color + ";  尺码：" + size);

        holder.price.setText(TextUtils.isEmpty(result.getPrice()) ? "0" : result.getPrice());


    }

    private void setItemThree(ItemThreeViewHolder holder, int position) {


        MapiCartItemResult result = (MapiCartItemResult) mList.get(position).getData();
        holder.rootView.setTag(position);
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (int) view.getTag();
                if (null != itemClickListener)
                    itemClickListener.onItemClick(view, pos);
            }
        });

        String count = TextUtils.isEmpty(result.getCount()) ? "1" : result.getCount();
        holder.num.setText("X " + count);

        holder.price.setText(TextUtils.isEmpty(result.getPrice()) ? "0" : result.getPrice());


    }

}
