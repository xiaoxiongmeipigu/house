package com.yigu.house.adapter.prompt;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yigu.commom.result.IndexData;
import com.yigu.commom.result.MapiItemResult;
import com.yigu.commom.result.MapiResourceResult;
import com.yigu.commom.util.DebugLog;
import com.yigu.commom.widget.MainToast;
import com.yigu.house.R;
import com.yigu.house.interfaces.RecyOnItemClickListener;
import com.yigu.house.view.GroupToolLayout;
import com.yigu.house.view.HomeItemLayout;
import com.yigu.house.view.HomeSliderLayout;
import com.yigu.house.view.HomeToolLayout;
import com.yigu.house.view.IndentToolLayout;
import com.yigu.house.view.PromptItemLayout;
import com.yigu.house.view.PromptSliderLayout;
import com.yigu.house.view.PromptToolLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2016/12/14.
 */
public class PromptInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private LayoutInflater inflater;
    private List<IndexData> mList;
    private RecyOnItemClickListener onItemClickListener;

    private final static int SLIDER_IMAGE = 0;
    private final static int TOOL = 1;
    private final static int ITEM = 2;
    private final static int TOOL_GROUP = 3;
    private final static int TOOL_INDENT = 4;
    private final static int ITEM_GROUP = 5;
    private final static int ITEM_INDENT = 6;
    private final static int ITEM_VIP = 7;

    public void setOnItemClickListener(RecyOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public PromptInfoAdapter(Context context, List<IndexData> list) {
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
                return new SliderViewHolder(inflater.inflate(R.layout.lay_prompt_slider, parent, false));
            case TOOL:
                return new ToolViewHolder(inflater.inflate(R.layout.lay_prompt_tool, parent, false));
            case ITEM:
                return new ItemViewHolder(inflater.inflate(R.layout.lay_prompt_item, parent, false));
            case ITEM_GROUP:
                return new ItemGroupViewHolder(inflater.inflate(R.layout.lay_prompt_item, parent, false));
            case ITEM_INDENT:
                return new ItemIndentViewHolder(inflater.inflate(R.layout.lay_prompt_item, parent, false));
            case ITEM_VIP:
                return new ItemVipViewHolder(inflater.inflate(R.layout.lay_prompt_item, parent, false));
            case TOOL_GROUP:
                return new GroupToolViewHolder(inflater.inflate(R.layout.lay_group_tool, parent, false));
            case TOOL_INDENT:
                return new IndentToolViewHolder(inflater.inflate(R.layout.lay_indent_tool, parent, false));
            default:
                return new SliderViewHolder(inflater.inflate(R.layout.lay_prompt_slider, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SliderViewHolder) {
            ((SliderViewHolder)holder).promptSliderLayout.load((List<MapiResourceResult>) mList.get(position).getData());
        }else if(holder instanceof ItemViewHolder){
            ((ItemViewHolder)holder).promptItemLayout.load((List<MapiItemResult>) mList.get(position).getData(),0);
        }else if(holder instanceof ToolViewHolder){
            ((ToolViewHolder)holder).promptToolLayout.load((MapiItemResult) mList.get(position).getData());
            ((ToolViewHolder)holder).promptToolLayout.setSizeOpenListener(new PromptToolLayout.SizeOpenListener() {
                @Override
                public void open() {
                    DebugLog.i("PromptInfoAdapter=>selSizeLL");
                    if(null!=sizeOpenListener)
                        sizeOpenListener.open();
                }
            });
        }else if(holder instanceof GroupToolViewHolder){
            ((GroupToolViewHolder)holder).groupToolLayout.load((MapiItemResult) mList.get(position).getData());
            ((GroupToolViewHolder)holder).groupToolLayout.setSizeOpenListener(new GroupToolLayout.SizeOpenListener() {
                @Override
                public void open() {
                    DebugLog.i("GroupToolViewHolder=>selSizeLL");
                    if(null!=sizeOpenListener)
                        sizeOpenListener.open();
                }
            });
        }else if(holder instanceof IndentToolViewHolder){
            ((IndentToolViewHolder)holder).intentToolLayout.load((MapiItemResult) mList.get(position).getData());
        }else if(holder instanceof ItemGroupViewHolder){
            ((ItemGroupViewHolder)holder).promptItemLayout.load((List<MapiItemResult>) mList.get(position).getData(),1);
        }else if(holder instanceof ItemIndentViewHolder){
            ((ItemIndentViewHolder)holder).promptItemLayout.load((List<MapiItemResult>) mList.get(position).getData(),2);
        }else if(holder instanceof ItemVipViewHolder){
            ((ItemVipViewHolder)holder).promptItemLayout.load((List<MapiItemResult>) mList.get(position).getData(),3);
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
            case "ITEM_GROUP":
                return ITEM_GROUP;
            case "ITEM_INDENT":
                return ITEM_INDENT;
            case "ITEM_VIP":
                return ITEM_VIP;
            case "TOOL_GROUP":
                return TOOL_GROUP;
            case "TOOL_INDENT":
                return TOOL_INDENT;
            default:
                return SLIDER_IMAGE;
        }
    }

    class SliderViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.promptSliderLayout)
        PromptSliderLayout promptSliderLayout;
        public SliderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ToolViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.promptToolLayout)
        PromptToolLayout promptToolLayout;
        public ToolViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class GroupToolViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.groupToolLayout)
        GroupToolLayout groupToolLayout;
        public GroupToolViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class IndentToolViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.intentToolLayout)
        IndentToolLayout intentToolLayout;
        public IndentToolViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.promptItemLayout)
        PromptItemLayout promptItemLayout;
        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ItemGroupViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.promptItemLayout)
        PromptItemLayout promptItemLayout;
        public ItemGroupViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ItemIndentViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.promptItemLayout)
        PromptItemLayout promptItemLayout;
        public ItemIndentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ItemVipViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.promptItemLayout)
        PromptItemLayout promptItemLayout;
        public ItemVipViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public SizeOpenListener sizeOpenListener;

    public interface SizeOpenListener{
        void open();
    }

    public void setSizeOpenListener(SizeOpenListener sizeOpenListener){
        this.sizeOpenListener = sizeOpenListener;
    }

}
