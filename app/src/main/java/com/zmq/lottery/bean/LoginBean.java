package com.zmq.lottery.bean;

/**
 * 登陆
 * Created by Administrator on 2017/5/25.
 */

public class LoginBean {

    /**
     * code : 0
     * message : 成功
     * id_user : 2
     * username : 15960773338
     */

    private int code;
    private String message;
    private int id_user;
    private String username;

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

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
