package com.example.cp.common.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.cp.common.enums.CodeMessage;
import com.example.cp.common.tool.AESUtil;
import com.example.cp.common.tool.StringUtils;
import com.example.cp.entity.BaseResponse;
import com.example.cp.common.config.YmlPropers;
import com.example.cp.common.tool.Md5Utils;
import com.whalin.MemCached.MemCachedClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.util.Date;

/**
 * @Description: 过滤器
 * @Author: chenping
 * @Date: 2020-05-23
 */
@Slf4j
@Component
@WebFilter
public class AuthFilter implements Filter {

    @Autowired
    YmlPropers config;

    @Autowired
    private MemCachedClient memCachedClient;

    //请求过期时间，固定15分钟，可配
    final static Long fifteenMinutes = 15 * 60 * 1000L;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        if (config == null) {
            ServletContext servletContext = filterConfig.getServletContext();
            ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            config = (YmlPropers) ctx.getBean("ymlPropers");
        }
        if (memCachedClient == null) {
            ServletContext servletContext = filterConfig.getServletContext();
            ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            memCachedClient = (MemCachedClient) ctx.getBean("memCachedClient");
        }
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String errText = "";
        String code = "";
        String requestURI = request.getRequestURI().replaceFirst(request.getContextPath(), "");
        //方案1：config.notAuthList 配置不过滤的url
        //方案2：urlPatterns 配置某个url前缀下的接口都过滤，@WebFilter(urlPatterns = "/cxyh/filter/api/*", filterName = "rest0PubFilter")
        if (config.notAuthList.contains(requestURI)) {
            log.info("不鉴权：" + requestURI);
        } else {
            // 防止流读取一次后就没有了, 所以需要将流继续写出去
            ServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper(request);
            String jsonString = HttpHelper.getBodyString(requestWrapper);

            String sign = request.getHeader("sign");
            if (StringUtils.isEmpty(sign)) {
                log.info("sign 为空");
                errText = "请求参数有误";
                code = CodeMessage.WRONG_PARAMETER.getCode();
                dealResp(response, errText, code);
                return;
            }

            String md5Entry = Md5Utils.md5Encrypt(jsonString + config.matchSign);
            if (!md5Entry.equalsIgnoreCase(sign)) {
                log.info("sign 匹配失败");
                errText = "请求参数有误";
                dealResp(response, errText, code);
                return;
            }

            JSONObject jsonObject = JSON.parseObject(jsonString);
            Long sendTime = jsonObject.getLong("ts");
            String udid = jsonObject.getString("udid");
            String os = jsonObject.getString("os");
            String adid = jsonObject.getString("adid");
            String token = jsonObject.getString("token");

            String deviceId = "";
            if (os.equalsIgnoreCase("android")) {
                deviceId = !StringUtils.isEmpty(adid) && adid.length() > 8 ? adid : udid;
            } else {
                deviceId = udid;
            }

            Long now = System.currentTimeMillis();
            Long time = now - sendTime;
            if (time > fifteenMinutes || time < (-fifteenMinutes)) {
//            if (false) {
                log.info("请求时间异常");
                errText = "网络连接失败，请检查手机的日期和时间设置是否正确";
                code = "1002";
                dealResp(response, errText, code);
                return;
            }

            /**
             * memcached 去匹配 sign
             *
             * 有value不通过，无value通过，set值
             */

            String cacheKey = config.environment + "_1_req_" + requestURI + "_" + deviceId + "_" + sendTime;
            Object value = memCachedClient.get(cacheKey);

            if (null !=value && value.equals(sign)) {
//            if (false) {
                log.info("memcached验证异常");
                errText = "请求参数有误";
                code = "1001";
                dealResp(response, errText, code);
                return;
            }
//                        } else if (value == null){
            boolean setCacheKey = memCachedClient.set(cacheKey, sign, new Date(fifteenMinutes));//过期时间 15分钟
            log.info("memcached验证成功，设置缓存：{}", setCacheKey ? "成功" : "失败");
            try {
                String desToken = AESUtil.desEncrypt(token,config.tokenKey,config.tokenIv);
                if (StringUtils.isEmpty(desToken)) {
                    log.info("token解密异常");
                    errText = "请求参数有误";
                    code = "1001";
                    dealResp(response, errText, code);
                    return;
                }
            } catch (Exception e) {
                log.info("token解密异常");
                errText = "请求参数有误";
                code = "1001";
                dealResp(response, errText, code);
                return;
            }
            //如果需要解密body的token,可配置
//            if (true) {
//                String realToken = AESUtils.decrypt(token, config.tokenKey);//key 不同
//                jsonObject.put("Token", realToken);
//                String jsonStr = jsonObject.toString();
//                ModifyBodyHttpServletRequestWrapper httpServletRequestWrapper = new ModifyBodyHttpServletRequestWrapper(request, jsonStr);
//                chain.doFilter(httpServletRequestWrapper, res);
//                return;
//            }

            chain.doFilter(requestWrapper, res);
            return;
        }

        chain.doFilter(req, res);

    }

    /**
     * @Description:  请求不符合过滤要求，返回响应
     * @param: response
     * @param: errText
     * @param: code
     * @return: void
     * @Author: chenping
     * @Date: 2020/5/26
     */
    private void dealResp(HttpServletResponse response, String errText, String code) throws IOException {
        BaseResponse resp = new BaseResponse();
        resp.setMessage(errText);
        resp.setCode(code);

        String string = JSON.toJSONString(resp);


        System.out.println("过滤 http响应json" + string);
        response.setHeader("cache-control", "no-cache");
        response.setContentType("text/html");
//        response.setContentType("application/json");
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        PrintWriter pw = response.getWriter();
        pw.write(string);
        pw.flush();
        pw.close();
    }

    @Override
    public void destroy() {
    }

}


class BodyReaderHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final byte[] body;

    public BodyReaderHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        body = HttpHelper.getBodyString(request).getBytes(Charset.forName("UTF-8"));
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {

        final ByteArrayInputStream bais = new ByteArrayInputStream(body);

        return new ServletInputStream() {

            @Override
            public int read() throws IOException {
                return bais.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }
        };
    }
}

class HttpHelper {

    /**
     * 获取请求Body
     *
     * @param request
     * @return
     */
    public static String getBodyString(ServletRequest request) {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = request.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

}

//将解密后的body体的token重新赋值
class ModifyBodyHttpServletRequestWrapper extends HttpServletRequestWrapper {

    // 重新赋值的body数据
    private String bodyJsonStr;

    public ModifyBodyHttpServletRequestWrapper(HttpServletRequest request, String bodyJsonStr) {
        super(request);
        this.bodyJsonStr = bodyJsonStr;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (StringUtils.isEmpty(bodyJsonStr)) {
            bodyJsonStr = "";
        }
        // 必须指定utf-8编码，否则json请求数据中如果包含中文，会出现异常
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bodyJsonStr.getBytes("utf-8"));
        ServletInputStream servletInputStream = new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
            }

            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }
        };
        return servletInputStream;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    public String getBodyJsonStr() {
        return bodyJsonStr;
    }

    public void setBodyJsonStr(String bodyJsonStr) {
        this.bodyJsonStr = bodyJsonStr;
    }
}