package com.example.cp.entity;

import com.example.cp.common.enums.CodeMessage;
import lombok.Data;

/**
 * @Description:
 * @Author: chenping
 * @Date: 2020-05-23
 */
@Data
public class BaseResponse<T> {

    public  String code;

    public String message;
    public String trace;//错误信息

    private T data;

    private BaseResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public BaseResponse() {
    }

    private BaseResponse(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static BaseResponse ok() {
        return new BaseResponse(CodeMessage.SUCCESS.getCode(),CodeMessage.SUCCESS.getMsg());
    }

    public static <T> BaseResponse ok(T data) {
        return new BaseResponse<>(CodeMessage.SUCCESS.getCode(), CodeMessage.SUCCESS.getMsg(), data);
    }

    public static BaseResponse fail() {
        return new BaseResponse(CodeMessage.REQ_FAIL.getCode(),CodeMessage.REQ_FAIL.getMsg());
    }
    public static BaseResponse fail(String message) {
        return new BaseResponse(CodeMessage.REQ_FAIL.getCode(),message);
    }
}
