package com.zmq.lottery.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/17.
 */

public class BusinessBean {
    /**
     * code : 0
     * message : 成功
     * result : [{"opertime":"2017-05-24 21:25:12","score":"-3.00","kind":""},
     * {"opertime":"2017-05-24 21:20:22","score":"-3.00","kind":""},{"opertime":"2017-05-24 21:03:28","score":"-3.00","kind":""}]
     */

    private int code;
    private String message;
    private List<ResultBean> result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * opertime : 2017-05-24 21:25:12
         * score : -3.00
         * kind :
         */

        private String opertime;
        private String score;
        private String kind;

        public String getOpertime() {
            return opertime;
        }

        public void setOpertime(String opertime) {
            this.opertime = opertime;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getKind() {
            return kind;
        }

        public void setKind(String kind) {
            this.kind = kind;
        }
    }


//    private String name;
//    private String dateTime;
//    private String style;
//    private String moneyNum;
//
//    public BusinessBean(String name, String dateTime, String style, String moneyNum) {
//        this.name = name;
//        this.dateTime = dateTime;
//        this.style = style;
//        this.moneyNum = moneyNum;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getDateTime() {
//        return dateTime;
//    }
//
//    public String getStyle() {
//        return style;
//    }
//
//    public String getMoneyNum() {
//        return moneyNum;
//    }
}
