package com.zmq.lottery.bean;

/**
 * 注册
 * Created by Administrator on 2017/5/25.
 */

public class RegisterBean {

    /**
     * status : fail
     * errormsg : 验证码错误！
     */

    private String status;
    private String errormsg;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }
}
