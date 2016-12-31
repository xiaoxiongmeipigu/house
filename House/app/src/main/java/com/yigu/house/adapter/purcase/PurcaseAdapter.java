package com.yigu.house.adapter.purcase;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yigu.commom.result.IndexData;
import com.yigu.commom.result.MapiCarResult;
import com.yigu.house.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by brain on 2016/12/29.
 */
public class PurcaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private LayoutInflater inflater;
    private Context mContext;
    List<IndexData> mList = new ArrayList<>();
    public PurcaseAdapter(Context context,List<IndexData> list) {
        inflater = LayoutInflater.from(context);
        this.mList = list;
        mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        String type = mList.get(position).getType();
        if (type.equals("item")) {
            return 2;
        } else if (type.equals("head")) {
            return 1;
        }
        return 3;
    }

    @Override
    public int getItemCount() {
        return null==mList?0:mList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1:
                return new HeadViewHolder(inflater.inflate(R.layout.item_purcase_head, parent, false));
            case 2:
                return new ItemViewHolder(inflater.inflate(R.layout.item_purcase_item, parent, false));
            case 3:
                return new DividerViewHolder(inflater.inflate(R.layout.item_purcase_divider,parent,false));
            default:
                return new HeadViewHolder(inflater.inflate(R.layout.item_purcase_head, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeadViewHolder) {
            setHead((HeadViewHolder) holder,position);
        } else if (holder instanceof ItemViewHolder) {
            setItem((ItemViewHolder) holder,position);
        }
    }

    class HeadViewHolder extends RecyclerView.ViewHolder {
        public HeadViewHolder(View itemView) {
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

    class DividerViewHolder extends RecyclerView.ViewHolder {
        public DividerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void setHead(HeadViewHolder holder,int position){

    }

    private  void setItem(ItemViewHolder holder,int position){

    }

}
