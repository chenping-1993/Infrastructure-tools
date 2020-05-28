package com.example.cp.common.annotation;

import java.lang.annotation.*;

/**
 * @Description:
 * @Author: chenping
 * @Date: 2020-05-24
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface encryption {

    //返回结果是否加密
    boolean responseAes() default false;
}
