package com.example.cp.common.exception;

import com.example.cp.entity.BaseResponse;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Description: rest请求入参校验 全局异常处理类
 * @Author: chenping
 * @Date: 2020-11-11
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        BaseResponse response = new BaseResponse();
        ObjectError error = e.getBindingResult().getAllErrors().get(0);
        response.setMessage(error.getDefaultMessage());
        return response;
    }


    @ExceptionHandler(GameException.class)
    public BaseResponse testExceptionHandler(GameException e) {
        BaseResponse response = new BaseResponse();
        response.setMessage(e.getMessage());
        return response;
    }
}
