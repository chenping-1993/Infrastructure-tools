package com.example.cp.common.tool;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: chenping
 * @Date: 2020-05-22
 */
@Slf4j
public class RequestUtil {


    /**
     * 通过okhttp3 发送post请求
     *
     * @param obj 参数类型为对象，会使用JSON.toJSONString(obj);进行转换
     * @param url 请求地址
     * @return 返回结果
     */
    public static String send(Object obj, String url) {
        String json = JSON.toJSONString(obj);
        return send(json, url);
    }


    /**
     * @description:  处理 HttpServletRequest 公共方法
     * @param: [request]
     * @return: java.lang.String
     * @author: chenping
     * @date: 2019/11/8
     */
    public static String getRequest(HttpServletRequest request) {
        StringBuffer stringBuffer = new StringBuffer();
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        try {
            inputStream = request.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));

            String s = "";
            while ((s = bufferedReader.readLine()) != null) {
                stringBuffer.append(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (stringBuffer.length() == 0) {
            return null;
        } else {
            return stringBuffer.toString();
        }
    }

}
