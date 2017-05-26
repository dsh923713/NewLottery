package com.zmq.lottery.bean;

/**
 * Created by Administrator on 2017/5/17.
 */

public class MsgBean {
    public static final int TYPE_RECEIVED = 0;
    public static final int TYPE_SENT = 1;
    private boolean isMoney;
    private String content;
    private int type;
    private int icon;
    private boolean isLeadUp;

    public MsgBean(boolean isMoney, boolean isLeadUp, String content, int type, int icon) {
        this.isMoney = isMoney;
        this.content = content;
        this.type = type;
        this.icon = icon;
        this.isLeadUp = isLeadUp;
    }

    public boolean isLeadUp() {
        return isLeadUp;
    }

    public boolean isMoney() {
        return isMoney;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }

    public int getIcon() {
        return icon;
    }
}
