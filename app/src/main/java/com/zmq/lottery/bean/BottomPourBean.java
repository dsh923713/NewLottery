package com.zmq.lottery.bean;

/**
 * Created by Administrator on 2017/5/27.
 */

public class BottomPourBean {

    /**
     * code : 1
     * errormsg : 此单据不是下注状态！
     */

    private int code;
    private String errormsg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }
}
