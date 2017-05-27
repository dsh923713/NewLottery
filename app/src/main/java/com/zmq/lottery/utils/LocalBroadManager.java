package com.zmq.lottery.utils;

import android.support.v4.content.LocalBroadcastManager;

import com.zmq.lottery.MyApplication;

/**
 * Created by Administrator on 2017/5/27.
 */

public class LocalBroadManager {
    private static LocalBroadcastManager broadcastManager = null;

    public static LocalBroadcastManager getInstance(){
        if (broadcastManager == null){
            broadcastManager = LocalBroadcastManager.getInstance(MyApplication.getInstance());
        }
        return broadcastManager;
    }
}
