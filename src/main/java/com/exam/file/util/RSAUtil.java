package com.exam.file.util;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @description RSA签名工具类
 *
 */
public class RSAUtil {

    private final static String RSA = "RSA";

    private final static String MD5_WITH_RSA = "MD5withRSA";

    /**
     * 执行签名
     *
     * @param rsaPrivateKey  私钥
     * @param toSignStr  参数内容
     * @return  签名后的内容，base64后的字符串
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static String executeSignature(String rsaPrivateKey, String toSignStr) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {
        // base64解码私钥
        byte[] decodePrivateKey = Base64.getDecoder().decode(rsaPrivateKey.replace("\r\n", ""));

        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(decodePrivateKey);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Signature signature = Signature.getInstance(MD5_WITH_RSA);
        signature.initSign(privateKey);
        signature.update(toSignStr.getBytes());

        // 生成签名
        byte[] result = signature.sign();

        // base64编码签名为字符串
        return Base64.getEncoder().encodeToString(result);
    }

    /**
     * 验证签名
     *
     * @param rsaPublicKey  公钥
     * @param sign  签名
     * @return  验证结果
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static boolean verifySignature(String rsaPublicKey, String sign,String data) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {
        // base64解码公钥
        byte[] decodePublicKey = Base64.getDecoder().decode(rsaPublicKey.replace("\r\n", ""));

        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(decodePublicKey);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        Signature signature = Signature.getInstance(MD5_WITH_RSA);
        signature.initVerify(publicKey);
        signature.update(data.getBytes());
        // base64解码签名为字节数组
        byte[] decodeSign = Base64.getDecoder().decode(sign);

        // 验证签名
        return signature.verify(decodeSign);
    }
}