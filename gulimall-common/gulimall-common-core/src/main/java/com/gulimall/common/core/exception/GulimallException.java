package com.gulimall.common.core.exception;

import com.gulimall.common.core.constants.ResponseCodeEnum;
import org.springframework.http.HttpStatus;

/**
 * 自定义全局异常
 *
 * @author xiaoning
 * @date 2023/03/15
 */
public class GulimallException extends RuntimeException {

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

    public GulimallException(ResponseCodeEnum responseCodeEnum) {
        super(responseCodeEnum.getMessage());
        this.httpStatusCode = responseCodeEnum.getCode();
    }

    public GulimallException(Integer httpStatusCode, Object object) {
        this.httpStatusCode = httpStatusCode;
        this.object = object;
    }

    public GulimallException(String message) {
        super(message);
        this.httpStatusCode = HttpStatus.BAD_REQUEST.value();
    }

    public GulimallException(String message, Object object) {
        super(message);
        this.httpStatusCode = HttpStatus.BAD_REQUEST.value();;
        this.object = object;
    }
}
