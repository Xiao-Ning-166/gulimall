package com.gulimall.common.core.vo;


import com.gulimall.common.core.constants.ResponseCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 统一响应对象
 *
 * @author Mark sunlightcs@gmail.com
 */
@Data
@ApiModel("统一响应对象")
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 响应是否成功
     */
    @ApiModelProperty(value = "响应是否成功")
    private boolean success = true;

    /**
     * 响应状态码
     */
    @ApiModelProperty(value = "响应状态码")
    private int code;

    /**
     * 响应消息
     */
    @ApiModelProperty(value = "响应消息")
    private String message = "";

    /**
     * 响应数据
     */
    @ApiModelProperty(value = "响应数据")
    private T data;

    /**
     * 响应时间戳
     */
    @ApiModelProperty(value = "响应时间戳")
    private long timestamp = System.currentTimeMillis();

    public R() {
    }

    public R(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 响应成功
     *
     * @param <T>
     * @return
     */
    public static <T> R<T> success() {
        R<T> result = new R<T>();
        result.setCode(ResponseCodeEnum.RESPONSE_OK.getCode());
        result.setMessage(ResponseCodeEnum.RESPONSE_OK.getMessage());
        return result;
    }

    /**
     * 响应成功
     *
     * @param message 响应信息
     * @param <T>
     * @return
     */
    public static <T> R<T> success(String message) {
        R<T> result = new R<T>();
        result.setCode(ResponseCodeEnum.RESPONSE_OK.getCode());
        result.setMessage(message);
        return result;
    }

    /**
     * 响应成功
     *
     * @param data 响应数据
     * @param <T>
     * @return
     */
    public static <T> R<T> ok(T data) {
        R<T> result = new R<T>();
        result.setCode(ResponseCodeEnum.RESPONSE_OK.getCode());
        result.setMessage(ResponseCodeEnum.RESPONSE_OK.getMessage());
        result.setData(data);
        return result;
    }

    /**
     * 响应成功
     *
     * @param message 响应信息
     * @param data    响应数据
     * @param <T>
     * @return
     */
    public static <T> R<T> ok(String message, T data) {
        R<T> result = new R<T>();
        result.setCode(ResponseCodeEnum.RESPONSE_OK.getCode());
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    /**
     * 响应成功
     *
     * @param code    响应状态码
     * @param message 响应信息
     * @param data    响应数据
     * @param <T>
     * @return
     */
    public static <T> R<T> ok(int code, String message, T data) {
        R<T> result = new R<T>();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    /**
     * 响应失败
     *
     * @param <T>
     * @return
     */
    public static <T> R<T> error() {
        R<T> result = new R<T>();
        result.setSuccess(false);
        result.setCode(ResponseCodeEnum.INTERNAL_SERVER_ERROR_500.getCode());
        result.setMessage(ResponseCodeEnum.INTERNAL_SERVER_ERROR_500.getMessage());
        return result;
    }

    /**
     * 响应失败
     *
     * @param message 响应失败信息
     * @param <T>
     * @return
     */
    public static <T> R<T> error(String message) {
        R<T> result = new R<T>();
        result.setSuccess(false);
        result.setCode(ResponseCodeEnum.INTERNAL_SERVER_ERROR_500.getCode());
        result.setMessage(message);
        return result;
    }

    /**
     * 响应失败
     *
     * @param data 响应失败数据
     * @param <T>
     * @return
     */
    public static <T> R<T> error(T data) {
        R<T> result = new R<T>();
        result.setSuccess(false);
        result.setCode(ResponseCodeEnum.INTERNAL_SERVER_ERROR_500.getCode());
        result.setMessage(ResponseCodeEnum.INTERNAL_SERVER_ERROR_500.getMessage());
        result.setData(data);
        return result;
    }

    /**
     * 响应失败
     *
     * @param message 响应信息
     * @param data    响应失败数据
     * @param <T>
     * @return
     */
    public static <T> R<T> error(String message, T data) {
        R<T> result = new R<T>();
        result.setSuccess(false);
        result.setCode(ResponseCodeEnum.INTERNAL_SERVER_ERROR_500.getCode());
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    /**
     * 响应失败
     *
     * @param code    响应状态码
     * @param message 响应失败信息
     * @param <T>
     * @return
     */
    public static <T> R<T> error(int code, String message) {
        R<T> result = new R<T>();
        result.setSuccess(false);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    /**
     * 响应失败
     *
     * @param responseCodeEnum 响应错误枚举对象
     * @param <T>
     * @return
     */
    public static <T> R<T> error(ResponseCodeEnum responseCodeEnum) {
        R<T> result = new R<T>();
        result.setSuccess(false);
        result.setCode(responseCodeEnum.getCode());
        result.setMessage(responseCodeEnum.getMessage());
        return result;
    }

    /**
     * 响应失败
     *
     * @param responseCodeEnum 响应错误枚举对象
     * @param data             响应失败数据
     * @param <T>
     * @return
     */
    public static <T> R<T> error(ResponseCodeEnum responseCodeEnum, T data) {
        R<T> result = new R<T>();
        result.setSuccess(false);
        result.setCode(responseCodeEnum.getCode());
        result.setMessage(responseCodeEnum.getMessage());
        result.setData(data);
        return result;
    }

    /**
     * 响应失败
     *
     * @param code    响应状态码
     * @param message 响应失败信息
     * @param data    响应失败数据
     * @param <T>
     * @return
     */
    public static <T> R<T> error(int code, String message, T data) {
        R<T> result = new R<T>();
        result.setSuccess(false);
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }
}
