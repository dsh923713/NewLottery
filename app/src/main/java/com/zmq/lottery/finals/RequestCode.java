package com.zmq.lottery.finals;

/**
 * Created by Administrator on 2017/5/24.
 */

public class RequestCode {
    public static final String GET_RED_ROOM = "get_red_room"; //红包牛牛
    public static final String GET_BEIJING_ROOM = "get_beijing_room"; //北京28
    public static final String SEND_VALID_CODE = "send_valid_code"; //发送验证码
    public static final String REGISTER_ACCOUNT = "register_account"; //注册
    public static final String LOGIN_ACCOUNT = "login_account"; //登陆
    public static final String BUSINESS_DETAIL_ALL= "business_detail_all"; //交易类型-全部
    public static final String BUSINESS_DETAIL_RECHARGE= "business_detail_recharge";//交易类型-充值
    public static final String BUSINESS_DETAIL_BUNKO= "business_detail_bunko";//交易类型-输赢
    public static final String BUSINESS_DETAIL_WITHDRAW_CASH= "business_detail_withdraw_cash";//交易类型-提现
    public static final String BUSINESS_DETAIL_POUNDAGE= "business_detail_poundage";//交易类型-手续费
    public static final String BUSINESS_DETAIL_LEAD_UP= "business_detail_lead_up";//交易类型-抢庄
}
