package com.yigu.house.adapter.order;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yigu.commom.result.IndexData;
import com.yigu.house.R;

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
        }else if(type.equals("send_bottom")){
            return 5;
        }else if(type.equals("receive_bottom")){
            return 6;
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
        }else if (holder instanceof ItemReceiveBottomHolder) {
            setReceiveBottom((ItemReceiveBottomHolder) holder, position);
        }
    }

    class ItemHeadHolder extends RecyclerView.ViewHolder {
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

    class ItemBottomHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.delete)
        TextView delete;
        @Bind(R.id.pay)
        TextView pay;

        public ItemBottomHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ItemSendBottomHolder extends RecyclerView.ViewHolder {
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

    }

    private void setBottom(ItemBottomHolder holder, int position) {
        holder.delete.setTag(position);
        holder.pay.setTag(position);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (int) view.getTag();

            }
        });
        holder.pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (int) view.getTag();

            }
        });
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

}
