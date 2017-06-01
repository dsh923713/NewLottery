package com.zmq.lottery.activity;

import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zmq.lottery.R;
import com.zmq.lottery.base.BaseActivity;
import com.zmq.lottery.base.RequestResult;
import com.zmq.lottery.bean.BusinessBean;
import com.zmq.lottery.finals.RequestCode;
import com.zmq.lottery.utils.GsonUtil;
import com.zmq.lottery.utils.HttpUtils;
import com.zmq.lottery.utils.SPUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class BusinessRecordActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener
        , View.OnClickListener, RequestResult {
    @BindView(R.id.tv_all)
    TextView tv_all;  //全部
    @BindView(R.id.tv_recharge)
    TextView tv_recharge;  //充值
    @BindView(R.id.tv_bunko)
    TextView tv_bunko;  //输赢
    @BindView(R.id.tv_withdraw_cash)
    TextView tv_withdraw_cash;  //提现
    @BindView(R.id.tv_poundage)
    TextView tv_poundage;  //手续费
    @BindView(R.id.tv_lead_up)
    TextView tv_lead_up;  //抢庄
    @BindView(R.id.srl_refurbish)
    SwipeRefreshLayout srl_refurbish;//下拉刷新
    @BindView(R.id.lv_business)
    ListView lv_business;//显示记录
    private List<BusinessBean.ResultBean> data = new ArrayList<>();//数据
    private Map<String, String> map = new HashMap<>();//参数集合
    private boolean isChecked1 = true;//全部按钮是否选中
    private boolean isChecked2;//充值按钮是否选中
    private boolean isChecked3;//输赢按钮是否选中
    private boolean isChecked4;//提现按钮是否选中
    private boolean isChecked5;//手续费按钮是否选中
    private boolean isChecked6;//抢庄按钮是否选中
    private CommonAdapter<BusinessBean.ResultBean> adapter;
    private HttpUtils httpUtils;
    private String code;
    private String kind = "";

    public BusinessRecordActivity() {
        super(R.layout.activity_business_record);
    }


    @Override
    protected void initView() {
        code = SPUtil.getString("alias");
        map.clear();
        httpUtils = new HttpUtils(BusinessRecordActivity.this, BusinessRecordActivity.this, "正在获取", true);
        map.put("m", "score");
        map.put("act", "detail");
        map.put("code", "001");
        map.put("kind", kind);
        httpUtils.async(RequestCode.BUSINESS_DETAIL_ALL, map);
        setTitle("交易记录"); //标题
        //返回按钮
        setLeftIcon(R.mipmap.ic_back, "返回", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //注册点击监听事件
        tv_all.setOnClickListener(this);
        tv_recharge.setOnClickListener(this);
        tv_bunko.setOnClickListener(this);
        tv_withdraw_cash.setOnClickListener(this);
        tv_poundage.setOnClickListener(this);
        tv_lead_up.setOnClickListener(this);

        srl_refurbish.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorAccent));//下拉刷新时控件的颜色
        srl_refurbish.setOnRefreshListener(this);//注册下拉刷新
        adapter = new CommonAdapter<BusinessBean.ResultBean>(this, R.layout.item_lv_act_business, data) {
            @Override
            protected void convert(ViewHolder viewHolder, BusinessBean.ResultBean item, int position) {
                viewHolder.setText(R.id.tv_name, SPUtil.getString("alias"))
                        .setText(R.id.tv_style, item.getKind())
                        .setText(R.id.tv_time, item.getOpertime())
                        .setText(R.id.tv_money, item.getScore());
            }
        };
        lv_business.setAdapter(adapter);
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
//        map.put("m", "score");
//        map.put("act", "detail");
//        map.put("code", "001");
        map.put("kind", kind);
        httpUtils.async(RequestCode.BUSINESS_DETAIL_RECHARGE, map);
//        srl_refurbish.setRefreshing(false);//刷新结束 下拉框消失
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_all://全部
                isChecked1 = true;
                isChecked2 = false;
                isChecked3 = false;
                isChecked4 = false;
                isChecked5 = false;
                isChecked6 = false;
                srl_refurbish.setRefreshing(true);
                break;
            case R.id.tv_recharge://下注
                isChecked2 = true;
                isChecked1 = false;
                isChecked3 = false;
                isChecked4 = false;
                isChecked5 = false;
                isChecked6 = false;
                srl_refurbish.setRefreshing(true);
                break;
            case R.id.tv_bunko://充值
                isChecked3 = true;
                isChecked1 = false;
                isChecked2 = false;
                isChecked4 = false;
                isChecked5 = false;
                isChecked6 = false;
                srl_refurbish.setRefreshing(true);
                break;
            case R.id.tv_withdraw_cash://提现
                isChecked4 = true;
                isChecked1 = false;
                isChecked2 = false;
                isChecked3 = false;
                isChecked5 = false;
                isChecked6 = false;
                srl_refurbish.setRefreshing(true);
                break;
            case R.id.tv_poundage://手续费
                isChecked5 = true;
                isChecked1 = false;
                isChecked2 = false;
                isChecked3 = false;
                isChecked4 = false;
                isChecked6 = false;
                srl_refurbish.setRefreshing(true);
                break;
            case R.id.tv_lead_up://手续费
                isChecked6 = true;
                isChecked5 = false;
                isChecked1 = false;
                isChecked2 = false;
                isChecked3 = false;
                isChecked4 = false;
                srl_refurbish.setRefreshing(true);
                break;
            default:
                break;
        }
        isSelected();
    }

    private void isSelected() {
        if (isChecked1) { //全部
            tv_all.setClickable(false);
            kind = "";
//            map.put("m", "score");
//            map.put("act", "detail");
//            map.put("code", "001");
            map.put("kind", kind);
            httpUtils.async(RequestCode.BUSINESS_DETAIL_ALL, map);
            tv_all.setTextColor(ContextCompat.getColor(BusinessRecordActivity.this, R.color.red));
        } else {
            tv_all.setClickable(true);
            tv_all.setTextColor(ContextCompat.getColor(BusinessRecordActivity.this, R.color.black));
        }
        if (isChecked2) { //充值
            tv_recharge.setClickable(false);
            kind = "充值";
//            map.put("m", "score");
//            map.put("act", "detail");
//            map.put("code", "001");
            map.put("kind", kind);
            httpUtils.async(RequestCode.BUSINESS_DETAIL_RECHARGE, map);
            tv_recharge.setTextColor(ContextCompat.getColor(BusinessRecordActivity.this, R.color.red));
        } else {
            tv_recharge.setClickable(true);
            tv_recharge.setTextColor(ContextCompat.getColor(BusinessRecordActivity.this, R.color.black));
        }
        if (isChecked3) {  //输赢
            tv_bunko.setClickable(false);
            kind = "输赢";
//            map.put("m", "score");
//            map.put("act", "detail");
//            map.put("code", "001");
            map.put("kind", kind);
            httpUtils.async(RequestCode.BUSINESS_DETAIL_BUNKO, map);
            tv_bunko.setTextColor(ContextCompat.getColor(BusinessRecordActivity.this, R.color.red));
        } else {
            tv_bunko.setClickable(true);
            tv_bunko.setTextColor(ContextCompat.getColor(BusinessRecordActivity.this, R.color.black));
        }
        if (isChecked4) { //提现
            tv_withdraw_cash.setClickable(false);
            kind = "提现";
//            map.put("m", "score");
//            map.put("act", "detail");
//            map.put("code", "001");
            map.put("kind", kind);
            httpUtils.async(RequestCode.BUSINESS_DETAIL_WITHDRAW_CASH, map);
            tv_withdraw_cash.setTextColor(ContextCompat.getColor(BusinessRecordActivity.this, R.color.red));
        } else {
            tv_withdraw_cash.setClickable(true);
            tv_withdraw_cash.setTextColor(ContextCompat.getColor(BusinessRecordActivity.this, R.color.black));
        }
        if (isChecked5) { //手续费
            tv_poundage.setClickable(false);
            kind = "手续费";
//            map.put("m", "score");
//            map.put("act", "detail");
//            map.put("code", "001");
            map.put("kind", kind);
            httpUtils.async(RequestCode.BUSINESS_DETAIL_POUNDAGE, map);
            tv_poundage.setTextColor(ContextCompat.getColor(BusinessRecordActivity.this, R.color.red));
        } else {
            tv_poundage.setClickable(true);
            tv_poundage.setTextColor(ContextCompat.getColor(BusinessRecordActivity.this, R.color.black));
        }
        if (isChecked6) { //抢庄
            tv_lead_up.setClickable(false);
            kind = "抢庄";
//            map.put("m", "score");
//            map.put("act", "detail");
//            map.put("code", "001");
            map.put("kind", kind);
            httpUtils.async(RequestCode.BUSINESS_DETAIL_LEAD_UP, map);
            tv_lead_up.setTextColor(ContextCompat.getColor(BusinessRecordActivity.this, R.color.red));
        } else {
            tv_lead_up.setClickable(true);
            tv_lead_up.setTextColor(ContextCompat.getColor(BusinessRecordActivity.this, R.color.black));
        }
    }

    @Override
    public void onSuccess(String result, String requestCode) {
        BusinessBean bean = GsonUtil.GsonToBean(result, BusinessBean.class);
        srl_refurbish.setRefreshing(false);
        data.clear();
        if (bean.getCode() == 0) {
            if (RequestCode.BUSINESS_DETAIL_ALL.equals(requestCode)) { //全部
                data.addAll(bean.getResult());
                adapter.notifyDataSetChanged();
            } else if (RequestCode.BUSINESS_DETAIL_RECHARGE.equals(requestCode)) { //充值
                data.addAll(bean.getResult());
                adapter.notifyDataSetChanged();
            } else if (RequestCode.BUSINESS_DETAIL_BUNKO.equals(requestCode)) { //输赢
                data.addAll(bean.getResult());
                adapter.notifyDataSetChanged();
            } else if (RequestCode.BUSINESS_DETAIL_WITHDRAW_CASH.equals(requestCode)) { //提现
                data.addAll(bean.getResult());
                adapter.notifyDataSetChanged();
            } else if (RequestCode.BUSINESS_DETAIL_POUNDAGE.equals(requestCode)) { //手续费
                data.addAll(bean.getResult());
                adapter.notifyDataSetChanged();
            } else if (RequestCode.BUSINESS_DETAIL_LEAD_UP.equals(requestCode)) { //抢庄
                data.addAll(bean.getResult());
                adapter.notifyDataSetChanged();
            }
        } else
            showShortToast(bean.getMessage());
    }

    @Override
    public void onFailure(String result, String requestCode) {
        showShortToast(result);
    }
}
