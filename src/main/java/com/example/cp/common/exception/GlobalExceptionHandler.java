package com.example.cp.common.exception;

import com.example.cp.entity.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Description: 全局异常处理类；rest请求入参校验异常、业务异常 响应
 * 处理抛到controller层的异常，若是没有抛到controller层，则不会将service层的异常展示出来
 * @Author: chenping
 * @Date: 2020-11-11
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * @Description:  方法中的参数格式正确性异常
     * @param: e
     * @return: com.example.cp.entity.BaseResponse
     * @Author: chenping
     * @Date: 2020/12/16 18:15
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        ObjectError error = e.getBindingResult().getAllErrors().get(0);
        return BaseResponse.fail(error.getDefaultMessage());
    }


    /**
     * @Description:  自定义异常
     * @param: e
     * @return: com.example.cp.entity.BaseResponse
     * @Author: chenping
     * @Date: 2020/12/16 18:16
     */
    @ExceptionHandler(GameException.class)
    public BaseResponse testExceptionHandler(GameException e) {
        return BaseResponse.fail(e.getMessage());
    }

    /** 
     * @Description:  打印exception日志到控制台和error日志文件
     * @param: e 
     * @return: com.example.cp.entity.BaseResponse 
     * @Author: chenping
     * @Date: 2020/12/16 18:04
     */
    @ExceptionHandler(Exception.class)
    public BaseResponse ExceptionHandler(Exception e) {
        log.error("异常错误信息：",e);
        BaseResponse baseResponse = BaseResponse.fail();
        StringBuilder sb = new StringBuilder();
        sb.append(e.toString()).append(" : ").append(e.getMessage());
        sb.append(System.getProperty("line.separator"));
        StackTraceElement[] stackTraceElements = e.getStackTrace();
        if (null != stackTraceElements){
            int i = 1;
            for (StackTraceElement se: stackTraceElements) {
                if (i < 20) {
                    sb.append(se.toString());
                    sb.append(System.getProperty("line.separator"));
                        i++;
                }
            }
        }
        log.error("e.getMessage():[{}]",sb.toString());
        baseResponse.setTrace(sb.toString());
        return baseResponse;
    }
}
