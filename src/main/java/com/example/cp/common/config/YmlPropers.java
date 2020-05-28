package com.example.cp.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @Author: chenping
 * @Date: 2020-05-21
 */
@Configuration
public class YmlPropers {

    /**
     * 匹配，redis队列key
     */
    @Value("${cxyh.match}")
    public String matchKey;

    /**
     * 邀请队列key
     */
    @Value("${cxyh.invite}")
    public String inviteKey;

    /**
     * 拼接请求体的常量字符串
     */
    @Value("${matchSign}")
    public String matchSign;

    /**
     * 系统环境
     */
    @Value("${spring.profiles.active}")
    public String environment;

    /**
     * http响应结果加密的key
     */
    @Value("${aeskey}")
    public String aeskey;

    /**
     * 解密token的key
     */
    @Value("${token.key}")
    public String tokenKey;

    /**
     * 解密token的key
     */
    @Value("${token.iv}")
    public String tokenIv;

    /**
     * redis数据库号码
     */
    @Value("${spring.redis.database}")
    public Integer redisDatabaseNum;

    /**
     * 不需要鉴权的列表
     */
    @Value("${cxyh.notAuth}")
    public String notAuthList;
}
