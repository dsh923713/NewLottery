package com.zmq.lottery.utils;

import android.util.Log;

/**
 * Created by Administrator on 2017/5/15 0015.
 */

public class LogUtils {
    private static final String TAG = "DSH -> LogUtils";
    public static final int VERBOSE = 1;
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int WARN = 4;
    public static final int ERROR = 5;
    public static final int NOTHING = 6;
    public static int level = VERBOSE;

    /**
     * 默认
     * @param msg
     */
    public static void v( String msg) {
        if (level <= VERBOSE) {
            Log.v(TAG, msg);
        }
    }
    public static void d(String msg) {
        if (level <= DEBUG) {
            Log.d(TAG, msg);
        }
    }
    public static void i(String msg) {
        if (level <= INFO) {
            Log.i(TAG, msg);
        }
    }
    public static void w(String msg) {
        if (level <= WARN) {
            Log.w(TAG, msg);
        }
    }
    public static void e(String msg) {
        if (level <= ERROR) {
            Log.e(TAG, msg);
        }
    }

    /**
     * 自定义tag
     * @param tag
     * @param msg
     */
    public static void v(String tag, String msg) {
        if (level <= VERBOSE) {
            Log.v(tag, msg);
        }
    }
    public static void d(String tag, String msg) {
        if (level <= DEBUG) {
            Log.d(tag, msg);
        }
    }
    public static void i(String tag, String msg) {
        if (level <= INFO) {
            Log.i(TAG, msg);
        }
    }
    public static void w(String tag, String msg) {
        if (level <= WARN) {
            Log.w(tag, msg);
        }
    }
    public static void e(String tag, String msg) {
        if (level <= ERROR) {
            Log.e(tag, msg);
        }
    }
}
