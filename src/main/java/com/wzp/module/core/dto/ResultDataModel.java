package com.wzp.module.core.dto;

public class ResultDataModel {

    private Meta meta;
    private Object data;

    /**
     * 组装成功数据模型
     * @return
     */
    public static ResultDataModel handleSuccessResult() {
        return handleResult(ErrorCodeEnum.SYS_SUCCESS.getCode(), Meta.OK, null);
    }

    /**
     * 组装成功数据模型
     * @param data 任意Object类型数据
     * @return
     */
    public static ResultDataModel handleSuccessResult(Object data) {
        return handleResult(ErrorCodeEnum.SYS_SUCCESS.getCode(), Meta.OK, data);
    }

    /**
     * 组装成功数据模型
     * 增加这个方法是为了方便data是String类型时直接调用
     * @param data 任意Object类型数据
     * @return
     */
    public static ResultDataModel handleSuccessResultAndSetData(Object data) {
        return handleResult(ErrorCodeEnum.SYS_SUCCESS.getCode(), Meta.OK, data);
    }

    /**
     * 组装成功数据模型
     * @param message
     * @return
     */
    public static ResultDataModel handleSuccessResult(String message) {
        return handleResult(ErrorCodeEnum.SYS_SUCCESS.getCode(), message, null);
    }

    /**
     * 组装失败数据模型
     * @return
     */
    public static ResultDataModel handleFailureResult() {
        return handleResult(ErrorCodeEnum.SYS_SERVER_ERROR.getCode(), Meta.ERROR, null);
    }

    /**
     * 组装失败数据模型
     * @param errCode
     * @return
     */
    public static ResultDataModel handleFailureResult(int errCode) {
        return handleResult(errCode, Meta.ERROR, null);
    }

    public static ResultDataModel handleFailureResult(String message) {
        return handleResult(ErrorCodeEnum.SYS_SERVER_ERROR.getCode(), message, null);
    }

    /**
     * 组装失败数据模型
     * @param errCode
     * @param errMsg
     * @return
     */
    public static ResultDataModel handleFailureResult(int errCode, String errMsg) {
        return handleResult(errCode, errMsg, null);
    }

    /**
     * 组装失败数据模型
     * @param errCode
     * @param data
     * @return
     */
    public static ResultDataModel handleFailureResult(int errCode, Object data) {
        return handleResult(errCode, Meta.ERROR, data);
    }

    /**
     * 组装结果数据模型
     * @param errCode
     * @param errMsg
     * @param data
     * @return
     */
    public static ResultDataModel handleResult(int errCode, String errMsg, Object data) {
        Meta meta = new Meta(errCode, errMsg);
        ResultDataModel dataModel = new ResultDataModel();
        dataModel.setMeta(meta);
        dataModel.setData(data);
        return dataModel;
    }

    /**
     * 根据传入的boolean变量组装结果
     * @param success
     * @return
     */
    public static ResultDataModel handleResult(boolean success) {
        return success ? handleSuccessResult() : handleFailureResult();
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
