package com.yigu.house.fragment.person;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yigu.house.R;
import com.yigu.house.base.BaseFrag;
import com.yigu.house.util.ControllerUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link BaseFrag} subclass.
 */
public class PersonFragment extends BaseFrag {


    public PersonFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_person, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.addr_ll)
    public void onClick() {
        ControllerUtil.go2AddrList();
    }
}
