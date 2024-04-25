package com.mrt.ocr;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Mr.T
 * @Date: 2024/4/23
 */
public class ImageOrc {

    static {
        try {
            System.load("C:\\opencv\\build\\java\\x64\\opencv_java454.dll");
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Cannot load the opencv_java451 library: " + e.getMessage());
        }
    }

    public static void detectTextRegion(String imagePath) {
        // 读取图像
        Mat src = Imgcodecs.imread(imagePath);
        // 转换为灰度图像
        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
        // 应用阈值化，以便更清晰地看到文字
        Mat thresh = new Mat();
        Imgproc.threshold(gray, thresh, 0, 255, Imgproc.THRESH_BINARY_INV + Imgproc.THRESH_OTSU);
        // 这里可以添加更多图像处理步骤，如寻找轮廓等
        // 保存处理后的图像，或进一步处理
        Imgcodecs.imwrite("E:\\test\\book\\ocr\\0.jpg", thresh);
    }


    public static void detectTextRegion2(String imagePath) throws TesseractException {
        // 读取图像
        Mat src = Imgcodecs.imread(imagePath);
        Mat gray = new Mat();
        // 转换为灰度图
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
        // 应用高斯模糊
        Imgproc.GaussianBlur(gray, gray, new Size(5, 5), 0);
        // 边缘检测
        Mat edges = new Mat();
        Imgproc.Canny(gray, edges, 75, 200);

        // 寻找轮廓
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(edges, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        List<Rectangle> rectangles = new ArrayList<>();
        // 筛选轮廓并绘制边界框
        for (MatOfPoint contour : contours) {
            Rect rect = Imgproc.boundingRect(contour);
            Rectangle rectangle = new Rectangle(rect.x, rect.y, rect.width, rect.height);
            System.out.println(rectangle);
            rectangles.add(rectangle);
        
            // 根据需要，这里可以添加更多的筛选条件
            //if (rect.width > 30 && rect.height > 30) { // 简单的筛选条件
            //    Imgproc.rectangle(src, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0), 2);
            //}
            Imgproc.rectangle(src, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0), 2);
        }
        // 保存或显示结果
        Imgcodecs.imwrite("E:\\test\\book\\ocr\\0.jpg", src);

        ITesseract instance = new Tesseract();  // JNA I
        instance.setDatapath("C:\\Program Files\\Tesseract-OCR\\tessdata");  // tessdata路径
        instance.setLanguage("eng");
        for (Rectangle rectangle : rectangles) {
            String string = instance.doOCR(new File("E:\\test\\book\\ocr\\0.jpg"), rectangle);
            System.out.println("内容:" + string);
        }
    }


    public static void main(String[] args) throws TesseractException {
        detectTextRegion2("E:\\test\\book\\0.png");
        //ocrText();
    }



    public static void ocrText() throws TesseractException {
        ITesseract instance = new Tesseract();  // JNA I
        instance.setDatapath("C:\\Program Files\\Tesseract-OCR\\tessdata");  // tessdata路径
        instance.setLanguage("eng");
        String string = instance.doOCR(new File("E:\\test\\book\\eng.traineddata\\0.png"));
        System.out.println(string);
        System.out.println("为什么不提交??");
    }


}
