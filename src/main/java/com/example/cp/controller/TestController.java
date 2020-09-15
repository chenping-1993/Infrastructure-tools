package com.example.cp.controller;

import com.example.cp.common.tool.ZipUtil;
import com.example.cp.entity.User;
import com.example.cp.mapper.UserMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @Description:
 * @Author: chenping
 * @Date: 2020-09-15
 */
@Slf4j
@Api(tags = "测试接口")
@RestController
public class TestController extends BaseController {

    @Autowired
    UserMapper userMapper;

    @ApiOperation("测试zip打包")
    @GetMapping("/tozip")
    public void zipTest() throws IOException {
        ZipUtil.toZip("C:/资料/cp/work/知识问题.docx","C:/资料/cp/work/aa.zip",true);
    }

    @ApiOperation("测试输出sql")
    @GetMapping("/outsql")
    public void outsql() {
        log.info("测试输出sql============");
        log.error("输出错误级别日志===========");
        List<User> list = userMapper.list();
    }
}
