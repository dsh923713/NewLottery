package com.zmq.lottery.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zmq.lottery.R;
import com.zmq.lottery.base.BaseFragment;


/**
 * Created by Administrator on 2017/5/15.
 */

public class RechargeFragment extends BaseFragment {
    @Override
    protected View initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recharge, container, false);
        return view;
    }

    @Override
    protected void initView(View view) {

    }

}
