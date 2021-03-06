package com.example.cp.common.tool;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.word.WordExportUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: chenping
 * @Date: 2020-11-16
 */
public class ExcelUtil {

    /**
     * 导出excel
     *
     * @param list      数据集合
     * @param pojoClass 数据类型
     * @param fileName  文件名称
     * @param title     表明
     * @param response  响应对象
     */
    public static void exportExcel(List<?> list, Class<?> pojoClass, String fileName, String title, HttpServletResponse response, HttpServletRequest request) {
        ExportParams exportParams = new ExportParams(title, null);
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);

        String agent = request.getHeader("User-Agent");

        // 自定义字典查询规则
//        exportParams.setDictHandler(new IExcelDictHandlerImpl());
        if (workbook != null) {
            try {
                if (agent.contains("MSIE") || agent.contains("Trident")) {//IE浏览器
                    fileName = URLEncoder.encode(fileName, "utf-8");
                } else {//非IE浏览器
                    fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
                }
                response.setCharacterEncoding("UTF-8");
                response.setHeader("content-Type", "application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");
                workbook.write(response.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @Description:  导出word文件
     * @param: filePath 源文件地址
     * @param: fileName 要导出的文件名
     * @param: response
     * @param: request
     * @return: void
     * @Author: chenping
     * @Date: 2021/1/20 11:12
     */
    public static void exportWord(String filePath, String fileName, HttpServletResponse response, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("department", "Easypoi");
        map.put("person", "JueYue");
        map.put("me","JueYue");
        map.put("date", "2015-01-03");
        try {
            XWPFDocument doc = WordExportUtil.exportWord07(filePath, map);

            String agent = request.getHeader("User-Agent");

            if (doc != null) {

                if (agent.contains("MSIE") || agent.contains("Trident")) {//IE浏览器
                    fileName = URLEncoder.encode(fileName, "utf-8");
                } else {//非IE浏览器
                    fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
                }
                response.setCharacterEncoding("UTF-8");
                response.setHeader("content-Type", "application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
                doc.write(response.getOutputStream());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
