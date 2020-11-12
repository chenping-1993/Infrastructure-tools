package com.example.cp.common.exception;

import com.example.cp.entity.BaseResponse;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Description: 全局异常处理类；rest请求入参校验异常 、业务异常 响应
 * @Author: chenping
 * @Date: 2020-11-11
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        ObjectError error = e.getBindingResult().getAllErrors().get(0);
        return BaseResponse.fail(error.getDefaultMessage());
    }


    @ExceptionHandler(GameException.class)
    public BaseResponse testExceptionHandler(GameException e) {
        return BaseResponse.fail(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public BaseResponse ExceptionHandler(Exception e) {
        return BaseResponse.fail(e.toString());
    }
}
