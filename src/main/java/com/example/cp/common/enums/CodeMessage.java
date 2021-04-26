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

    /**
     * @Description:  根据code获取message
     * @param: code
     * @return: java.lang.String
     * @Author: chenping
     * @Date: 2021/3/25 17:34
     */
    public static String getMessageByCode(String code) {
        CodeMessage[] codeMsgs = CodeMessage.values();
        for (CodeMessage codeMessage: codeMsgs) {
            if (code.equals(codeMessage.getCode())) {
                return codeMessage.getMsg();
            }
        }
        return "";
    }

    /**
     * @Description:  根据code获取枚举
     * @param: code
     * @return: com.example.cp.common.enums.CodeMessage
     * @Author: chenping
     * @Date: 2021/4/25 13:48
     */
    public static CodeMessage getEnumByCode(String code) {
        CodeMessage[] codeMsgs = CodeMessage.values();
        for (CodeMessage codeMessage: codeMsgs) {
            if (code.equals(codeMessage.getCode())) {
                return codeMessage;
            }
        }
        return null;
    }

}
