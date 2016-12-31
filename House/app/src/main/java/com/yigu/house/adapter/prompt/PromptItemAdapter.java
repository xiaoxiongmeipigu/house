package com.yigu.house.adapter.prompt;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yigu.commom.result.IndexData;
import com.yigu.commom.result.MapiItemResult;
import com.yigu.house.R;
import com.yigu.house.interfaces.RecyOnItemClickListener;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by brain on 2016/12/14.
 */
public class PromptItemAdapter extends RecyclerView.Adapter<PromptItemAdapter.ViewHolder>{

    private LayoutInflater inflater;
    private List<MapiItemResult> mList;
    private RecyOnItemClickListener onItemClickListener;

    public void setOnItemClickListener(RecyOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public PromptItemAdapter(Context context, List<MapiItemResult> list) {
        inflater = LayoutInflater.from(context);
        mList = list;
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_prompt_item,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
