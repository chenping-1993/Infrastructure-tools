package com.example.cp.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * @Description:
 * @Author: chenping
 * @Date: 2020-05-24
 */
@Data
public class ResponseVO {
    public JSONObject data;
    public String code;
}
