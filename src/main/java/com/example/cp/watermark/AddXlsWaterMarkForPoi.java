package com.example.cp.watermark;

//import com.example.cp.service.handler.WaterMarkHandler;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFRelation;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;

/**
 * @Description: 使用原生的poi给excel文件加水印
 * @Author: chenping
 * @Date: 2021-04-08
 */
public class AddXlsWaterMarkForPoi {

//    public static void main(String[] args) throws IOException {
//        FileOutputStream fileOutputStream = new FileOutputStream(new File("C:\\Users\\chenping\\Desktop\\aa-2.xlsx"));
//        XSSFWorkbook workbook = new XSSFWorkbook("C:\\Users\\chenping\\Desktop\\aa.xlsx");
//
//        int sheetNum = workbook.getNumberOfSheets();
//        for (int i = 0; i <sheetNum ; i++) {
//            try (ByteArrayOutputStream byteArrayOutputStream = WaterMarkHandler.createWaterMark("我是水印")){
//                int pictureIdx = workbook.addPicture(byteArrayOutputStream.toByteArray(), Workbook.PICTURE_TYPE_PNG);
//                //add relation from sheet to the picture data
//                String rID = workbook.getSheetAt(i).addRelation(null, XSSFRelation.IMAGES, workbook.getAllPictures().get(pictureIdx))
//                        .getRelationship().getId();
//                //set background picture to sheet
//                workbook.getSheetAt(i).getCTWorksheet().addNewPicture().setId(rID);
//
//                workbook.getSheetAt(i).protectSheet("123456");
//            }
//
//        }
//        workbook.write(fileOutputStream);
//        fileOutputStream.close();
//    }
}
