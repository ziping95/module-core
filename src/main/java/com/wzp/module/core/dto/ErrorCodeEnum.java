package com.wzp.module.core.dto;

/**
 * Created by lx on 2017/8/24.
 * 错误码规范：A-BB-CCC；如：101001
 * A:错误级别，如1代表系统级错误，2代表服务级错误；
 * B:项目或模块名称，一般公司不会超过99个项目；
 * C:具体错误编号，一个系统模块错误码一般不会大于999，三位应该够使了
 */
public enum ErrorCodeEnum {

    /**
     * ###################################   系统级错误码：1xxxxx   ###########################################
     */

    SYS_COMMON_CUSTOM_MSG(-1, "《《《《《注意：通用错误码，用于系统内部自定义异常消息》》》》》》"),
    SYS_SUCCESS(0, "请求成功"),
    SYS_SERVER_ERROR(1, "系统错误"),
    SYS_SERVER_BUSY(2, "系统繁忙"),

    PARAM_NOT_NULL_ERROR(3, "参数不能为空！"),
    PAGE_PARAM_NOT_NULL_ERROR(4, "分页查询参数不能为空！"),
    //用于用户传入参数异常
    SYS_PARAM_ERROR(5, "参数错误"),

    // 认证（102xxx）：第三方客户端接入认证
    SYS_APP_KEY_ERROR(101001, "app_id无效"),
    SYS_APP_SECRET_ERROR(101002, "app_secret无效"),
    SYS_USER_UN_LOGIN(101003, "平台未登录认证！"),

    SYS_TOKEN_UESED(101004, "token已被使用"),
    SYS_TOKEN_EXPIRED(101005, "token已过期"),
    SYS_TOKEN_REVOKED(101006, "token已被回收"),
    SYS_TOKEN_REJECTED(101007, "token不合法"),


    // 权限（103xxx）：第三方客户端认证_访问资源权限
    SYS_CLIENT__AUTH_ACCESS_RESOURCE_PERMISSION(102001, "需要appId认证授权资源访问"),
    SYS_PASSWORD_AUTH_ACCESS_RESOURCE_PERMISSION(102002, "需要平台登录认证授权资源访问"),


    /**
     * ###################################   服务级错误码：2xxxxx   ###########################################
     */

    // 课程信息（201xxx）

    // 组件配置获取（202xxx）

    // 学习记录（203xxx）

    // 课程节点（204xxx）

    // 组件：课件（205xxx）

    // 组件：通知（206xxx）
    NOTICE_ADD_SAVE_ERROR(202001, "添加保存笔记失败！"),
    NOTICE_UPDATE_SAVE_ERROR(202001, "添加保存笔记失败！"),

    // 组件：作业（207xxx）
    HOMEWORK_SAVE_ERROR(204002, "success"),

    // 组件：自测（208xxx）
    TEST_QUERY_FIRST_PAGE_ERROR(205001, "分页查询课程下的第一页测试列表失败！"),
    TEST_QUERY_PAGE_ERROR(205002, "滚动分页加载测试列表失败！"),
    TEST_QUERY_DETAIL_ERROR(205003, "查看自测详情失败！"),
    TEST_QUERY_STUDENT_SCORE_LIST_ERROR(205004, "查看单个自测的学生成绩列表失败！"),
    TEST_QUERY_CUSTOM_PAPER_ERROR(205004, "查看指定题测试试卷失败！"),

    // 讨论（209xxx）

    // 组件：笔记（210xxx）
    NOTE_ADD_SAVE_ERROR(201001, "添加保存笔记失败！"),
    NOTE_UPDATE_SAVE_ERROR(201002, "修改保存笔记失败！"),
    NOTE_QUERY_DETAIL_ERROR(201003, "查询笔记详情失败！"),
    NOTE_QUERY_PAGE_ERROR(201004, "查询笔记详情失败！"),
    NOTE_DELETE_ERROR(201005, "删除笔记失败！"),
    NOTE_PAGE_QUERY_ERROR(201006, "分页查询笔记列表失败！"),

    // 组件：答疑（211xxx）

    // 组件：评价（212xxx）

    // 组件：考试（213xxx）
    ;

    private int code;
    private String message;

    ErrorCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 根据错误码返回错误消息
     * @param errCode
     * @return
     */
    public ErrorCodeEnum getErrorCodeEnum(int errCode) {
        ErrorCodeEnum codeEnum = null;
        for (ErrorCodeEnum errorCodeEnum : ErrorCodeEnum.values()) {
            if(errorCodeEnum.getCode() == errCode) {
                codeEnum = errorCodeEnum;
                break;
            }
        }
        return codeEnum;
    }

    public static String getMessage(int errCode) {
        return "";
    }

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
}
