package com.zmq.lottery.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zmq.lottery.R;
import com.zmq.lottery.bean.RoomBean;

import java.util.List;


/**
 * Created by Administrator on 2017/5/16.
 */

public class PlayRoomAdapter extends BaseQuickAdapter<RoomBean, BaseViewHolder> {
    public PlayRoomAdapter(List<RoomBean> data) {
        super(R.layout.item_rv_act_redpacket, data);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, RoomBean item) {
        viewHolder.setText(R.id.tv_room_name, item.getCname());
    }
}
