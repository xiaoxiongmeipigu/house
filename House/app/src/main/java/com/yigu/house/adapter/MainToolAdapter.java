package com.yigu.house.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yigu.commom.result.MapiResourceResult;
import com.yigu.house.R;
import com.yigu.house.interfaces.RecyOnItemClickListener;
import com.yigu.house.util.JGJDataSource;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2016/12/5.
 */
public class MainToolAdapter extends RecyclerView.Adapter<MainToolAdapter.ViewHolder> {
    LayoutInflater inflater;

    private List<MapiResourceResult> mList;

    private RecyOnItemClickListener onItemClickListener;

    public void setOnItemClickListener(RecyOnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public MainToolAdapter(Context context, List<MapiResourceResult> list) {
        inflater = LayoutInflater.from(context);
        mList = list;
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_home_tool, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int resId = R.mipmap.main_xianhuo;
        switch (mList.get(position).getId()){
            case JGJDataSource.TYPE_XIANHUO:
                resId = R.mipmap.main_xianhuo;
                break;
            case JGJDataSource.TYPE_XIANDAN:
                resId = R.mipmap.main_xiadan;
                break;
            case JGJDataSource.TYPE_HUODONG:
                resId = R.mipmap.main_huodong;
                break;
            case JGJDataSource.TYPE_PINTUAN:
                resId = R.mipmap.main_pingtuan;
                break;
            case JGJDataSource.TYPE_DINGZHI:
                resId = R.mipmap.main_dingyang;
                break;
            case JGJDataSource.TYPE_TUIHUO:
                resId = R.mipmap.main_tuihuo;
                break;
            case JGJDataSource.TYPE_VIP:
                resId = R.mipmap.main_vip;
                break;
            case JGJDataSource.TYPE_MORE:
                resId = R.mipmap.main_more;
                break;

        }
        holder.image.setImageResource(resId);
        holder.root_view.setTag(position);
        holder.root_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null!=onItemClickListener)
                    onItemClickListener.onItemClick(view,(Integer)view.getTag());
            }
        });

        holder.title.setText(mList.get(position).getNAME());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.root_view)
        LinearLayout root_view;
        @Bind(R.id.image)
        ImageView image;
        @Bind(R.id.title)
        TextView title;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
