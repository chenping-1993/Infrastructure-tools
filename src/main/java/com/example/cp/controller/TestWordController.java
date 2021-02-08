package com.example.cp.controller;


import cn.afterturn.easypoi.word.WordExportUtil;

import com.example.cp.common.tool.ExcelUtil;
import com.example.cp.common.tool.WordUtils;
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
import java.util.HashMap;
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

}
