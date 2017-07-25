package com.yigu.house.adapter.addr;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yigu.commom.result.MapiAddrResult;
import com.yigu.house.R;
import com.yigu.house.interfaces.RecyOnItemClickListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2016/12/31.
 */
public class AddrListAdapter extends RecyclerView.Adapter<AddrListAdapter.ViewHolder> {
    private LayoutInflater inflater;
    List<MapiAddrResult> mList;

    private boolean fromBalance = false;
    boolean isShowDel = true;
    private RecyOnItemClickListener onItemClickListener;

    public void setOnItemClickListener(RecyOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setFromBalance(boolean fromBalance) {
        this.fromBalance = fromBalance;
    }

    public void setShowDel(boolean showDel) {
        isShowDel = showDel;
    }

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
        MapiAddrResult mapiAddrResult = mList.get(position);
        holder.name.setText("收货人："+ (TextUtils.isEmpty(mapiAddrResult.getName())?"":mapiAddrResult.getName()));
        holder.mobile.setText("联系电话："+ (TextUtils.isEmpty(mapiAddrResult.getMobile())?"":mapiAddrResult.getMobile()));
        holder.address.setText("收货地址："+ mapiAddrResult.getProvince()+" "+ mapiAddrResult.getCity()+" "+mapiAddrResult.getArea()+" "+mapiAddrResult.getAddress());
        if(mapiAddrResult.getIs_default().equals("0"))
            holder.defaultIv.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.sel_rect_black,0,0,0);
        else if(mapiAddrResult.getIs_default().equals("1"))
            holder.defaultIv.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.sel_rect_orange,0,0,0);

        holder.defaultIv.setTag(position);
        holder.defaultIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (int) view.getTag();
                if(mList.get(pos).getIs_default().endsWith("0")){
                    if(null!=addrListener)
                        addrListener.defaultAddr(pos);
                }
            }
        });

        holder.edit.setTag(position);
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (int) view.getTag();
                if(null!=addrListener)
                    addrListener.edit(pos);
            }
        });

        if(isShowDel){
            holder.delete.setVisibility(View.VISIBLE);
            holder.delete.setTag(position);
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = (int) view.getTag();
                    if(null!=addrListener)
                        addrListener.del(pos);

                }
            });
        }else{
            holder.delete.setVisibility(View.GONE);
        }

        holder.root_view.setTag(position);
        holder.root_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (int) view.getTag();
                if(fromBalance){
                    if(null!=onItemClickListener)
                        onItemClickListener.onItemClick(view,pos);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.edit)
        TextView edit;
        @Bind(R.id.delete)
        TextView delete;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.mobile)
        TextView mobile;
        @Bind(R.id.address)
        TextView address;
        @Bind(R.id.defaultIv)
        TextView defaultIv;
        @Bind(R.id.root_view)
        LinearLayout root_view;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface AddrListener{
        void del(int postion);
        void edit(int postion);
        void defaultAddr(int postion);
    }

    private  AddrListener addrListener;

    public void setAddrListener(AddrListener addrListener){
        this.addrListener = addrListener;
    }

}
