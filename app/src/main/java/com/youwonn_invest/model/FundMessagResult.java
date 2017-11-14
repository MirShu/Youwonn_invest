package com.youwonn_invest.model;

import com.alibaba.fastjson.JSON;

/**
 * Created by ShuLin on 2017/11/9.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class FundMessagResult {
    private String error_code;
    private String reason;
    private String result;
    private String comments;

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }


    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }


    /**
     * @param result
     * @return
     */
    public static FundMessagResult parse(String result) {
        FundMessagResult message = new FundMessagResult();
        try {
            message = JSON.parseObject(result, FundMessagResult.class);
            return message;
        } catch (Exception e) {
            String str = e.getMessage();
        }
        return message;
    }


    /**
     * @param result
     * @return
     */
    public static FundMessagResult parseComments(String result) {
        FundMessagResult message = new FundMessagResult();
        try {
            message = JSON.parseObject(result, FundMessagResult.class);
            return message;
        } catch (Exception e) {
            String str = e.getMessage();
        }
        return message;
    }
}
