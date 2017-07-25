package com.yigu.house.adapter.indent;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.yigu.commom.result.IndexData;
import com.yigu.commom.result.MapiCarResult;
import com.yigu.commom.util.DPUtil;
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
        @Bind(R.id.num)
        TextView num;
        @Bind(R.id.price)
        TextView price;
        @Bind(R.id.image)
        SimpleDraweeView image;
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.date)
        TextView date;

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

        MapiCarResult ware = (MapiCarResult) mList.get(position).getData();

        String count = TextUtils.isEmpty(ware.getCount())?"1":ware.getCount();
        holder.num.setText("X "+count);

        holder.price.setText(TextUtils.isEmpty(ware.getPrice())?"0":ware.getPrice());
        holder.date.setText(TextUtils.isEmpty(ware.getOrder_date())?"":ware.getOrder_date());

        //创建将要下载的图片的URI
        Uri imageUri = Uri.parse(TextUtils.isEmpty(ware.getImg())?"":ware.getImg());
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(imageUri)
                .setResizeOptions(new ResizeOptions(DPUtil.dip2px(78), DPUtil.dip2px(78)))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(holder.image.getController())
                .setControllerListener(new BaseControllerListener<ImageInfo>())
                .build();
        holder.image.setController(controller);

        holder.title.setText(TextUtils.isEmpty(ware.getTitle()) ? "" : ware.getTitle());

        holder.order.setTag(position);
        holder.order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (int) view.getTag();
                MapiCarResult ware = (MapiCarResult) mList.get(pos).getData();
                ControllerUtil.go2IndentBalance(ware);
            }
        });
    }

    private  void setUnCompleteItem(UnCompleteItemViewHolder holder,int position){

    }

}
