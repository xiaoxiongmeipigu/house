package com.yigu.house.fragment.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yigu.commom.result.IndexData;
import com.yigu.commom.result.MapiResourceResult;
import com.yigu.commom.util.DebugLog;
import com.yigu.commom.widget.MainToast;
import com.yigu.house.R;
import com.yigu.house.adapter.MainAdapter;
import com.yigu.house.base.BaseFrag;
import com.yigu.house.util.ControllerUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by brain on 2016/12/6.
 */
public class HomeFragment extends BaseFrag {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.lay_header)
    RelativeLayout layHeader;
    @Bind(R.id.bg_view)
    View bg_view;
    @Bind(R.id.clear_iv)
    ImageView clearIv;
    @Bind(R.id.search_et)
    EditText searchEt;

    private final static String SCROLL = "SCROLL";
    private final static String TOOL = "TOOL";
    private final static String ITEM = "ITEM";
    List<IndexData> mList = new ArrayList<>();
    MainAdapter mAdapter;

    float total = 0f;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        initView();
        initListener();
        load();
        return view;
    }

    private void initView() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new MainAdapter(getActivity(), mList);
        recyclerView.setAdapter(mAdapter);
    }

    private void initListener() {

        searchEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {//EditorInfo.IME_ACTION_SEARCH、EditorInfo.IME_ACTION_SEND等分别对应EditText的imeOptions属性
                    //TODO回车键按下时要执行的操作
                    ControllerUtil.go2PromptList();
                }
                return true;
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
               /* LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if ((newState == RecyclerView.SCROLL_STATE_IDLE) && manager.findLastVisibleItemPosition() > 0 && (manager.findLastVisibleItemPosition() == (manager.getItemCount() - 1))) {
                    loadNext();
                }*/

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int firstVisibleItemPosition = manager.findFirstVisibleItemPosition();
                DebugLog.i("position:" + firstVisibleItemPosition);
                total += dy;
                if (firstVisibleItemPosition == 0) {

                    DebugLog.i("total:" + total);
                    DebugLog.i("hight:" + (recyclerView.getChildAt(0).getBottom() - recyclerView.getChildAt(0).getTop()));
                    float alphaValue = total / (recyclerView.getChildAt(0).getBottom() - recyclerView.getChildAt(0).getTop());
                    if (alphaValue > 1) {
                        alphaValue = 1f;
                    } else if (alphaValue < 0) {
                        alphaValue = 0;
                    }
                    bg_view.setAlpha(alphaValue);
                }

            }
        });

        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count,
                                          int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (charSequence.length() > 0) {
                    clearIv.setVisibility(View.VISIBLE);
                } else {
                    clearIv.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }


    public void load() {
        mList.clear();
        mList.add(new IndexData(0, SCROLL, new ArrayList<MapiResourceResult>()));
        mList.add(new IndexData(1, TOOL, new ArrayList<MapiResourceResult>()));
        mList.add(new IndexData(2, ITEM, new ArrayList<MapiResourceResult>()));
        mList.add(new IndexData(3, "ITEM_NEWS", new ArrayList<MapiResourceResult>()));
        mList.add(new IndexData(4, "ITEM_WH", new ArrayList<MapiResourceResult>()));
        mList.add(new IndexData(5, "ITEM_STAR", new ArrayList<MapiResourceResult>()));
        mList.add(new IndexData(6, "ITEM_INDENT", new ArrayList<MapiResourceResult>()));
        Collections.sort(mList);
        mAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.clear_iv)
    public void onClick() {
        searchEt.setText("");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
