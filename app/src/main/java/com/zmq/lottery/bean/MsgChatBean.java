package com.zmq.lottery.bean;

/**
 * Created by Administrator on 2017/5/27.
 */

public class MsgChatBean {
    private String status;
    private int listid;
    private String msg;
    private long balance;

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

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }
}
