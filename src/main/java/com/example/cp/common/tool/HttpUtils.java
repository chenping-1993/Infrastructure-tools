package com.example.cp.common.tool;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.io.IOException;

/**
 * @Description:
 * @Author: chenping
 * @Date: 2020-05-22
 */
@Slf4j
public class HttpUtils {


    public static String HttpPostWithJson(String url, JSONObject param) {
        Long startTime = System.currentTimeMillis();
        log.info("- ->>url:["+url+"] param:[ "+param.toJSONString());

        String returnValue =null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        try{
            httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(10*1000).setConnectTimeout(10*1000).setConnectionRequestTimeout(10*1000).build();
            httpPost.setConfig(requestConfig);
            StringEntity requestEntity = new StringEntity(param.toJSONString(),"utf-8");
            requestEntity.setContentEncoding("UTF-8");
            httpPost.setHeader("Content-type", "application/json");

            httpPost.setEntity(requestEntity);
            returnValue = httpClient.execute(httpPost,responseHandler);
            log.info("send message takes: {}",System.currentTimeMillis()-startTime);
        }catch(Exception e){

            log.error("调用对外接口异常:"+url,e.fillInStackTrace());
        }finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
                log.error(e.getMessage(),e.fillInStackTrace());
            }
        }
        System.out.println("- -<<url:["+url+"] result:[ "+returnValue);
        return returnValue;
    }

    public static String HttpGet(String url, JSONObject param) {
        Long startTime = System.currentTimeMillis();

        String returnValue =null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        try{
            httpClient = HttpClients.createDefault();

            HttpGet httpGet = new HttpGet(url);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(10*1000).setConnectTimeout(10*1000).setConnectionRequestTimeout(10*1000).build();
            httpGet.setConfig(requestConfig);

            returnValue = httpClient.execute(httpGet,responseHandler);
            log.info("send message takes: {}",System.currentTimeMillis()-startTime);
        }catch(Exception e){

            log.error("调用对外接口异常:"+url,e.fillInStackTrace());
        }finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
                log.error(e.getMessage(),e.fillInStackTrace());
            }
        }
        log.info("返回信息：{}",returnValue);
        return returnValue;
    }
//    public static String HttpPostWithJsonWays(String url, JSONObject param,String sendWay) {
//        Long startTime = System.currentTimeMillis();
//
//        String returnValue =null;
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        ResponseHandler<String> responseHandler = new BasicResponseHandler();
//        try{
//            if (sendWay.equals("http")){
//                httpClient = HttpClients.createDefault();
//            }else if (sendWay.equals("https")){
//                httpClient=getHttpsClient();
//            }
//            HttpPost httpPost = new HttpPost(url);
//            RequestConfig requestConfig = RequestConfig.custom()
//                    .setSocketTimeout(10*1000).setConnectTimeout(10*1000).setConnectionRequestTimeout(10*1000).build();
//            httpPost.setConfig(requestConfig);
//            StringEntity requestEntity = new StringEntity(param.toJSONString(),"utf-8");
//            requestEntity.setContentEncoding("UTF-8");
//            httpPost.setHeader("Content-type", "application/json");
//            httpPost.setEntity(requestEntity);
//            returnValue = httpClient.execute(httpPost,responseHandler);
//            log.info("send message takes: {}",System.currentTimeMillis()-startTime);
//        }catch(Exception e){
//
//            log.error("调用对外接口异常:"+url,e.fillInStackTrace());
//        }finally {
//            try {
//                httpClient.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//                log.error(e.getMessage(),e.fillInStackTrace());
//            }
//        }
//        return returnValue;
//    }
//
//
//
//    public static CloseableHttpClient getHttpsClient() throws Exception {
//
//        SSLContext sslContext = SSLContext.getInstance("TLS");
//        // 初始化SSL上下文
//        sslContext.init(null, new TrustManager[] { new HttpsTrustManager() }, null);
//        // SSL套接字连接工厂,NoopHostnameVerifier为信任所有服务器
//        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
//        /**
//         * 通过setSSLSocketFactory(sslsf)保证httpclient实例能发送Https请求
//         */
//        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).setMaxConnTotal(50)
//                .setMaxConnPerRoute(50).setDefaultRequestConfig(RequestConfig.custom()
//                        .setConnectionRequestTimeout(60000).setConnectTimeout(60000).setSocketTimeout(60000).build())
//                .build();
//
//        return httpclient;
//    }
}
