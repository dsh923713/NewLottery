package com.zmq.lottery.base;

/**
 * Created by Administrator on 2017/5/24.
 */

public interface RequestResult {
    public void onSuccess(String result, String requestCode);
    public void onFailure(String result, String requestCode);
}
