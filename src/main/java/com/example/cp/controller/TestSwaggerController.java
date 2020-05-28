package com.example.cp.controller;

import com.example.cp.entity.User;
import com.example.cp.mapper.UserMapper;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description:
 * @Author: chenping
 * @Date: 2020-05-28
 */
@Api(tags = "测试swagger接口")
@RestController
@RequestMapping("/cp")
public class TestSwaggerController {

    @Autowired
    UserMapper userMapper;

    @ApiOperation(value = "POST测试" ,  notes="POST测试")
    @RequestMapping(value = "/testPostSwagger",method = RequestMethod.POST)
    public Object testSwagger(@RequestBody User user) {
        List<User> u = userMapper.selectAll();
        return u;
    }

    @ApiOperation(value = "GET测试" ,  notes="GET测试")
    @RequestMapping(value = "/testGetSwagger",method = RequestMethod.GET)
    public Object testGetSwagger() {
        List<User> u = userMapper.selectAll();
        return u;
    }

    @ApiOperation(value = "GET带参数测试" ,  notes="GET带参数测试")
    @ApiImplicitParam(name = "userName", value = "用户名", required = true,paramType = "query", dataType = "String")
    @RequestMapping(value = "/testGetWithParamSwagger",method = RequestMethod.GET)
    public Object testGetWithParamSwagger(@RequestParam(value = "userName")String userName) {
        List<User> u = userMapper.selectAll();
        return u;
    }

    @ApiOperation(value = "GET多参数测试" ,  notes="GET多参数测试")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="主键id",required=true,paramType="query"),
            @ApiImplicitParam(name = "userName", value = "用户名",defaultValue = "jack",required = true,paramType = "query", dataType = "String")
    })
    @RequestMapping(value = "/testGetWithParamsSwagger",method = RequestMethod.GET)
    public Object testGetWithParamsSwagger(@RequestParam(value = "userName")String userName,@RequestParam(value = "id")Integer id) {
        System.out.println(id);
        List<User> u = userMapper.selectAll();
        return u;
    }
}
