package com.example.cp.service;

import com.example.cp.common.exception.CustomException;
import com.example.cp.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: chenping
 * @Date: 2020-12-11
 */
@Slf4j
@Service
public class UserService {


    /**
     * @Description: 读取excel内容
     * @param: file
     * @return: java.util.List<com.example.cp.entity.User>
     * @Author: chenping
     * @Date: 2020/12/11 15:05
     */
    public List<User> readExcelToList(MultipartFile file) {
        List<User> list = new ArrayList<>();
        Workbook wb;
        Sheet sheet;
        String fileName = file.getOriginalFilename();

        try {
            if (fileName.endsWith("xls")) {
                wb = new HSSFWorkbook(file.getInputStream());

            } else if (fileName.endsWith("xlsx")) {
                wb = new XSSFWorkbook(file.getInputStream());
            } else {
                throw new CustomException("文件格式不正确");
            }
            sheet = wb.getSheetAt(0);//获取第一个sheet页
            int rowNum = sheet.getPhysicalNumberOfRows();//最大行数
            Row row = sheet.getRow(0);//第一行
            int columnNum = row.getPhysicalNumberOfCells();//最大列数

            //循环行，第一行是标题，从第二行开始
            for (int i = 1; i < rowNum; i++) {
                row = sheet.getRow(i);
                if (null != row) {
                    List<String> oneRowValueList = new ArrayList<>();//把一行的数据存起来
                    //循环取每一行列的数据
                    for (int j = 0; j < columnNum; j++) {
                        CellType cellType = row.getCell(j).getCellType();
                        if (cellType.equals(CellType.NUMERIC)) {
                            //按照excel单元格的真实数据格式取数据，转为字符串； 否则有可能出现： 真实数据是int类型，取出来是double类型
                            String num = NumberToTextConverter.toText(row.getCell(j).getNumericCellValue());
                            oneRowValueList.add(num);
                        } else if (cellType.equals(CellType.STRING)) {
                            oneRowValueList.add(String.valueOf(row.getCell(j).getStringCellValue()));
                        } else {
                            oneRowValueList.add("");
                        }
                    }
                    User user = new User();
                    user.setId(Integer.parseInt(oneRowValueList.get(0)));
                    user.setName(oneRowValueList.get(1));
                    user.setAge(Integer.parseInt(oneRowValueList.get(2)));

                    list.add(user);
                }
            }
        } catch (IOException e) {
            log.error(e.toString());
            e.printStackTrace();
        }


        return list;
    }

}
