package com.gulimall.common.core.exception;

import com.gulimall.common.core.constant.ResponseCodeEnum;
import org.springframework.http.HttpStatus;

/**
 * 手机号不唯一异常
 *
 * @author xiaoning
 * @date 2023/03/15
 */
public class PhoneNotUniqueException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * http状态码
     */
    private Integer httpStatusCode;

    private Object object;

    public PhoneNotUniqueException() {
        this(ResponseCodeEnum.PHONE_NOT_UNIQUE_10402);
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    public PhoneNotUniqueException(ResponseCodeEnum responseCodeEnum) {
        super(responseCodeEnum.getMessage());
        this.httpStatusCode = responseCodeEnum.getCode();
    }

    public PhoneNotUniqueException(Integer httpStatusCode, Object object) {
        this.httpStatusCode = httpStatusCode;
        this.object = object;
    }

    public PhoneNotUniqueException(String message) {
        super(message);
        this.httpStatusCode = HttpStatus.BAD_REQUEST.value();
    }

    public PhoneNotUniqueException(String message, Object object) {
        super(message);
        this.httpStatusCode = HttpStatus.BAD_REQUEST.value();;
        this.object = object;
    }
}
