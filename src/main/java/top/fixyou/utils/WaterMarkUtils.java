package top.fixyou.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

/**
 * @author Lsk
 */
public class WaterMarkUtils {
    public static void addTextWaterMark(File file, Color textColor, int fontSize, String text, String outPath) {
        try {
            BufferedImage targetImg = ImageIO.read(file);
            //图片宽
            int width = targetImg.getWidth();
            //图片高
            int height = targetImg.getHeight();
            BufferedImage bufferedImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_BGR);
            Graphics2D g = bufferedImage.createGraphics();
            g.drawImage(targetImg, 0, 0, width, height, null);
            //水印颜色
            g.setColor(textColor);
            g.setFont(new Font("微软雅黑", Font.ITALIC, fontSize));
            // 水印内容放置在右下角
            int x = width - (text.length() + 1) * fontSize;
            int y = height- fontSize * 2;
            g.drawString(text, x, y);
            FileOutputStream outImgStream = new FileOutputStream(outPath);
            ImageIO.write(bufferedImage, "jpg", outImgStream);
            outImgStream.flush();
            outImgStream.close();
            g.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
