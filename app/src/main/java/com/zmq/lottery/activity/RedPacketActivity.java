package com.zmq.lottery.activity;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zmq.lottery.R;
import com.zmq.lottery.base.BaseActivity;
import com.zmq.lottery.bean.RedPacketBean;
import com.zmq.lottery.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/6/2.
 */

public class RedPacketActivity extends BaseActivity {

    @BindView(R.id.iv_head)
    RoundedImageView ivHead;//顶部头像
    @BindView(R.id.tv_someone_red_packet)
    TextView tvSomeoneRedPacket;//XX人的红包
    @BindView(R.id.tv_blessing)
    TextView tvBlessing; //祝福语
    @BindView(R.id.tv_cash_num)
    TextView tvCashNum; //金额数
    @BindView(R.id.tv_cash_packet)
    TextView tvCashPacket; //零钱包
    @BindView(R.id.tv_red_packet_time)
    TextView tvRedPacketTime; //红包被抢光时间
    @BindView(R.id.lv_cash_num)
    ListView lvCashNum; //listview显示抢红包人数
    private List<RedPacketBean> data; //模拟数据
    private CommonAdapter<RedPacketBean> adapter; //适配器

    public RedPacketActivity() {
        super(R.layout.activity_red_packet);
    }

    @Override
    protected void initView() {
        setTitle("红包详情");
        setLeftIcon(0, "关闭", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setBackgroundColor(ContextCompat.getColor(context, R.color.te45a34));
        setStatusBar(ContextCompat.getColor(context, R.color.te45a34));
        addData();
        adapter = new CommonAdapter<RedPacketBean>(this, R.layout.item_red_packet, data) {
            @Override
            protected void convert(ViewHolder holder, RedPacketBean item, int position) {
                holder.setImageResource(R.id.iv_head, item.getHeadId())  //头像
                        .setText(R.id.tv_name, item.getName())  //昵称
                        .setText(R.id.tv_time, DateUtil.toTime9(item.getTime())) //红包获取时间
                        .setText(R.id.tv_cash_num, item.getCashNum() + "元"); //抢到的红包金额
                if (item.getBestOfLuck()) {
                    holder.setVisible(R.id.tv_best_of_luck, true); //是否显示手气最佳
                } else {
                    holder.setVisible(R.id.tv_best_of_luck, false);
                }
            }
        };
        lvCashNum.setAdapter(adapter);
    }

    /**
     * 模拟数据
     */
    private void addData() {
        data = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            RedPacketBean redPacketBean = new RedPacketBean(R.drawable.xiaohei, "张晓明" + i, System.currentTimeMillis(), 0.02 * (i+3), false);
            data.add(redPacketBean);
        }
        RedPacketBean redPacketBean2 = new RedPacketBean(R.drawable.xiaohei, "张晓明", System.currentTimeMillis(), 0.85, true);
        data.add(redPacketBean2);
    }
}
