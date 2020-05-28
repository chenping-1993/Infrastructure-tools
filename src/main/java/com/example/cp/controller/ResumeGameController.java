package com.example.cp.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 恢复游戏
 * @Author: chenping
 * @Date: 2020-05-22
 */
@RestController
public class ResumeGameController {

    @PostMapping("/resumeGame")
    public JSONObject backGame() {

        return null;
    }
}
