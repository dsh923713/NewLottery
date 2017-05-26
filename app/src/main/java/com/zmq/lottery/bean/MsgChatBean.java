package com.zmq.lottery.bean;

/**
 * Created by Administrator on 2017/5/27.
 */

public class MsgChatBean {
    private String status;
    private int listid;
    private String msg;
    private String balance;

    public MsgChatBean(String status, int listid, String msg, String balance) {
        this.status = status;
        this.listid = listid;
        this.msg = msg;
        this.balance = balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getListid() {
        return listid;
    }

    public void setListid(int listid) {
        this.listid = listid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
