package com.example.cp.watermark;

import com.spire.pdf.PdfDocument;
import com.spire.pdf.PdfPageBase;
import com.spire.pdf.graphics.*;

import java.awt.*;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * @Description:
 * 限制：免费版本仅限于10页PDF。在编写PDF时会强制执行此限制。将PDF转换为Image，XPS，Word，HTML时，您只能获取文件的前3页
 * @Author: chenping
 * @Date: 2021-04-08
 */
public class AddPdfWaterMark {
    public static void main(String[] args) {

        //create a PdfDocument instance
        PdfDocument pdf = new PdfDocument();

        //load the sample document
        pdf.loadFromFile("C:\\Users\\chenping\\Desktop\\test-pdf.pdf");
        //get the first page of the PDF
        PdfPageBase page = pdf.getPages().get(0);
        //use insertWatermark()to insert the watermark
        insertWatermark(page, "watermark");
        //save the document to file
        pdf.saveToFile("C:\\Users\\chenping\\Desktop\\test-pdf-2.pdf");
    }

    static void insertWatermark(PdfPageBase page, String watermark) {
        Dimension2D dimension2D = new Dimension();
        dimension2D.setSize(page.getCanvas().getClientSize().getWidth() / 2, page.getCanvas().getClientSize().getHeight() / 3);
        PdfTilingBrush brush = new PdfTilingBrush(dimension2D);
        brush.getGraphics().setTransparency(0.3F);
        brush.getGraphics().save();
        brush.getGraphics().translateTransform((float) brush.getSize().getWidth() / 2, (float) brush.getSize().getHeight() / 2);
        brush.getGraphics().rotateTransform(-45);
        brush.getGraphics().drawString(watermark, new PdfFont(PdfFontFamily.Helvetica, 24), PdfBrushes.getViolet(), 0, 0, new PdfStringFormat(PdfTextAlignment.Center));
        brush.getGraphics().restore();
        brush.getGraphics().setTransparency(1);
        Rectangle2D loRect = new Rectangle2D.Float();
        loRect.setFrame(new Point2D.Float(0, 0), page.getCanvas().getClientSize());
        page.getCanvas().drawRectangle(brush, loRect);
    }
}
