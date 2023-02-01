package com.gulimall.common.core.constants;

/**
 * 响应码
 *
 * @author xiaoning
 * @date 2022/11/27
 */
public enum ResponseCodeEnum {

    /**
     * 响应成功
     */
    RESPONSE_OK(200, "操作成功"),

    /**
     * 系统内部异常
     */
    INTERNAL_SERVER_ERROR_500(500, "系统内部异常"),

    /**
     * 用户参数校验失败
     */
    USER_ERROR_10400(10400, "参数格式校验失败");

    /**
     * 错误码
     */
    private final Integer code;

    /**
     * 错误信息
     */
    private final String message;

    private ResponseCodeEnum(Integer code, String message) {
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
