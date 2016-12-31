package com.yigu.house.adapter.addr;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yigu.commom.result.MapiAddrResult;
import com.yigu.house.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2016/12/31.
 */
public class AddrListAdapter extends RecyclerView.Adapter<AddrListAdapter.ViewHolder> {
    private LayoutInflater inflater;
    List<MapiAddrResult> mList;

    public AddrListAdapter(Context context, List<MapiAddrResult> list) {
        inflater = LayoutInflater.from(context);
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_manage_addr, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 2;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.edit)
        TextView edit;
        @Bind(R.id.delete)
        TextView delete;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
