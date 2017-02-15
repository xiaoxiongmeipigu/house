package com.yigu.house.adapter.indent;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yigu.commom.result.IndexData;
import com.yigu.house.R;
import com.yigu.house.util.ControllerUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2017/1/9.
 */
public class IndentCompleteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private LayoutInflater inflater;
    private Context mContext;
    List<IndexData> mList = new ArrayList<>();


    public IndentCompleteAdapter(Context context,List<IndexData> list) {
        inflater = LayoutInflater.from(context);
        this.mList = list;
        mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        String type = mList.get(position).getType();
        if (type.equals("divider")) {
            return 3;
        } else if (type.equals("item")) {
            return 1;
        }else if(type.equals("unComplete_item")){
            return 2;
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
                return new ItemViewHolder(inflater.inflate(R.layout.item_indent_complete, parent, false));
            case 2:
                return new UnCompleteItemViewHolder(inflater.inflate(R.layout.item_indent_uncomplete, parent, false));
            case 3:
                return new DividerViewHolder(inflater.inflate(R.layout.item_indent_complete_divider,parent,false));
            default:
                return new DividerViewHolder(inflater.inflate(R.layout.item_indent_complete_divider,parent,false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            setItem((ItemViewHolder) holder,position);
        }else if(holder instanceof UnCompleteItemViewHolder){
            setUnCompleteItem((UnCompleteItemViewHolder) holder,position);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.order)
        TextView order;
        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    class UnCompleteItemViewHolder extends RecyclerView.ViewHolder {
        public UnCompleteItemViewHolder(View itemView) {
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

    private  void setItem(ItemViewHolder holder,int position){
        holder.order.setTag(position);
        holder.order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ControllerUtil.go2IndentBalance();
            }
        });
    }

    private  void setUnCompleteItem(UnCompleteItemViewHolder holder,int position){

    }

}
