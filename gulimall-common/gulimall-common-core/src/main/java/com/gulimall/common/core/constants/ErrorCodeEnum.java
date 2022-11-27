package com.gulimall.common.core.constants;

/**
 * 错误码
 *
 * @author xiaoning
 * @date 2022/11/27
 */
public enum ErrorCodeEnum {

    /**
     * 用户参数校验失败
     */
    USER_ERROR_10400(10400, "参数格式校验失败"),

    /**
     * 系统内部异常
     */
    SERVER_ERROR_20500(20500, "系统内部异常");

    /**
     * 错误码
     */
    private final Integer code;

    /**
     * 错误信息
     */
    private final String message;

    private ErrorCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
