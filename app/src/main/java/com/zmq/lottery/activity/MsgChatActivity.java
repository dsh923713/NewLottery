package com.zmq.lottery.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.JsonSyntaxException;
import com.makeramen.roundedimageview.RoundedImageView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zmq.lottery.R;
import com.zmq.lottery.adapter.MsgChatAdapter;
import com.zmq.lottery.base.BaseActivity;
import com.zmq.lottery.base.RequestResult;
import com.zmq.lottery.bean.BottomPourBean;
import com.zmq.lottery.bean.MsgBean;
import com.zmq.lottery.bean.MsgChatBean;
import com.zmq.lottery.finals.RequestCode;
import com.zmq.lottery.utils.DateUtil;
import com.zmq.lottery.utils.DialogUtil;
import com.zmq.lottery.utils.GsonUtil;
import com.zmq.lottery.utils.HttpUtils;
import com.zmq.lottery.utils.LocalBroadManager;
import com.zmq.lottery.utils.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import me.shaohui.bottomdialog.BaseBottomDialog;
import me.shaohui.bottomdialog.BottomDialog;

public class MsgChatActivity extends BaseActivity implements View.OnClickListener, RequestResult {
    private static final String TAG = "DSH -> MsgChatActivity";
    @BindView(R.id.rv_msg)
    RecyclerView rv_msg; //消息列表
    @BindView(R.id.et_msg)
    EditText et_msg; //消息输入框
    @BindView(R.id.tv_bottom_pour)
    TextView tv_bottom_pour; //下注
    @BindView(R.id.tv_lead_up)
    TextView tv_lead_up; //抢庄
    @BindView(R.id.v_background)
    View v_background;//半透明蒙版
    @BindView(R.id.tv_num_time)
    TextView tv_num_time; //期数
    @BindView(R.id.tv_lead_time)
    TextView tv_lead_time; //抢庄时间
    @BindView(R.id.tv_select_time)
    TextView tv_select_time; //抢庄或下注时间
    @BindView(R.id.rv_record)
    RecyclerView rv_record; //下注玩家列表
    @BindView(R.id.tv_bottom_pour_record)
    TextView tv_bottom_pour_record;//下注记录
    @BindView(R.id.ll_input)
    LinearLayout ll_input;//底部输入栏
    @BindView(R.id.ll_lead_up)
    LinearLayout ll_lead_up; //下注与抢庄
    @BindView(R.id.tv_send_msg)
    TextView tv_send_msg; //发送
    @BindView(R.id.ll_bottom_pour)
    LinearLayout ll_bottom_pour; //下注弹窗
    @BindView(R.id.tv_hundred_money)
    TextView tv_hundred_money; //100元
    @BindView(R.id.tv_two_hundred_money)
    TextView tv_two_hundred_money;//200元
    @BindView(R.id.tv_three_hundred_money)
    TextView tv_three_hundred_money;//300元
    @BindView(R.id.tv_five_hundred_money)
    TextView tv_five_hundred_money;//500元
    @BindView(R.id.et_bottom_pour_money)
    EditText et_bottom_pour_money; //自定义金额
    @BindView(R.id.tv_sure_bottom_pour)
    TextView tv_sure_bottom_pour; //确认下注
    private Button btnClose; //关闭弹窗
    private RoundedImageView ivHead; //头像
    private TextView diaTvName;//昵称
    private Button btnOpen; //打开红包
    private View dia_view; //红包弹窗视图
    private Dialog rpDialog; //红包弹窗

    List<MsgBean> data = new ArrayList<>(); //模拟即时消息数据
    private MsgChatAdapter adapter;

    List<String> recordList = new ArrayList<>();//模拟下注玩家数据
    private CommonAdapter<String> recordAdapter;

    private boolean isShow;//是否显示投注记录
    private boolean isClick1; //是否选择下注100
    private boolean isClick2; //是否选择下注200
    private boolean isClick3; //是否选择下注300
    private boolean isClick5; //是否选择下注500

