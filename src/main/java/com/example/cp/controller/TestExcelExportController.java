package com.example.cp.controller;

import com.example.cp.common.tool.ExcelUtil;
import com.example.cp.entity.User;
import com.example.cp.mapper.UserMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Description: excel 导出测试
 * @Author: chenping
 * @Date: 2020-11-16
 */
@Api(tags = "excel 导出")
@RestController
public class TestExcelExportController {

    @Autowired
    UserMapper userMapper;

    @ApiOperation(value = "导出excel" ,  notes="导出excel")
    @GetMapping(value = "/exportExcel")
    public void exportExcel(HttpServletResponse response, HttpServletRequest request) {
        List<User> list = userMapper.selectAll();
        ExcelUtil.exportExcel(list,User.class,"用户信息表","用户信息统计",response,request);
    }
}
