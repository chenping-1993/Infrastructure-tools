package com.example.cp.watermark;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

/**
 * @Description: 图片加水印
 * @Author: chenping
 * @Date: 2021-03-31
 */
public class AddPhotoWaterMark {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // 原图位置, 输出图片位置, 水印文字颜色, 水印文字

        try {
            // 读取原图片信息
            File srcImgFile = new File("C:/Users/chenping/Desktop/zp.jpg");
            String fileName = srcImgFile.getName();
            String fileType = fileName.substring(fileName.lastIndexOf("."),fileName.length()).replace(".","");
            Image srcImg = ImageIO.read(srcImgFile);
            int srcImgWidth = srcImg.getWidth(null);
            int srcImgHeight = srcImg.getHeight(null);
            // 加水印
            BufferedImage bufImg = new BufferedImage(srcImgWidth,
                    srcImgHeight,
                    BufferedImage.TYPE_INT_RGB);
            //获取 Graphics2D 对象
            Graphics2D g = bufImg.createGraphics();
            //设置绘图区域
            g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
            //设置字体
            Font font = new Font("宋体", Font.PLAIN, 20);
            // 根据图片的背景设置水印颜色
            g.setColor(Color.green);
            g.setFont(font);
            //获取文字长度
            int len = g.getFontMetrics(
                    g.getFont()).charsWidth("水印".toCharArray(),
                    0,
                    "水印".length());
            int x = srcImgWidth - len - 10;
            int y = srcImgHeight - 20;
            g.drawString("水印", x, y);
            g.dispose();
            // 输出图片
            FileOutputStream outImgStream = new FileOutputStream("C:/Users/chenping/Desktop/zp-1.jpg");
            ImageIO.write(bufImg, fileType, outImgStream);
            outImgStream.flush();
            outImgStream.close();
            System.out.println("OKOK");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