    private TextView tv_lead_up_money_sure; //抢庄确认
    private EditText et_lead_up_money; //抢庄金额
    private String money = "";
    private BaseBottomDialog dialog; //底部弹窗--抢庄金额输入
    private int id; //房间id
    private String cname;//房间名称
    private int curlistid;
    private JPushToMyReceiver receiver; //接收JPush发送过来的广播
    Map<String, String> map = new HashMap<>(); //参数集合
    private HttpUtils httpUtils;
    private Timer timer;  //计时器
    private TimerTask task;
    private long leadUpTime;//抢庄时间
    private long bottomPourTime;//下注时间

    public MsgChatActivity() {
        super(R.layout.activity_msg_chat);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        if (extras != null) {
            id = extras.getInt("id");
            cname = extras.getString("cname");
            LogUtils.d(id + "--" + cname);
        }
    }


    @Override
    protected void initView() {
        setTitle("");//标题为空
        setTimer();
        registerReceiver();//注册广播 接收极光推送的消息
        setLeftIcon(R.mipmap.ic_back, "", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setRightIcon(R.mipmap.ic_upload, "中奖明细", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showShortToast("开发中...");
            }
        });
        setStatusBar(ContextCompat.getColor(this, R.color.colorAccent));//改变状态栏颜色
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent)); //改变toolbar颜色
        addData();
        LinearLayoutManager msgLayout = new LinearLayoutManager(this);
        rv_msg.setLayoutManager(msgLayout);//显示样式
        adapter = new MsgChatAdapter(data); //声明适配器
        rv_msg.setAdapter(adapter);
        dia_view = View.inflate(this, R.layout.dia_red_packet, null);
        findDialogId();
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                MsgBean msgBean = (MsgBean) adapter.getData().get(position);
                showShortToast(msgBean.getContent() + "---" + position);
                if (rpDialog != null) {
                    rpDialog.show();
                } else {
                    rpDialog = DialogUtil.getDialog1(MsgChatActivity.this, dia_view);
                }
            }
        });

        addRecordList();
        LinearLayoutManager recordLayout = new LinearLayoutManager(this);
        recordAdapter = new CommonAdapter<String>(this, R.layout.item_rv_record, recordList) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.tv_play_name, s);
            }
        };
        rv_record.setLayoutManager(recordLayout);
        rv_record.setAdapter(recordAdapter);
        if (recordList.size() > 0) {
            isShow = true;
            setDrawableRight(R.mipmap.ic_up);
        }
        setOnClicked();
        isSend();
    }

    /**
     * 模拟红包打开缩放效果
     */
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    startActivity(RedPacketActivity.class);
                    if (rpDialog != null) {
                        rpDialog.dismiss();
                        rpDialog.cancel();
                    }
                    btnOpen.clearAnimation();
                    if (handler != null) {
                        handler.removeCallbacksAndMessages(null);
                    }
                }
            });
        }
    };
    /**
     * 红包弹窗的点击事件
     */
    View.OnClickListener rpClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_close:
                    rpDialog.dismiss();
                    btnOpen.clearAnimation();
                    break;
                case R.id.btn_open:
                    Animation operatingAnim = AnimationUtils.loadAnimation(MsgChatActivity.this, R.anim.red_packet_rotate);
                    LinearInterpolator lin = new LinearInterpolator();
                    operatingAnim.setInterpolator(lin);
                    btnOpen.startAnimation(operatingAnim);
                    handler.postDelayed(runnable, 2000);
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 初始化弹窗控件
     */
    private void findDialogId() {
        ivHead = (RoundedImageView) dia_view.findViewById(R.id.iv_head);
        diaTvName = (TextView) dia_view.findViewById(R.id.dia_tv_name);
        btnClose = (Button) dia_view.findViewById(R.id.btn_close);
        btnOpen = (Button) dia_view.findViewById(R.id.btn_open);
        btnClose.setOnClickListener(rpClicked);
        btnOpen.setOnClickListener(rpClicked);
    }

    /**
     * 注册点击事件
     */
    private void setOnClicked() {
        tv_bottom_pour.setOnClickListener(this);
        tv_lead_up.setOnClickListener(this);
        tv_bottom_pour_record.setOnClickListener(this);

        tv_hundred_money.setOnClickListener(this);
        tv_two_hundred_money.setOnClickListener(this);
        tv_three_hundred_money.setOnClickListener(this);
        tv_five_hundred_money.setOnClickListener(this);
        tv_sure_bottom_pour.setOnClickListener(this);
        tv_send_msg.setOnClickListener(this);
        v_background.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ll_input.setVisibility(View.VISIBLE);
                ll_bottom_pour.setVisibility(View.GONE);
                v_background.setVisibility(View.GONE);
                return false;
            }
        });
    }

    /**
     * 注册本地广播
     */
    private void registerReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.zmq.lottery.JPUSH_RECEIVER");
        receiver = new JPushToMyReceiver();
        LocalBroadManager.getInstance().registerReceiver(receiver, filter);
    }

    /**
     * 判断输入框是否有内容，有内容则显示发送按钮，反之则隐藏
     */
    private void isSend() {
        et_msg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    ll_lead_up.setVisibility(View.GONE);
                    tv_send_msg.setVisibility(View.VISIBLE);
                } else {
                    ll_lead_up.setVisibility(View.VISIBLE);
                    tv_send_msg.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 消息列表添加模拟数据
     */
    private void addData() {
        MsgBean msgBean1 = new MsgBean(false, false, "测试数据试试看怎么样？", MsgBean.TYPE_RECEIVED, R.drawable.renma);
        data.add(msgBean1);
        MsgBean msgBean2 = new MsgBean(true, false, "200", MsgBean.TYPE_RECEIVED, R.drawable.renma);
        data.add(msgBean2);
        MsgBean msgBean3 = new MsgBean(false, false, "发表的数据试试看怎么样？", MsgBean.TYPE_SENT, R.drawable.xiaohei);
        data.add(msgBean3);
        MsgBean msgBean4 = new MsgBean(true, false, "500", MsgBean.TYPE_SENT, R.drawable.xiaohei);
        data.add(msgBean4);
        MsgBean msgBean5 = new MsgBean(false, false, "测试数据试试看怎么样？", MsgBean.TYPE_RECEIVED, R.drawable.renma);
        data.add(msgBean5);
        MsgBean msgBean6 = new MsgBean(true, true, "500", MsgBean.TYPE_RECEIVED, R.drawable.renma);
        data.add(msgBean6);
    }

    //投注记录添加模拟数据
    private void addRecordList() {
        for (int i = 1; i < 5; i++) {
            recordList.add("玩家" + i);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() != R.id.tv_send_msg)
            hideSoftInput(v);//隐藏软键盘
        switch (v.getId()) {
            case R.id.tv_bottom_pour: //下注
                ll_input.setVisibility(View.GONE);
                ll_bottom_pour.setVisibility(View.VISIBLE);
                v_background.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_lead_up:  //抢庄
                dialog = BottomDialog.create(getSupportFragmentManager()).setLayoutRes(R.layout.dia_lead_up).setViewListener(new BottomDialog.ViewListener() {
                    @Override
                    public void bindView(View v) {
                        et_lead_up_money = (EditText) v.findViewById(R.id.et_lead_up_money);
                        tv_lead_up_money_sure = (TextView) v.findViewById(R.id.tv_lead_up_money_sure);
                        tv_lead_up_money_sure.setOnClickListener(clickListener);
                    }
                }).show();
                break;
            case R.id.tv_send_msg:  //发送
                String content = et_msg.getText().toString().trim();
                if (!TextUtils.isEmpty(content)) {
                    MsgBean msgBean = new MsgBean(false, false, content, MsgBean.TYPE_SENT, R.drawable.xiaohei);
                    data.add(msgBean);
                    adapter.notifyItemInserted(data.size() - 1);//当有新消息，刷新recyclerview显示
                    rv_msg.scrollToPosition(data.size() - 1);//将recyclerview定位在最后一行
                    et_msg.setText("");//清空输入框数据
                }
                break;
            case R.id.tv_hundred_money: //100元
                isClick1 = true;
                isClick2 = false;
                isClick3 = false;
                isClick5 = false;
                break;
            case R.id.tv_two_hundred_money: // 200元
                isClick1 = false;
                isClick2 = true;
                isClick3 = false;
                isClick5 = false;
                break;
            case R.id.tv_three_hundred_money: //300元
                isClick1 = false;
                isClick2 = false;
                isClick3 = true;
                isClick5 = false;
                break;
            case R.id.tv_five_hundred_money://500元
                isClick1 = false;
                isClick2 = false;
                isClick3 = false;
                isClick5 = true;
                break;
            case R.id.tv_sure_bottom_pour://确认下注
                content = et_bottom_pour_money.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    showShortToast("请选择要下注的金额");
                    return;
                }
                money = content.replace("元", "");//去掉单位
//                bottomPour();
                MsgBean msgBean = new MsgBean(true, false, money, MsgBean.TYPE_SENT, R.drawable.xiaohei);
                data.add(msgBean);
                adapter.notifyItemInserted(data.size() - 1);//当有新消息，刷新recyclerview显示
                rv_msg.scrollToPosition(data.size() - 1);//将recyclerview定位在最后一行
                v_background.setVisibility(View.GONE);
                ll_bottom_pour.setVisibility(View.GONE);
                ll_input.setVisibility(View.VISIBLE);
                isClick1 = false;
                isClick2 = false;
                isClick3 = false;
                isClick5 = false;

                recordList.add("玩家");
                recordAdapter.notifyItemInserted(recordList.size() - 1);
                break;
            case R.id.tv_bottom_pour_record://投注记录
                if (recordList.size() > 0) {
                    if (isShow) {
                        rv_record.setVisibility(View.GONE);
                        isShow = false;
                        setDrawableRight(R.mipmap.ic_down);
                    } else {
                        rv_record.setVisibility(View.VISIBLE);
                        isShow = true;
                        setDrawableRight(R.mipmap.ic_up);
                    }
                }
                break;
            default:
                break;
        }
        checkedHowMoney();
    }

    //改变textview右侧图标
    private void setDrawableRight(int resId) {
        Drawable drawable = ContextCompat.getDrawable(this, resId);
        // 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tv_bottom_pour_record.setCompoundDrawables(null, null, drawable, null);//右侧显示
    }

    /**
     * 判断选中的是哪个下注金额并显示在下方
     */
    private void checkedHowMoney() {
        if (isClick1) {
            tv_hundred_money.setBackgroundResource(R.drawable.pink_shape);
            tv_two_hundred_money.setBackgroundResource(R.drawable.grey_shape);
            tv_three_hundred_money.setBackgroundResource(R.drawable.grey_shape);
            tv_five_hundred_money.setBackgroundResource(R.drawable.grey_shape);
            et_bottom_pour_money.setText(tv_hundred_money.getText());
        } else if (isClick2) {
            tv_hundred_money.setBackgroundResource(R.drawable.grey_shape);
            tv_two_hundred_money.setBackgroundResource(R.drawable.pink_shape);
            tv_three_hundred_money.setBackgroundResource(R.drawable.grey_shape);
            tv_five_hundred_money.setBackgroundResource(R.drawable.grey_shape);
            et_bottom_pour_money.setText(tv_two_hundred_money.getText());
        } else if (isClick3) {
            tv_hundred_money.setBackgroundResource(R.drawable.grey_shape);
            tv_two_hundred_money.setBackgroundResource(R.drawable.grey_shape);
            tv_three_hundred_money.setBackgroundResource(R.drawable.pink_shape);
            tv_five_hundred_money.setBackgroundResource(R.drawable.grey_shape);
            et_bottom_pour_money.setText(tv_three_hundred_money.getText());
        } else if (isClick5) {
            tv_hundred_money.setBackgroundResource(R.drawable.grey_shape);
            tv_two_hundred_money.setBackgroundResource(R.drawable.grey_shape);
            tv_three_hundred_money.setBackgroundResource(R.drawable.grey_shape);
            tv_five_hundred_money.setBackgroundResource(R.drawable.pink_shape);
            et_bottom_pour_money.setText(tv_five_hundred_money.getText());
        } else {
            tv_hundred_money.setBackgroundResource(R.drawable.grey_shape);
            tv_two_hundred_money.setBackgroundResource(R.drawable.grey_shape);
            tv_three_hundred_money.setBackgroundResource(R.drawable.grey_shape);
            tv_five_hundred_money.setBackgroundResource(R.drawable.grey_shape);
            et_bottom_pour_money.setText("");
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null) { //注销广播
            LocalBroadManager.getInstance().unregisterReceiver(receiver);
            receiver = null;
        }
        if (timer != null) { //反注册定时器
            timer.cancel();
            task.cancel();
            timer = null;
        }
    }

    /**
     * 抢庄事件监听
     */
    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            hideSoftInput(v);//隐藏软键盘
            String money = et_lead_up_money.getText().toString().trim();
            if (TextUtils.isEmpty(money)) {
                showShortToast("请输入抢庄金额！");
                return;
            }
            MsgBean msgBean = new MsgBean(true, true, money, MsgBean.TYPE_SENT, R.drawable.xiaohei);
            data.add(msgBean);
            adapter.notifyItemInserted(data.size() - 1);//当有新消息，刷新recyclerview显示
            rv_msg.scrollToPosition(data.size() - 1);//将recyclerview定位在最后一行
            et_msg.setText("");//清空输入框数据
            dialog.dismiss();
        }
    };

    /**
     * 隐藏软键盘
     */
    private void hideSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        // 获取软键盘的显示状态
        boolean isOpen = imm.isActive();
        if (isOpen)
            // 强制隐藏软键盘
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 下注
     */
    private void bottomPour(String id_user, String username, String money) {
        map.put("m", "zhu");
        map.put("act", "xz");
        map.put("id_user", "1");
        map.put("username", "test");
        map.put("money", "100");
        httpUtils = new HttpUtils(this, this, "下注中...", true);
        httpUtils.async(RequestCode.BUSINESS_BOTTOM_POUR, map);
    }

    @Override
    public void onSuccess(String result, String requestCode) {
        if (requestCode.equals(RequestCode.BUSINESS_BOTTOM_POUR)) {
            BottomPourBean bottomPourBean = GsonUtil.GsonToBean(result, BottomPourBean.class);
            showShortToast(bottomPourBean.getErrormsg());
        }
    }

    @Override
    public void onFailure(String result, String requestCode) {
        showShortToast(result);
    }

    /**
     * 接收JPush推送过来的信息广播
     */
    class JPushToMyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String msg = intent.getStringExtra("msg");
            LogUtils.d(msg);
            try {
                MsgChatBean bean = GsonUtil.GsonToBean(msg, MsgChatBean.class);
                showShortToast("执行....." + bean.getStatus());
                if (bean.getStatus().equals("created")) { //抢庄
                    leadUpTime = bean.getBalance();
                } else if (bean.getStatus().equals("zhu")) { //下注
                    bottomPourTime = bean.getBalance();
                } else if (bean.getStatus().equals("success")) { //结算成功

                }
            } catch (JsonSyntaxException jse) {
                showShortToast("数据格式错误");
            } catch (Exception e) {
                System.out.print("数据异常");
            }
        }
    }

    /**
     * 设置定时器
     */
    private void setTimer() {
        if (timer == null) {
            timer = new Timer();
        }
        task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (bottomPourTime > 0) { //下注
                            bottomPourTime--;
                            tv_select_time.setVisibility(View.VISIBLE);
                            tv_lead_time.setVisibility(View.VISIBLE);
                            tv_select_time.setText(R.string.bottom_pour_time);//下注时间
                            tv_lead_time.setText(DateUtil.getCutDown(bottomPourTime));
                            tv_lead_up.setVisibility(View.GONE);
                            tv_bottom_pour.setVisibility(View.VISIBLE);
                            return;
                        }
                        if (leadUpTime > 0) { //抢庄
                            leadUpTime--;
                            tv_select_time.setVisibility(View.VISIBLE);
                            tv_lead_time.setVisibility(View.VISIBLE);
                            tv_select_time.setText(R.string.lead_up_time);//抢庄时间
                            tv_lead_time.setText(DateUtil.getCutDown(leadUpTime));
                            tv_lead_up.setVisibility(View.VISIBLE);
                            tv_bottom_pour.setVisibility(View.GONE);
                            return;
                        }
                        if (bottomPourTime == 0 || leadUpTime == 0) { //未开始或已结束阶段
                            tv_select_time.setVisibility(View.GONE);
                            tv_lead_time.setVisibility(View.GONE);
                            tv_lead_up.setVisibility(View.GONE);
                            tv_bottom_pour.setVisibility(View.GONE);
                            return;
                        }
                    }
                });
            }
        };
        timer.schedule(task, 1000, 1000);
    }


}
