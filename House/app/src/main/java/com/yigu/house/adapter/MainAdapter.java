package com.yigu.house.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yigu.commom.result.IndexData;
import com.yigu.commom.result.MapiItemResult;
import com.yigu.commom.result.MapiResourceResult;
import com.yigu.house.R;
import com.yigu.house.view.HomeIndentLayout;
import com.yigu.house.view.HomeItemLayout;
import com.yigu.house.view.HomeNewLayout;
import com.yigu.house.view.HomeSliderLayout;
import com.yigu.house.view.HomeStarLayout;
import com.yigu.house.view.HomeToolLayout;
import com.yigu.house.view.HomeWhLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2016/12/5.
 */
public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static int SLIDER_IMAGE = 0;
    private final static int TOOL = 1;
    private final static int ITEM = 2;
    private final static int ITEM_NEWS = 3;
    private final static int ITEM_WH = 4;
    private final static int ITEM_STAR = 5;
    private final static int ITEM_INDENT = 6;

    LayoutInflater inflater;

    private List<IndexData> mList;

    public MainAdapter(Context context, List<IndexData> list) {
        inflater = LayoutInflater.from(context);
        mList = list;
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case SLIDER_IMAGE:
                return new SliderViewHolder(inflater.inflate(R.layout.lay_home_slider, parent, false));
            case TOOL:
                return new ToolViewHolder(inflater.inflate(R.layout.lay_home_tool, parent, false));
            case ITEM:
                return new ItemViewHolder(inflater.inflate(R.layout.lay_home_item, parent, false));
            case ITEM_NEWS:
                return new ItemNewsViewHolder(inflater.inflate(R.layout.lay_home_new, parent, false));
            case ITEM_WH:
                return new ItemWHViewHolder(inflater.inflate(R.layout.lay_home_wh, parent, false));
            case ITEM_STAR:
                return new ItemSTARViewHolder(inflater.inflate(R.layout.lay_home_star, parent, false));
            case ITEM_INDENT:
                return new ItemINDENTViewHolder(inflater.inflate(R.layout.lay_home_indent, parent, false));
            default:
                return new SliderViewHolder(inflater.inflate(R.layout.lay_home_slider, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SliderViewHolder) {
            ((SliderViewHolder)holder).homeSliderLayout.load((List<MapiResourceResult>) mList.get(position).getData());
        }else if(holder instanceof ItemViewHolder){
            ((ItemViewHolder)holder).homeItemLayout.load((List<MapiItemResult>) mList.get(position).getData());
        }else if(holder instanceof ItemNewsViewHolder){
            ((ItemNewsViewHolder)holder).homeNewLayout.load((List<MapiItemResult>) mList.get(position).getData());
        }else if(holder instanceof ItemWHViewHolder){
            ((ItemWHViewHolder)holder).homeWhLayout.load((List<MapiItemResult>) mList.get(position).getData());
        }else if(holder instanceof ItemSTARViewHolder){
            ((ItemSTARViewHolder)holder).homeStarLayout.load((List<MapiItemResult>) mList.get(position).getData());
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (mList.get(position).getType()) {
            case "SCROLL":
                return SLIDER_IMAGE;
            case "TOOL":
                return TOOL;
            case "ITEM":
                return ITEM;
            case "ITEM_NEWS":
                return ITEM_NEWS;
            case "ITEM_WH":
                return ITEM_WH;
            case "ITEM_STAR":
                return ITEM_STAR;
            case "ITEM_INDENT":
                return ITEM_INDENT;
            default:
                return SLIDER_IMAGE;
        }
    }

    class SliderViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.homeSliderLayout)
        HomeSliderLayout homeSliderLayout;
        public SliderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ToolViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.homeToolLayout)
        HomeToolLayout homeToolLayout;
        public ToolViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.homeItemLayout)
        HomeItemLayout homeItemLayout;
        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ItemNewsViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.homeNewLayout)
        HomeNewLayout homeNewLayout;
        public ItemNewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ItemWHViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.homeWhLayout)
        HomeWhLayout homeWhLayout;
        public ItemWHViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ItemSTARViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.homeStarLayout)
        HomeStarLayout homeStarLayout;
        public ItemSTARViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ItemINDENTViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.homeIndentLayout)
        HomeIndentLayout homeIndentLayout;
        public ItemINDENTViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
