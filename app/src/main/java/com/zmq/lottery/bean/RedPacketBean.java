package com.zmq.lottery.bean;

/**
 * Created by Administrator on 2017/6/2.
 */

public class RedPacketBean {
    private int headId;
    private String name;
    private long time;
    private double cashNum;
    private boolean bestOfLuck;

    public RedPacketBean(int headId, String name, long time, double cashNum, boolean bestOfLuck) {
        this.headId = headId;
        this.name = name;
        this.time = time;
        this.cashNum = cashNum;
        this.bestOfLuck = bestOfLuck;
    }

    public int getHeadId() {
        return headId;
    }

    public String getName() {
        return name;
    }

    public long getTime() {
        return time;
    }

    public double getCashNum() {
        return cashNum;
    }

    public boolean getBestOfLuck() {
        return bestOfLuck;
    }
}
