package com.zmq.lottery.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/24.
 */

public class RoomBean implements Serializable{

    /**
     * id : 1
     * cname : 普通房
     */

    private int id;
    private String cname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }
}
