package com.example.cp.common.tool;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @Description:
 * @Author: chenping
 * @Date: 2020-11-16
 */
public class ExcelUtil {

    /**
     * 导出excel
     *
     * @param list 数据集合
     * @param pojoClass 数据类型
     * @param fileName 文件名称
     * @param title 表明
     * @param response 响应对象
     */
    public static void exportExcel(List<?> list, Class<?> pojoClass, String fileName, String title, HttpServletResponse response,HttpServletRequest request) {
        ExportParams exportParams = new ExportParams(title, null);
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);

        String agent = request.getHeader("User-Agent");

        // 自定义字典查询规则
//        exportParams.setDictHandler(new IExcelDictHandlerImpl());
        if (workbook != null) {
            try {
                if (agent.contains("MSIE") || agent.contains("Trident")) {//IE浏览器
                    fileName = URLEncoder.encode(fileName,"utf-8");
                } else {//非IE浏览器
                    fileName = new String(fileName.getBytes(StandardCharsets.UTF_8),StandardCharsets.ISO_8859_1);
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
}
