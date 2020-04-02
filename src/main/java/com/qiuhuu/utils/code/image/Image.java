package com.qiuhuu.utils.code.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * @Description: 生成静态验证码
 * @Author: qiuhuu
 * @Create: 2020-03-30 11:22
 */

public class Image {

    // 图片的宽度。
    private int width = 200;
    // 图片的高度。
    private int height = 70;
    // 验证码字符个数
    private int codeCount = 4;

    //验证码
    private String code = null;
    //图片流
    private BufferedImage bufferedImage;
    //数字和字母的组合
    private char[] codeSequence = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j',
            'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
            'x', 'y', 'z',
            '2', '3', '4', '5', '6', '7', '8', '9'
    };

    public Image() {
        drawRandomText();
    }

    public Image(int width, int height) {
        this.width = width;
        this.height = height;
        drawRandomText();
    }

    public Image(int width, int height, int codeCount) {
        this.width = width;
        this.height = height;
        this.codeCount = codeCount;
        drawRandomText();
    }

    private void drawRandomText() {

        bufferedImage =new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);

        Graphics2D graphics = (Graphics2D)bufferedImage.getGraphics();
        graphics.setColor(Color.WHITE);//验证码背景色
        graphics.fillRect(0, 0, width, height);//填充背景
        int fontsize = fontsize()+2;
        graphics.setFont(new Font("微软雅黑", Font.BOLD, fontsize));

        Random random = new Random();

        //设置图片中字体样式
        StringBuffer sBuffer = setFont(graphics, random,fontsize);

        //画干扰线
        drawInterferenceLine(graphics,random);

        //添加噪点
        addNoisyPoint(graphics,random);
        code = sBuffer.toString();
    }
    /**
     * 设置字体
     */
    private StringBuffer setFont(Graphics2D graphics,Random random,int fontSpacing){
        //旋转原点的 x 坐标
        int x = width/(codeCount+6);
        int y = (int) Math.round(height*0.68);

        String ch = "";
        StringBuffer sBuffer = new StringBuffer();
        for(int i = 0;i < codeCount;i++){
            graphics.setColor(getRandomColor());
            //设置字体旋转角度  //角度小于30度
            int degree = random.nextInt() % 30;
            ch = codeSequence[random.nextInt(codeSequence.length)] + "";
            sBuffer.append(ch);
            //正向旋转
            graphics.rotate(degree * Math.PI / 180, x, y);
            graphics.drawString(ch, x, y);

            //反向旋转
            graphics.rotate(-degree * Math.PI / 180, x, y);
            x += fontSpacing;
        }
        return sBuffer;
    }

    /**
     * 画干扰线
     * @param graphics
     * @param random
     */
    private void drawInterferenceLine(Graphics2D graphics,Random random){
        int lineCount = 4;
        for (int i = 0; i < lineCount; i++) {
            //x轴第一个点的位置
            int x1 = random.nextInt(width);
            //y轴第一个点的位置
            int y1 = random.nextInt(height);
            //x轴第二个点的位置
            int x2 = x1 + random.nextInt(width >> 1);
            //y轴第二个点的位置
            int y2 = y1 + random.nextInt(height >> 1);

            graphics.setColor(getRandomColor());
            graphics.drawLine(x1, y1, x2, y2);
        }
    }

    /**
     * 添加噪点
     * @param graphics
     * @param random
     */
    private void addNoisyPoint(Graphics2D graphics,Random random){
        int noisyPoint = 30;
        for(int i=0;i<noisyPoint;i++){

            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);

            graphics.setColor(getRandomColor());
            graphics.fillRect(x1, y1, 2,2);

        }
    }

    /**
     * 随机取色
     */
    private static Color getRandomColor() {
        Random ran = new Random();
        Color color = new Color(ran.nextInt(256),
                ran.nextInt(256), ran.nextInt(256));
        return color;
    }

    public void write(OutputStream outputStream) throws IOException {
        ImageIO.write(bufferedImage, "png", outputStream);
    }

    /**
     * 计算验证码 字体大小
     * @return
     */
    private int fontsize(){
        double d = 0.0;
        for (int i = 0; i < codeCount; i++) {
            d += 0.68;
        }
        double floor = Math.floor(Math.sqrt(width) * Math.sqrt(height) / d);
        return (int) Math.round(floor);
    }

    public String getCode() {
        return code;
    }
}
