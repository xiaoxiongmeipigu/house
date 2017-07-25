package com.yigu.house.adapter.prompt;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yigu.commom.result.MapiSizeResult;
import com.yigu.house.R;
import com.yigu.house.fragment.prompt.FromptSizeFrag;
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
    private Context mContext;
    private  String price = "";
    public PromptSizeAdapter(Context context, List<MapiSizeResult> list) {
        inflater = LayoutInflater.from(context);
        mList = list;
        mContext = context;
    }

    public void setBaiscPrice(String price){
        this.price = price;
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

        String addPrice = TextUtils.isEmpty(sizeResult.getAdd_price())?"0":sizeResult.getAdd_price();
        double addPriceDouble = Double.parseDouble(addPrice);
        double basicPrice = Double.parseDouble(price);
        basicPrice += addPriceDouble;
        holder.price.setText(basicPrice+"");
        holder.title.setText(TextUtils.isEmpty(sizeResult.getSize())?"":sizeResult.getSize());
        holder.purcaseSheetLayout.setZero(true);
        holder.purcaseSheetLayout.setCountEdit(true);
        holder.purcaseSheetLayout.setNum(0);
        holder.purcaseSheetLayout.setTag(position);
        holder.purcaseSheetLayout.setNumListener(new PurcaseSheetLayout.NumInterface() {
            @Override
            public void modify(View view, int num, String price) {
                int pos = (int) view.getTag();
                if(null!=numInterface)
                    numInterface.modify(view,num,pos);
            }
        });
        holder.account.setText(TextUtils.isEmpty(sizeResult.getCount())?"0":sizeResult.getCount());
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

    NumInterface numInterface;

    public interface NumInterface{
        void modify(View view, int num,int position);
    }

    public void setNumListener(NumInterface numInterface){
        this.numInterface = numInterface;
    }

}
