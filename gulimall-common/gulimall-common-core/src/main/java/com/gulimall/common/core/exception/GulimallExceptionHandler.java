package com.gulimall.common.core.exception;

import com.gulimall.common.core.constant.ResponseCodeEnum;
import com.gulimall.common.core.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理
 *
 * @author xiaoning
 * @date 2022/11/26
 */
@Slf4j
@RestControllerAdvice
public class GulimallExceptionHandler {

    /**
     * 数据校验异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handleValidationException(MethodArgumentNotValidException e) {
        log.error("数据校验出现异常，{}", e.getMessage(), e);
        Map<String, String> data = new HashMap<>();
        BindingResult bindingResult = e.getBindingResult();
        bindingResult.getFieldErrors().forEach((fieldError) -> {
            data.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return R.error(ResponseCodeEnum.USER_ERROR_10400.getCode(), ResponseCodeEnum.USER_ERROR_10400.getMessage(), data);
    }


    /**
     * 其他异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = GulimallException.class)
    public R handleGulimallException(GulimallException e) {
        log.error("发生异常，原因：{}", e.getMessage(), e);
        return R.error(e.getHttpStatusCode(), e.getMessage());
    }


    /**
     * 其他异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public R handleException(Exception e) {
        log.error("发生异常，原因：{}", e.getMessage(), e);
        return R.error(ResponseCodeEnum.INTERNAL_SERVER_ERROR_500);
    }

}
