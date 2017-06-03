package com.zmq.lottery.adapter;

import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zmq.lottery.R;
import com.zmq.lottery.bean.MsgBean;

import java.util.List;


/**
 * Created by Administrator on 2017/5/17.
 */

public class MsgChatAdapter extends BaseQuickAdapter<MsgBean, BaseViewHolder> {

    public MsgChatAdapter(List<MsgBean> data) {
        super(R.layout.item_rv_act_msg, data);
        Log.d(TAG, "MsgChatAdapter: " + data.size());
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, MsgBean item) {
        if (item.getType() == MsgBean.TYPE_RECEIVED && item.isMoney() && !item.isLeadUp()) { //收到的下注信息
            viewHolder.setVisible(R.id.left_layout_money, true) //接收的下注金额列表
                    .setVisible(R.id.left_layout_msg, false) //接收的消息列表
                    .setVisible(R.id.right_layout_money, false) //发送的下注金额
                    .setVisible(R.id.tv_islead, false) //抢庄中
                    .setVisible(R.id.right_layout_msg, false) //发送的消息列表
                    .setText(R.id.tv_left_money, "下注：" + item.getContent() + "元") //接收的下注金额数目
                    .setImageResource(R.id.im_left_money, item.getIcon())
                    .addOnClickListener(R.id.left_layout_money); //接收的下注人头像
//            Glide.with(mContext).load(item.getIcon()).into((ImageView) viewHolder.getView(R.id.im_left_money));

        } else if (item.getType() == MsgBean.TYPE_RECEIVED && item.isMoney() && item.isLeadUp()) { //收到的抢庄信息
            viewHolder.setVisible(R.id.left_layout_money, true)
                    .setVisible(R.id.left_layout_msg, false)
                    .setVisible(R.id.right_layout_money, false)
                    .setVisible(R.id.tv_islead, true)
                    .setVisible(R.id.right_layout_msg, false)
                    .setText(R.id.tv_left_money, "抢庄：" + item.getContent() + "元") //左抢庄金额数目
                    .setImageResource(R.id.im_left_money, item.getIcon())
                    .addOnClickListener(R.id.left_layout_money);
        } else if (item.getType() == MsgBean.TYPE_RECEIVED && !item.isMoney() && !item.isLeadUp()) { //收到的信息
            viewHolder.setVisible(R.id.left_layout_money, false)
                    .setVisible(R.id.left_layout_msg, true)
                    .setVisible(R.id.right_layout_money, false)
                    .setVisible(R.id.tv_islead, false)
                    .setVisible(R.id.right_layout_msg, false)
                    .setText(R.id.tv_left_msg, item.getContent()) //接收的消息内容
                    .setImageResource(R.id.im_left_msg, item.getIcon());
        } else if (item.getType() == MsgBean.TYPE_SENT && item.isMoney() && !item.isLeadUp()) { //发送的下注信息
            viewHolder.setVisible(R.id.left_layout_money, false)
                    .setVisible(R.id.left_layout_msg, false)
                    .setVisible(R.id.right_layout_money, true)
                    .setVisible(R.id.tv_islead, false)
                    .setVisible(R.id.right_layout_msg, false)
                    .setText(R.id.tv_right_money, "下注：" + item.getContent() + "元")
                    .setImageResource(R.id.im_right_money, item.getIcon())
                    .addOnClickListener(R.id.right_layout_money);
        } else if (item.getType() == MsgBean.TYPE_SENT && item.isMoney() && item.isLeadUp()) { //发送的抢庄信息
            viewHolder.setVisible(R.id.left_layout_money, false)
                    .setVisible(R.id.left_layout_msg, false)
                    .setVisible(R.id.right_layout_money, true)
                    .setVisible(R.id.tv_islead, true)
                    .setVisible(R.id.right_layout_msg, false)
                    .setText(R.id.tv_right_money, "抢庄：" + item.getContent() + "元")
                    .setImageResource(R.id.im_right_msg, item.getIcon())
                    .addOnClickListener(R.id.right_layout_money);
        } else if (item.getType() == MsgBean.TYPE_SENT && !item.isMoney() && !item.isLeadUp()) { //发送的信息
            viewHolder.setVisible(R.id.left_layout_money, false)
                    .setVisible(R.id.left_layout_msg, false)
                    .setVisible(R.id.right_layout_money, false)
                    .setVisible(R.id.tv_islead, false)
                    .setVisible(R.id.right_layout_msg, true)
                    .setText(R.id.tv_right_msg, item.getContent())
                    .setImageResource(R.id.im_right_msg, item.getIcon());
        }
    }
}
