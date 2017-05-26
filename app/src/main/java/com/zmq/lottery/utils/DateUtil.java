package com.zmq.lottery.utils;

/**
 * Created by Administrator on 2017/5/17 0017.
 */

import android.annotation.SuppressLint;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 类描述：<br>
 * 日期时间帮助类
 *
 * @author lsd
 * @date 2015-11-30
 */
@SuppressLint("SimpleDateFormat")
public class DateUtil {

    /**
     * 一分钟的秒数
     */
    public static final int ONE_MINUTE = 60;
    /**
     * 一小时的秒数
     */
    public static final int ONE_HOUR = 3600;
    /**
     * 一整天的秒数
     */
    public static final int ONE_DAY = 86400;
    /**
     * 一个月的秒数（30天）
     */
    public static final int ONE_MONTH = 2592000;
    /**
     * 一整年的秒数（365天）
     */
    public static final int ONE_YEAR = 31536000;

    public static final String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四",
            "星期五", "星期六"};

    public static final String[] dayParts = {"凌晨", "早上", "上午", "中午", "下午", "晚上"};

    /**
     * 当前时间的日历
     */
    public static Calendar calendar = Calendar.getInstance();

    /**
     * 日期格式yyyy-MM字符串常量
     */
    public static final String MONTH_FORMAT = "yyyy-MM";

    /**
     * 日期格式yyyy-MM-dd字符串常量
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 日期格式yyyy-MM-dd HH:mm字符串常量
     */
    public static final String DATE_HOUR_FORMAT = "yyyy-MM-dd HH:mm";

    /**
     * 日期格式HH:mm:ss字符串常量
     */
    public static final String HOUR_FORMAT = "HH:mm:ss";

    /**
     * 日期格式yyyy-MM-dd HH:mm:ss字符串常量
     */
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 解析json数据
     *
     * @param json
     * @param class1
     * @return 返回JSON数据对象bean
     */
    public static <T> Object processJson(String json, Class<T> class1) {
        // 解析json
        Gson gson = new Gson();
        T bean = gson.fromJson(json, class1);

        return bean;
    }

    /**
     * 按照指定的文本格式返回当前时间
     *
     * @param format 默认格式：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getDateStr(String format) {
        if (format == null || "".equals(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        return new SimpleDateFormat(format).format(new Date());
    }

    /**
     * 转换时间戳为月日<br>
     * 将服务器获取的10位时间戳String时间转换成long类型*1000+"" 在转换成日期格式的string
     *
     * @param str
     * @return
     */
    public static String toTime1(String str) {
        long time = Long.parseLong(str) * 1000;
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日");
        Date d = new Date(time);
        String t = format.format(d);
        return t;
    }

    /**
     * 转换时间戳为时分
     */
    public static String toTime2(String str) {
        long time = Long.parseLong(str) * 1000;
        SimpleDateFormat format1 = new SimpleDateFormat("kk:mm");
        Date d = new Date(time);
        String t1 = format1.format(d);
        return t1;
    }

    /**
     * 转换时间戳为年月日 时分
     */
    public static String toTime3(String str) {
        long time = Long.parseLong(str) * 1000;
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 kk:mm");
        Date d = new Date(time);
        String t = format.format(d);
        return t;
    }

    /**
     * 转换时间戳为年月日
     * yyyy年MM月dd日
     */
    public static String toTime4(String str) {
        long time = Long.parseLong(str);
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        Date d = new Date(time);
        String t = format.format(d);
        return t;
    }

    /**
     * 转换时间戳为2016.3.25<br>
     * 将服务器获取的10位时间戳String时间转换成long类型*1000+"" 在转换成日期格式的string
     *
     * @param str
     * @return
     */
    public static String toTime5(String str) {
        long time = Long.parseLong(str) * 1000;
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        Date d = new Date(time);
        String t = format.format(d);
        return t;
    }

    /**
     * 转换时间戳为2016-3-25<br>
     *
     * @return
     */
    public static String toTime6(Long time) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        Date d = new Date(time);
        String t = format.format(d);
        return t;
    }

    /**
     * 转换时间戳为2016-3-25 00:00<br>
     *
     * @return
     */
    public static String toTime7(Long time) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_HOUR_FORMAT);
        Date d = new Date(time);
        String t = format.format(d);
        return t;
    }

    /**
     * 转换时间戳为2016-3-25 00:00:00<br>
     *
     * @return
     */
    public static String toTime8(Long time) {
        SimpleDateFormat format = new SimpleDateFormat(DATETIME_FORMAT);
        Date d = new Date(time);
        String t = format.format(d);
        return t;
    }

    /**
     * 转换时间戳为00:00:00<br>
     *
     * @return
     */
    public static String toTime9(Long time) {
        SimpleDateFormat format = new SimpleDateFormat(HOUR_FORMAT);
        Date d = new Date(time);
        String t = format.format(d);
        return t;
    }

    /**
     * 获取时间差的字符串形式
     * 如：1时1分1秒
     *
     * @param time 秒
     * @return
     */
    public static String getCutDown(long time) {
        int hours = ((int) time) % (3600 * 24) / 3600;
        int minutes = ((int) time) % (3600 * 24) % 3600 / 60;
        int seconds = ((int) time) % (3600 * 24) % 3600 % 60 % 60;

        String str = "";
        //1天之内  只显示小时  1小时之内的 只显示分钟  1分钟之内 的只显示秒
        if (hours != 0) {
            str = hours + ":" + minutes + ":" + seconds;
        } else if (minutes != 0) {
            str = "0:" + minutes + ":" + seconds;
        } else {
            str = "0:" + "0:" + seconds;
        }
        return str;
    }

    /**
     * 获取时间差的字符串形式
     * 如：1秒
     *
     * @param time 秒
     * @return
     */
    public static String getTimeDown(long time) {
        int seconds = ((int) time) % (3600 * 24) % 3600 % 60 % 60;

        String str = "";
        //1分钟之内 的只显示秒
        if (seconds != 0) {
            str = "" + seconds;
        }
        return str;
    }
}
