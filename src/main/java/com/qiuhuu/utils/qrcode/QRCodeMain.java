package com.qiuhuu.utils.qrcode;

import com.qiuhuu.utils.qrcode.utils.QRCodeUtils;

/**
 * @Description: 二维码生成
 * @Author: qiuhuu
 * @Create: 2020-03-03 10:52
 */
public class QRCodeMain {
    public static void main(String[] args) throws Exception {
        // 存放在二维码中的内容
        String text = "csa";
        // 嵌入二维码的图片路径
        String imgPath = "E://Download/18107.jpg";
        // 生成的二维码的路径及名称
        String destPath = "E://";
        QRCodeUtils.encode(text,destPath);
    }
}
