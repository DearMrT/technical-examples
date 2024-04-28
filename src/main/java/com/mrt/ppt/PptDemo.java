package com.mrt.ppt;

import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextShape;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @Author: Mr.T
 * @Date: 2024/4/28
 */
public class PptDemo {


    public static void main(String[] args) {
        try {
            // 读取现有的PPT文件
            FileInputStream fis = new FileInputStream("E:\\test\\ppt\\test.pptx");
            XMLSlideShow ppt = new XMLSlideShow(fis);

            int size = ppt.getSlides().size();
            System.out.println("页数:"+size);
            List<XSLFSlide> slides = ppt.getSlides();
            XSLFSlide xslfShapes = slides.get(0);
            List<XSLFShape> shapes = xslfShapes.getShapes();

            fis.close();

            // 创建一个新的幻灯片
            XSLFSlide slide = ppt.createSlide();

            // 在幻灯片上添加文本框
            XSLFTextShape title = slide.createTextBox();
            title.setText("Hello, Apache POI!");
            title.setAnchor(new java.awt.Rectangle(50, 50, 300, 50));

            // 保存修改后的PPT文件
            FileOutputStream fos = new FileOutputStream("E:\\test\\ppt\\modified_presentation.pptx");
            ppt.write(fos);
            fos.close();

            System.out.println("PPT file modified successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
