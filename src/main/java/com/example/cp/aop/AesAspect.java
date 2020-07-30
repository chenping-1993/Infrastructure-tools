package com.example.cp.aop;

import com.alibaba.fastjson.JSON;
import com.example.cp.common.annotation.encryption;
import com.example.cp.common.config.YmlPropers;
import com.example.cp.common.tool.AESUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Description: 自定义切面http响应加密
 * 若是含有注解@encryption(responseAes = true) 对返回的结果进行加密
 * @Author: chenping
 * @Date: 2020-05-24
 */
@Slf4j
@Aspect
@Component
public class AesAspect {

    @Value("${spring.profiles.active}")
    private String profileActive;

    @Autowired(required = false)
    YmlPropers propers;

    public AesAspect() {
    }

    //作用域
//    controller包及子包下的所有public方法
    @Pointcut("execution(public * com.example.cp.controller..*.*(..))")
    public void restControllerMethodPointcut() {
    }

    @SuppressWarnings("rawtypes")
    @Around("restControllerMethodPointcut()")
    public Object Interceptor(ProceedingJoinPoint pjPoint) throws Throwable {
        encryption interfaceFace = null;

        Object response = pjPoint.proceed();
        if ("test".equalsIgnoreCase(profileActive)) {//测试环境不进行加密
            return response;
        }
        MethodSignature msig = (MethodSignature) pjPoint.getSignature();
        Method pointMethod = pjPoint.getTarget().getClass().getMethod(msig.getName(), msig.getParameterTypes());
        interfaceFace = pointMethod.getAnnotation(encryption.class);//切点方法上获取注解

        if (interfaceFace == null) {
            return response;
        }
        if (interfaceFace.responseAes()) {
            response = encrypt(response);
//            if (!(response instanceof ResponseVO)) {
//                return "返回类型异常";
//            } else {
//                response = encrypt(response);
////                String data = outputParamter(((ResponseVO) response).getData());
////                ((ResponseVO) response).setData(data);
//            }

        }

        return response;
    }

    private Object encrypt(Object response) {
        log.debug("http响应======>返回明文数据：{}" + JSON.toJSONString(response));
        //对返回数据进行AES加密
        response = AESUtils.encrypt(response.toString(), propers.aeskey);
        log.debug("http响应======>返回加密数据：{}" + response);
        return response;
    }

    private String outputParamter(Object object) {
        String resultStr = null;
        try {
            String jsonString = JSON.toJSONString(object);
            log.debug("http响应======>返回明文数据：{}" + jsonString);
            //对返回数据进行AES加密
            resultStr = AESUtils.encrypt(jsonString, propers.aeskey);
            log.debug("http响应======>返回加密数据：{}" + resultStr);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("http响应======>", e);
        }
        return resultStr;
    }

}
