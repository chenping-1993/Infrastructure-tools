package com.example.cp.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.cp.common.config.YmlPropers;
import com.example.cp.common.tool.AESUtil;
import com.example.cp.common.tool.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @Author: chenping
 * @Date: 2020-05-21
 */
public abstract class BaseController {
    @Autowired(required = false)
    protected HttpServletRequest request;

    @Autowired(required = false)
    YmlPropers config;

    public static final String FIELD_DATA = "data";

    /**
     * 出错消息状态码
     */
    public static final String FIELD_CODE = "code";

    public static final String FIELD_MESSAGE = "message";

    protected JSONObject result = new JSONObject();

    public void resSuccessResult(Object data) {
        result.put(FIELD_DATA, data);
        result.put(FIELD_CODE, 0);
    }

    public void resSuccessResult() {
        result.put(FIELD_DATA, "");
        result.put(FIELD_CODE, 0);
    }

    public void resFailResult(Object message) {
        result.put(FIELD_DATA, null);
        result.put(FIELD_MESSAGE, message);
        result.put(FIELD_CODE, 0);
    }

    /** 
     * @Description:  解密token信息
     * @param: request 
     * @return: com.alibaba.fastjson.JSONObject 
     * @Author: chenping
     * @Date: 2020/5/24
     */
    public JSONObject decryptToken(HttpServletRequest request) throws Exception {
        String jsonString = RequestUtil.getRequest(request);
        JSONObject jsonObject = JSON.parseObject(jsonString);
        String userStr = jsonObject.getString("Token");
        String desToken = AESUtil.desEncrypt(userStr, config.tokenKey,config.tokenIv);
        JSONObject user = JSON.parseObject(desToken);
        return user;
    }
}
