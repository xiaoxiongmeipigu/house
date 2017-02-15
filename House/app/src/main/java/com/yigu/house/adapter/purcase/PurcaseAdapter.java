package com.yigu.house.adapter.purcase;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yigu.commom.result.IndexData;
import com.yigu.commom.result.MapiCarResult;
import com.yigu.commom.result.MapiItemResult;
import com.yigu.commom.widget.MainToast;
import com.yigu.house.R;
import com.yigu.house.interfaces.AdapterSelListener;
import com.yigu.house.view.PurcaseSheetLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2016/12/29.
 */
public class PurcaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private LayoutInflater inflater;
    private Context mContext;
    List<IndexData> mList = new ArrayList<>();

    AdapterSelListener listener;
    public void setOnAdapterSelListener(AdapterSelListener listener){
        this.listener = listener;
    }

    private boolean isDel = false;

    public void setDel(boolean del) {
        isDel = del;
    }

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
        }else if(type.equals("divider")){
            return 3;
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
        @Bind(R.id.root_sel)
        ImageView rootSel;
        public HeadViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_sel)
        ImageView itemSel;
        @Bind(R.id.purcaseSheetLayout)
        PurcaseSheetLayout purcaseSheetLayout;
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

        MapiCarResult ware = (MapiCarResult) mList.get(position).getData();

        boolean isAll = true;
        for (MapiItemResult item : ware.getItems()) {
            if (!item.isSel()) {
                isAll = false;
                break;
            }
        }
        ware.setSel(isAll);
        if(null!=listener){
            listener.isAll();
        }
        if(ware.isSel()){
            holder.rootSel.setImageResource(R.mipmap.sel_right);
        }else{
            holder.rootSel.setImageResource(R.mipmap.sel);
        }
        holder.rootSel.setTag(position);
        holder.rootSel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (int) view.getTag();
                if(null!=listener){
                    listener.notifyParentStatus(position);
                }

            }
        });

    }

    private  void setItem(ItemViewHolder holder,int position){

        MapiItemResult result = (MapiItemResult) mList.get(position).getData();
        if(result.isSel())
            holder.itemSel.setImageResource(R.mipmap.sel_right);
        else
            holder.itemSel.setImageResource(R.mipmap.sel);

        holder.itemSel.setTag(position);
        holder.itemSel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int position = (int) view.getTag();
                if(null!=listener){
                    listener.notifyChildStatus(position);
                }

            }
        });
        holder.purcaseSheetLayout.setCountEdit(false);
        if(isDel){
            holder.purcaseSheetLayout.setCanDo(false);
        }else{
            holder.purcaseSheetLayout.setCanDo(true);
        }

    }



}
