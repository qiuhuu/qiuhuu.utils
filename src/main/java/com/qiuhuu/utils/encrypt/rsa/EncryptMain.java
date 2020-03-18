package com.qiuhuu.utils.encrypt.rsa;

import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: qiuhuu
 * @Create: 2020-03-18 11:26
 */
@Component
public class EncryptMain {


    public static void main(String[] args) {
        Long startTime = System.currentTimeMillis();
        String password = "123";

        RSAUtils rsaUtils = new RSAUtils();
        try {
            System.out.println("原内容："+password);

            long startEncryptTime = System.currentTimeMillis();
            String encrypt = rsaUtils.encrypt(password);
            System.out.println("加密后内容："+encrypt);
            System.out.println("加密耗时："+(System.currentTimeMillis()-startEncryptTime));

            // RSA解密
            long startDecryptTime = System.currentTimeMillis();
            String decryptData = rsaUtils.decrypt(encrypt);
            System.out.println("解密后内容:" + decryptData);
            System.out.println("解密耗时："+(System.currentTimeMillis()-startDecryptTime));


            // RSA签名
            long startSignTime = System.currentTimeMillis();
            String sign = rsaUtils.sign(encrypt);
            System.out.println("签名耗时："+(System.currentTimeMillis()-startSignTime));

            // RSA验签
            long startVerifyTime = System.currentTimeMillis();
            boolean result = rsaUtils.verify(encrypt, sign);
            System.out.println("验签耗时："+(System.currentTimeMillis()-startVerifyTime));
            System.out.println("验签结果:" + result);
        } catch (Exception e) {
            System.out.print("加解密异常");
            e.printStackTrace();
        }
        Long endTime = System.currentTimeMillis();
        System.out.println("用时："+(endTime-startTime));
    }
}
