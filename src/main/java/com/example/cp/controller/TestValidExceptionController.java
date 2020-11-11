package com.example.cp.controller;

import com.example.cp.entity.TestValidVO;
import com.example.cp.entity.User;
import com.example.cp.mapper.UserMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description: 测试 校验入参、接口异常
 * @Author: chenping
 * @Date: 2020-11-11
 */
@Api(tags = "测试 校验入参、接口异常")
@RestController
public class TestValidExceptionController {

    @Autowired
    UserMapper userMapper;

    @ApiOperation(value = "POST入参校验" ,  notes="POST入参校验")
    @PostMapping(value = "/testPostValid")
    public List<User> testSwagger(@RequestBody @Validated TestValidVO vo) {
        List<User> u = userMapper.selectAll();
        return u;
    }
}
