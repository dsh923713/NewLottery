package com.zmq.lottery.bean;

/**
 * 登陆
 * Created by Administrator on 2017/5/25.
 */

public class LoginBean {

    /**
     * code : 2
     * message : 密码错误！
     */

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
