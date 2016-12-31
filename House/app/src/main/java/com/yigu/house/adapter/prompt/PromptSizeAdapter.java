package com.yigu.house.adapter.prompt;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yigu.commom.result.MapiSizeResult;
import com.yigu.house.R;
import com.yigu.house.view.PurcaseSheetLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2016/12/17.
 */
public class PromptSizeAdapter extends RecyclerView.Adapter<PromptSizeAdapter.ViewHolder> {


    private LayoutInflater inflater;
    private List<MapiSizeResult> mList;

    public PromptSizeAdapter(Context context, List<MapiSizeResult> list) {
        inflater = LayoutInflater.from(context);
        mList = list;
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_sel_size, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MapiSizeResult sizeResult = mList.get(position);
        holder.title.setText(sizeResult.getTitle());
        holder.price.setText(sizeResult.getPrice());
        holder.purcaseSheetLayout.setNum(Integer.parseInt(sizeResult.getNum()));
        holder.account.setText(sizeResult.getAccount());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.price)
        TextView price;
        @Bind(R.id.purcaseSheetLayout)
        PurcaseSheetLayout purcaseSheetLayout;
        @Bind(R.id.account)
        TextView account;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
