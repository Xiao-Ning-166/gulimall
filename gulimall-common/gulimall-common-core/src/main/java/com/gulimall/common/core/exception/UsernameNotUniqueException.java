package com.gulimall.common.core.exception;

import com.gulimall.common.core.constant.ResponseCodeEnum;
import org.springframework.http.HttpStatus;

/**
 * 用户名不唯一异常
 *
 * @author xiaoning
 * @date 2023/03/15
 */
public class UsernameNotUniqueException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * http状态码
     */
    private Integer httpStatusCode;

    private Object object;

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    public UsernameNotUniqueException() {
        this(ResponseCodeEnum.USERNAME_NOT_UNIQUE_10401);
    }

    public UsernameNotUniqueException(ResponseCodeEnum responseCodeEnum) {
        super(responseCodeEnum.getMessage());
        this.httpStatusCode = responseCodeEnum.getCode();
    }

    public UsernameNotUniqueException(Integer httpStatusCode, Object object) {
        this.httpStatusCode = httpStatusCode;
        this.object = object;
    }

    public UsernameNotUniqueException(String message) {
        super(message);
        this.httpStatusCode = HttpStatus.BAD_REQUEST.value();
    }

    public UsernameNotUniqueException(String message, Object object) {
        super(message);
        this.httpStatusCode = HttpStatus.BAD_REQUEST.value();;
        this.object = object;
    }
}
