package com.qiuhuu.utils.encrypt.rsa;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @Description: RSA加密方法
 * @Author: qiuhuu
 * @Create: 2020-03-17 17:21
 */
public class RSAUtils {

    /**
     * 私钥
     */
    private static final String PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALJCmzkh10Bvqmu+/p+vYt//xPSQ8P8o1CWEwwATlAhOwC1b/IbukOMjFVvBisI44EbeMDABDo4e8orK8EiNOtDa6MGAtpQcfDOp13pJl7ErhcxJ15QM0xxCblw/DfQ+C7d9nU2JJ8XRsFyVkviXr6yNN5vOZWdSUOA89pccMDmfAgMBAAECgYBf/F+Kxv8Z8kbo16JRp+XsydNrAXTTYE/DmBZMPz6Rrp3qjsKOgX1A4Q5KJxApfui9ID0ATEFjI6TkOVxjJLELCJYVppcn0IONiuSCkafW4OZqcFnA7Lq7q8RazfckE5Y5KuVsgR2GvyWGyKC6tY3AylIXZGZksAkolr2OqV4OaQJBAOQxnBjjzhFOvp9bmRuA9mX90swmP7V9KlwYQzzs4UnJeFf+UwKokZC0R+MbclX21GpQQGt2LW58ynkOGfzpMJUCQQDH+1fGbd6SuRxnVRglMv78if0kQl1IRVYz14xMyNL6zNCAkKBkmquTi3xtLTGNbsZGh+fmfK+eRkdt9QdXLLBjAkBgCp03QJHXaPwD2Cp9j+6kiIrzD1obCZ4Nesy51L0RMDFtX4YShV4WuDnVVRTvJ17JjBNA51FBwlX5Uz0O3BptAkEAwOMhKndWstZxGwnsnBSv9Q1zaMHKD6cxGhfRb05UkSt0AE1/Jwo4a7uia/unS++ItwbnGl0xVswaoWgxiFfGWQJBAN+ePI40L7SH9CyLd0UsgOS2ZJIDHobhDCk3x6rQzVaL4wb2BNM542KcrEuVbXRCUb/BQ+Df6AMkxgjO3NgDKKs=";

    /**
     * 公钥
     */
    private static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCyQps5IddAb6prvv6fr2Lf/8T0kPD/KNQlhMMAE5QITsAtW/yG7pDjIxVbwYrCOOBG3jAwAQ6OHvKKyvBIjTrQ2ujBgLaUHHwzqdd6SZexK4XMSdeUDNMcQm5cPw30Pgu3fZ1NiSfF0bBclZL4l6+sjTebzmVnUlDgPPaXHDA5nwIDAQAB";

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * 获取密钥对
     *
     * @return 密钥对
     */
    private KeyPair getKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(1024);
        return generator.generateKeyPair();
    }

    /**
     * 获取私钥
     *
     * @return private key
     */
    private PrivateKey getPrivateKey() throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] decodedKey = Base64.decodeBase64(PRIVATE_KEY.getBytes());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * 获取公钥
     *
     * @return public key
     */
    private PublicKey getPublicKey() throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] decodedKey = Base64.decodeBase64(PUBLIC_KEY.getBytes());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * RSA加密
     *
     * @param data 待加密数据
     * @return
     */
    public String encrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, getPublicKey());
        //字符加密
        byte[] encryptedData = byteCodec(data.getBytes(), cipher);
        // 获取加密内容使用base64进行编码,并以UTF-8为标准转化成字符串
        // 加密后的字符串
        return new String(Base64.encodeBase64String(encryptedData));
    }

    /**
     * RSA解密
     *
     * @param data 待解密数据
     * @return
     */
    public String decrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, getPrivateKey());
        byte[] dataBytes = Base64.decodeBase64(data);
        //字符解密
        byte[] decryptedData = byteCodec(dataBytes, cipher);
        // 解密后的内容
        return new String(decryptedData, "UTF-8");
    }

    /**
     * 根据  私钥签名
     *
     * @param data 待签名数据
     * @return 签名
     */
    public String sign(String data) throws Exception {
        //获取私钥编码
        byte[] keyBytes = getPrivateKey().getEncoded();
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey key = keyFactory.generatePrivate(keySpec);
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(key);
        signature.update(data.getBytes());
        return new String(Base64.encodeBase64(signature.sign()));
    }

    /**
     * 验签
     *
     * @param srcData 原始字符串
     * @param sign 签名
     * @return 是否验签通过
     */
    public boolean verify(String srcData, String sign) throws Exception {
        byte[] keyBytes = getPublicKey().getEncoded();
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey key = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(key);
        signature.update(srcData.getBytes());
        return signature.verify(Base64.decodeBase64(sign.getBytes()));
    }

    /**
     * 编解码器
     * @param data  编解码字符集
     * @param cipher
     * @return
     * @throws Exception
     */
    private byte[] byteCodec(byte[] data, Cipher cipher) throws Exception {
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offset > 0) {
            if (inputLen - offset > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(data, offset, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offset, inputLen - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    public static void main(String[] args) {

        try {
            // 生成密钥对
            KeyPair keyPair = new RSAUtils().getKeyPair();
            String privateKey = new String(Base64.encodeBase64(keyPair.getPrivate().getEncoded()));
            String publicKey = new String(Base64.encodeBase64(keyPair.getPublic().getEncoded()));
            System.out.println("私钥:" + privateKey);
            System.out.println("公钥:" + publicKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
