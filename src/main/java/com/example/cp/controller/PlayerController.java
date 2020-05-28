package com.example.cp.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 玩家信息
 * @Author: chenping
 * @Date: 2020-05-22
 */
@RestController
public class PlayerController {

    @PostMapping("/playerInformation")
    public JSONObject playersInformation() {
        return null;
    }
}
