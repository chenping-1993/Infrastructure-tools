package com.example.cp.watermark;
import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import com.spire.doc.Section;
import com.spire.doc.TextWatermark;
import com.spire.doc.collections.SectionCollection;
import com.spire.doc.documents.WatermarkLayout;

import java.awt.*;

/**
 * @Description:
 * 官网示例：https://www.e-iceblue.cn/doc_java_watermark/java-add-text-watermark-and-image-watermark-to-word-document.html
 * 限制：
 *  Java的免费Spire.Doc限于500个段落和25个表。在读取或写入文件期间会强制执行此限制。
 *  将Word文档转换为PDF和XPS文件时，只能获得PDF文件的前3页
 * @Author: chenping
 * @Date: 2021-04-08
 */
public class AddDocWaterMark {
    public static void main(String[] args) {

        Document document = new Document();
        document.loadFromFile("C:\\Users\\chenping\\Desktop\\simple.docx");
        SectionCollection sections = document.getSections();
        for (int i = 0; i < sections.getCount(); i++) {
            insertTextWatermark(document.getSections().get(i));
        }
        document.saveToFile("C:\\Users\\chenping\\Desktop\\simple-1.docx", FileFormat.Docx );
    }

    private static void insertTextWatermark(Section section) {
        TextWatermark txtWatermark = new TextWatermark();
        txtWatermark.setText("watermark");
        txtWatermark.setFontSize(40);
        txtWatermark.setColor(Color.red);
        txtWatermark.setLayout(WatermarkLayout.Diagonal);
        section.getDocument().setWatermark(txtWatermark);
    }
}
