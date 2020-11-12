package com.example.cp.common.enums;

/**
 * @Description: 错误码 错误信息枚举
 * @Author: chenping
 * @Date: 2020-05-24
 */
public enum CodeMessage {

    SUCCESS("200", "成功"), WRONG_PARAMETER("1001", "请求参数有误"),
    WRONG_TIME("1002", "网络连接失败，请检查手机的日期和时间设置是否正确"),
    SERVICE_EXCEPTION("1500", "服务异常，请重试"),
    REQ_FAIL("500", "请求失败");

    private String code;

    private String msg;


    CodeMessage(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
