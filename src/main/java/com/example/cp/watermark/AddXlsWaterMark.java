package com.example.cp.watermark;

import com.spire.xls.*;
import com.spire.xls.core.IPictureShape;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Iterator;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

/**
 * @Description: 限制
 *  免费版仅限于每本工作簿5张，每页200行。在读取或写入XLS文件的过程中会强制执行此限制。
 *  加载和保存.xlsx文件格式时没有任何限制。将Excel文件转换为PDF文件时，只能获取PDF文件的前3页
 * @Author: chenping
 * @Date: 2021-04-08
 */
public class AddXlsWaterMark {

    public static void main(String[] args)  {

        //加载示例文档
        Workbook workbook = new Workbook();
        workbook.loadFromFile("C:\\Users\\chenping\\Desktop\\aa.xlsx");

        //设置文本和字体大小
        Font font = new Font("仿宋", Font.PLAIN, 40);
        String watermark = "内部专用";

        for (Iterator iterator = ((Iterable) workbook.getWorksheets()).iterator(); iterator.hasNext(); ) {
            Worksheet sheet = (Worksheet) iterator.next();
//            //调用DrawText() 方法插入图片
            BufferedImage imgWtrmrk = drawText(watermark, font, Color.pink, Color.white, sheet.getPageSetup().getPageHeight(), sheet.getPageSetup().getPageWidth());

            //将图片设置为页眉
//            sheet.getPageSetup().setLeftHeaderImage(imgWtrmrk);
//            sheet.getPageSetup().setLeftHeader("&G");
//
//            //将显示模式设置为Layout
//            sheet.setViewMode(ViewMode.Layout);
            //以上注释同一个sheet页会分为多个块，有问题

            IPictureShape shape = sheet.getShapes().addPicture(imgWtrmrk,"aa", ImageFormatType.Png);
            sheet.protect("123456");
        }

        //保存文档
        workbook.saveToFile("C:\\Users\\chenping\\Desktop\\aa-2.xlsx");
    }
    private static BufferedImage drawText (String text, Font font, Color textColor, Color backColor,double height, double width)
    {
        //定义图片宽度和高度
        BufferedImage img = new BufferedImage((int) width, (int) height, TYPE_INT_ARGB);
        Graphics2D loGraphic = img.createGraphics();

        //获取文本size
        FontMetrics loFontMetrics = loGraphic.getFontMetrics(font);
        int liStrWidth = loFontMetrics.stringWidth(text);
        int liStrHeight = loFontMetrics.getHeight();

        //文本显示样式及位置
        loGraphic.setColor(backColor);
//        loGraphic.fillRect(0, 0, (int) width, (int) height);
        loGraphic.translate(((int) width - liStrWidth) / 2, ((int) height - liStrHeight) / 2);
        loGraphic.rotate(Math.toRadians(-45));

        loGraphic.translate(-((int) width - liStrWidth) / 2, -((int) height - liStrHeight) / 2);
        loGraphic.setFont(font);
        loGraphic.setColor(textColor);
        loGraphic.drawString(text, ((int) width - liStrWidth) / 2, ((int) height - liStrHeight) / 2);
        loGraphic.dispose();
        return img;
    }
}
