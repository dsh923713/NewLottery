package com.zmq.lottery.bean;

/**
 * Created by Administrator on 2017/5/15.
 */

public class HomeBean {
    private int imageId;
    private String title;

    public HomeBean(int imageId, String title){
        this.imageId = imageId;
        this.title = title;
    }
    public int getImageId() {
        return imageId;
    }

    public String getTitle() {
        return title;
    }

}
