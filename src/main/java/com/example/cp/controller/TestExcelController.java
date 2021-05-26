package com.example.cp.controller;

import cn.afterturn.easypoi.cache.manager.POICacheManager;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.ExcelXorHtmlUtil;
import cn.afterturn.easypoi.excel.entity.ExcelToHtmlParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.example.cp.common.tool.ExcelUtil;
import com.example.cp.entity.City;
import com.example.cp.entity.User;
import com.example.cp.mapper.UserMapper;
import com.example.cp.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
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
import java.io.InputStream;
import java.util.ArrayList;
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

    @ApiOperation(value = "导出单元格合并excel" ,  notes="导出单元格合并excel")
    @GetMapping(value = "/exportMergeExcel")
    public void exportMergeExcel(HttpServletResponse response, HttpServletRequest request) {
        List<City> list = new ArrayList<>();
        formData(list);
        ExcelUtil.exportExcel(list,City.class,"城市信息","城市信息",response,request);
    }

    /*
     * 预览excel excel文件不能有空行，否则会报空指针异常
     * 还不支持含有图片的预览
     */

    @ApiOperation(value = "预览excel xls格式" )
    @GetMapping("/excelToHtml")
    public void toHtmlOf07Base(HttpServletResponse response) throws IOException {
        ExcelToHtmlParams params = new ExcelToHtmlParams(WorkbookFactory.create(POICacheManager.getFile("C:/download/bbb.xlsx")),true,"yes");
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

    @ApiOperation(value = "注解导入excel" )
    @PostMapping("/importExcelData")
    public List<User> readExcelData1(@RequestBody MultipartFile multipartFile) throws Exception {
        InputStream inputStream = multipartFile.getInputStream();
        ImportParams importParams = new ImportParams();
        importParams.setHeadRows(1);
        importParams.setTitleRows(0);
        List<User> list = ExcelImportUtil.importExcel(inputStream, User.class, importParams);
        return list;
    }
    @ApiOperation(value = "预览excel多sheet" )
    @GetMapping("/manySheet")
    public void manySheet(HttpServletResponse response) throws IOException {
        StringBuilder html = new StringBuilder();
        Workbook workbook = WorkbookFactory.create(POICacheManager.getFile("C:/download/manysheet.xlsx"));
        int sheets = workbook.getNumberOfSheets();
        for (int i = 0; i <sheets ; i++) {
            ExcelToHtmlParams params = new ExcelToHtmlParams(workbook,true,i,"yes");
            String s = ExcelXorHtmlUtil.excelToHtml(params);
            html.append(s).append("\r");
        }
        response.getOutputStream().write(html.toString().getBytes());
    }
    private static void formData(List<City> list) {
        City city = new City("菏泽",2345,16,"山东");
        City city1 = new City("枣庄",2945,11,"山东");
        City city2 = new City("德州",8345,17,"山东");
        City city3 = new City("济南",8945,13,"山东");

        City city4 = new City("郑州",4670,18,"河南");
        City city5 = new City("开封",4550,21,"河南");
        City city6 = new City("洛阳",4580,27,"河南");
        City city7 = new City("新乡",4530,23,"河南");

        City city8 = new City("西安",2339,5,"陕西");
        City city9 = new City("渭南",2724,22,"陕西");
        City city10 = new City("宝鸡",3671,7,"陕西");
        City city11 = new City("榆林",4357,24,"陕西");
        City city12 = new City("榆林",8888,29,"陕西");
        list.add(city);
        list.add(city1);
        list.add(city2);
        list.add(city3);
        list.add(city4);
        list.add(city5);
        list.add(city6);
        list.add(city7);
        list.add(city8);
        list.add(city9);
        list.add(city10);
        list.add(city11);
        list.add(city12);
    }
}
