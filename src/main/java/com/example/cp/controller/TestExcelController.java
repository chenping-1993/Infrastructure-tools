package com.example.cp.controller;

import cn.afterturn.easypoi.cache.manager.POICacheManager;
import cn.afterturn.easypoi.excel.ExcelXorHtmlUtil;
import cn.afterturn.easypoi.excel.entity.ExcelToHtmlParams;
import com.example.cp.common.tool.ExcelUtil;
import com.example.cp.entity.User;
import com.example.cp.mapper.UserMapper;
import com.example.cp.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Description: excel 测试
 * @Author: chenping
 * @Date: 2020-11-16
 */
@Slf4j
@Api(tags = "excel 测试")
@RestController
public class TestExcelController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;

    @ApiOperation(value = "导出excel" ,  notes="导出excel")
    @GetMapping(value = "/exportExcel")
    public void exportExcel(HttpServletResponse response, HttpServletRequest request) {
        List<User> list = userMapper.selectAll();
        ExcelUtil.exportExcel(list,User.class,"用户信息表","用户信息统计",response,request);
    }

    /*
     * 预览excel excel文件不能有空行，否则会报空指针异常
     * 还不支持含有图片的预览
     */

    @ApiOperation(value = "预览excel xls格式" )
    @GetMapping("/excelToHtml")
    public void toHtmlOf07Base(HttpServletResponse response) throws IOException {
        ExcelToHtmlParams params = new ExcelToHtmlParams(WorkbookFactory.create(POICacheManager.getFile("C:/download/用户信息表.xls")),true,"yes");
        response.getOutputStream().write(ExcelXorHtmlUtil.excelToHtml(params).getBytes());
    }

    @ApiOperation(value = "预览excel xlsx格式" )
    @GetMapping("/excelToHtml2016")
    public void excelToHtml2016(HttpServletResponse response) throws IOException {
        ExcelToHtmlParams params = new ExcelToHtmlParams(WorkbookFactory.create(POICacheManager.getFile("C:/Users/chenping/Desktop/工作簿1.xlsx")),true,"yes");
        response.getOutputStream().write(ExcelXorHtmlUtil.excelToHtml(params).getBytes());
    }

    @ApiOperation(value = "导入excel 读取excel内容" )
    @PostMapping("/readExcelData")
    public List<User> readExcelData(@RequestBody MultipartFile multipartFile) {
        List<User> list = userService.readExcelToList(multipartFile);
        return list;
    }

}
