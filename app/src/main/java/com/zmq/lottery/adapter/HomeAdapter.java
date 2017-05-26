package com.zmq.lottery.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zmq.lottery.R;
import com.zmq.lottery.bean.HomeBean;

import java.util.List;


/**
 * Created by Administrator on 2017/5/15.
 */

public class HomeAdapter extends BaseQuickAdapter<HomeBean, BaseViewHolder> {
    public HomeAdapter(List<HomeBean> list) {
        super(R.layout.item_rv_frag_home, list);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, HomeBean item) {
        viewHolder.setImageResource(R.id.im_rlv_head, item.getImageId())
                .setText(R.id.tv_rlv_title, item.getTitle());
    }
}
