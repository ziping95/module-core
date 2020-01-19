package com.wzp.module.core.dto;


public class Meta {

    public static final String OK = "ok";
    public static final String ERROR = "error";

    private int errCode;
    private String errMsg;

    public Meta(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
