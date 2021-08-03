package com.example.cp.aop;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description: 接口、业务 日志记录
 * @Author: chenping
 * @Date: 2021-08-02
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    @Around("execution(public * com.example.cp.controller..*.*(..)) || execution(public * com.example.cp.service..*.*(..))")
    public Object Interceptor(ProceedingJoinPoint point) throws Throwable {
        return printLog(point);
    }

    private Object printLog(ProceedingJoinPoint point) throws Throwable {
        String classPath = point.getTarget().getClass().getName();//类路径
        String methodName = point.getSignature().getName();//方法名称
        Object[] args = point.getArgs();//方法参数
        StringBuilder sb = new StringBuilder();
        for (Object arg:  args) {
            if (excludeParam(arg)) {//排除非常规入参实体 如 MultipartFile、HttpServletRequest
                continue;
            }
            String str = JSON.toJSONString(arg);
            String paramType = arg.getClass().getSimpleName();//参数的类型
            sb.append(paramType).append(":").append(str).append(";");
        }
        log.info("{} 方法开始执行，入参为--》 {}",classPath+"."+methodName,sb.toString());
        Object result = point.proceed();
        log.info("{} 方法执行结束，返回结果--》 {}",classPath+"."+methodName,JSON.toJSON(result));
        return result;
    }

    private boolean excludeParam(Object arg) {
        return arg instanceof MultipartFile || arg instanceof HttpServlet || arg instanceof HttpServletRequest || arg instanceof HttpServletResponse;
    }
}
