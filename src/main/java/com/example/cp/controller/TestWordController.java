package com.example.cp.controller;


import cn.afterturn.easypoi.word.WordExportUtil;

import com.alibaba.fastjson.JSON;
import com.example.cp.common.tool.ExcelUtil;
import com.example.cp.common.tool.WordUtils;
import com.example.cp.entity.City;
import com.example.cp.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.FileOutputStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: excel 测试
 * @Author: chenping
 * @Date: 2020-11-16
 */
@Slf4j
@Api(tags = "word 测试")
@RestController
public class TestWordController {

    /**
     * @Description:  代码指定word文件导出位置
     * @param:
     * @return: void
     * @Author: chenping
     * @Date: 2021/1/20 11:13
     */
    @ApiOperation(value = "导出word-指定导出位置" ,  notes="导出word-指定导出位置")
    @GetMapping(value = "/exportWord")
    public void simpleWordExport() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("department", "Easypoi");
        map.put("person", "JueYue");
//        map.put("time", format.format(new Date()));
        map.put("me","JueYue");
        map.put("date", "2015-01-03");
        try {
            //源文件
            XWPFDocument doc = WordExportUtil.exportWord07("C:/Users/chenping/Desktop/kafka.docx", map);
            //目标文件
            FileOutputStream fos = new FileOutputStream("C:/Users/chenping/Desktop/simple.docx");
            doc.write(fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description:  导出word文件  点击下载文件
     * @param: response
     * @param: request
     * @return: void
     * @Author: chenping
     * @Date: 2021/1/20 11:13
     */
    @ApiOperation(value = "导出word-点击下载" ,  notes="导出word-点击下载")
    @GetMapping(value = "/exportWord1")
    public void simpleWordExport1(HttpServletResponse response, HttpServletRequest request) {
        ExcelUtil.exportWord("C:/Users/chenping/Desktop/kafka.docx","kafka1.docx",response,request);
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "预览word文件" ,  notes="预览word文件")
    @GetMapping(value = "/viewDocToWord")
    public void viewDocToWord(HttpServletResponse response) throws ParserConfigurationException, TransformerException, IOException {
        String result = WordUtils.viewDoc("C:/Users/chenping/Desktop/","jl.doc");
        response.getOutputStream().write(result.getBytes());
    }


    /** 
     * @Description:  简单的往word中写数据，使用map
     * @param: response 
     * @return: void 
     * @Author: chenping
     * @Date: 2021/6/22 16:41
     */
    @ApiOperation(value = "写入数据到word文件并导出" )
    @GetMapping(value = "/writeAndExportWord")
    public void writeAndExportWord(HttpServletResponse response){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "标题");
        map.put("time", "2021");
        String url = System.getProperty("user.dir");
        url+="/src/main/resources/templates/writeAndExport.docx";
        try {
            XWPFDocument doc = WordExportUtil.exportWord07(url, map);
            FileOutputStream fos = new FileOutputStream("C:/Users/chenping/Desktop/writeAndExport11.docx");
            doc.write(fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 
     * @Description:  一个模板，多页数据，写入的数据结构是 list包含map
     * @param: response 
     * @return: void 
     * @Author: chenping
     * @Date: 2021/6/22 14:43
     */
    @ApiOperation(value = "写入列表数据到word文件并导出" )
    @GetMapping(value = "/writeTableDataAndExportWord")
    public void writeTableDataAndExportWord(HttpServletResponse response){
        User user = new User();
        user.setName("小李");
        user.setAge(22);
        User user1 = new User();
        user1.setName("小刘");
        user1.setAge(24);
        Map<String,Object> map1 = JSON.parseObject(JSON.toJSONString(user), Map.class);
        Map<String,Object> map2 = JSON.parseObject(JSON.toJSONString(user1), Map.class);
        List<Map<String,Object>> list = new ArrayList<>();
        list.add(map1);
        list.add(map2);
        String url = System.getProperty("user.dir");
        url+="/src/main/resources/templates/word-excel.docx";
        try {
            XWPFDocument doc = WordExportUtil.exportWord07(url, list);
            FileOutputStream fos = new FileOutputStream("C:/Users/chenping/Desktop/word-excel-1.docx");
            doc.write(fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description:  写入list table数据到word   一个模板，内有table列表，结构是map 包含list
     * @param: response
     * @return: void
     * @Author: chenping
     * @Date: 2021/6/22 14:43
     */
    @ApiOperation(value = "写入列表数据到word文件并导出" )
    @GetMapping(value = "/writeListDataAndExportWord")
    public void writeListDataAndExportWord(HttpServletResponse response){
        User user = new User();
        user.setName("小李");
        user.setAge(22);
        User user1 = new User();
        user1.setName("小刘");
        user1.setAge(24);
        Map<String,Object> map1 = JSON.parseObject(JSON.toJSONString(user), Map.class);
        Map<String,Object> map2 = JSON.parseObject(JSON.toJSONString(user1), Map.class);
        List<Map<String,Object>> list = new ArrayList<>();
        list.add(map1);
        list.add(map2);

        City city = new City("菏泽",2345,16,"山东");
        City city1 = new City("枣庄",2945,11,"山东");
        City city2 = new City("德州",8345,17,"山东");
        City city3 = new City("济南",8945,13,"山东");
        Map<String,Object> c1 = JSON.parseObject(JSON.toJSONString(city), Map.class);
        Map<String,Object> c2 = JSON.parseObject(JSON.toJSONString(city1), Map.class);
        Map<String,Object> c3 = JSON.parseObject(JSON.toJSONString(city2), Map.class);
        Map<String,Object> c4 = JSON.parseObject(JSON.toJSONString(city3), Map.class);
        List<Map<String,Object>> list1 = new ArrayList<>();
        list1.add(c1);
        list1.add(c2);
        list1.add(c3);
        list1.add(c4);

        Map<String,Object> res = new HashMap<>();
        res.put("user",list);
        res.put("city",list1);
        String url = System.getProperty("user.dir");
        url+="/src/main/resources/templates/word-table.docx";
        try {
            XWPFDocument doc = WordExportUtil.exportWord07(url, res);
            FileOutputStream fos = new FileOutputStream("C:/Users/chenping/Desktop/word-table-1.docx");
            doc.write(fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
