package com.yigu.house.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yigu.commom.result.MapiItemResult;
import com.yigu.commom.util.DebugLog;
import com.yigu.house.R;
import com.yigu.house.interfaces.RecyOnItemClickListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2016/12/5.
 */
public class ItemGridAdapter extends RecyclerView.Adapter<ItemGridAdapter.ViewHolder>{

    private LayoutInflater inflater;
    List<MapiItemResult> mList;
    private RecyOnItemClickListener onItemClickListener;

    public void setOnItemClickListener(RecyOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ItemGridAdapter(Context context, List<MapiItemResult> list) {
        inflater = LayoutInflater.from(context);
        mList = list;
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_home_item_grid,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DebugLog.i("position:"+position);
        holder.root_view.setTag(position);
        holder.root_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null!=onItemClickListener)
                    onItemClickListener.onItemClick(view, (Integer) view.getTag());
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.root_view)
        LinearLayout root_view;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
