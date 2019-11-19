package com.frame.common.utils;

import org.apache.tomcat.util.codec.binary.Base64;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 验签工具类
 */
public class SignatureHelper {
    
    private static SignatureHelper instance = null;
    
    public static String thirdPublicKey;
    public static String welabPrivateKey;
    
    /**
     * 我方私钥
     */
    private static RSAPrivateKey s_privateKey;
    
    /**
     * 对方公钥
     */
    private RSAPublicKey m_publicKey;
    
    public static SignatureHelper getInstance(String thirdPublicKey, String welabPrivateKey) {
        SignatureHelper.thirdPublicKey = thirdPublicKey;
        SignatureHelper.welabPrivateKey = welabPrivateKey;
        if (instance == null) {
            instance = new SignatureHelper();
        }
        return instance;
    }
    
    private SignatureHelper() {
        try {
            loadPublicKey(thirdPublicKey);
            loadPrivateKey(welabPrivateKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 从字符串中加载公钥
     *
     * @param publicKeyStr 公钥数据字符串
     * @throws Exception 加载公钥时产生的异常
     */
    private void loadPublicKey(String publicKeyStr) throws Exception {
        try {
            byte[] buffer = Base64.decodeBase64(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            m_publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        }
    }
    
    
    /**
     * 从字符串中加载私钥
     *
     * @param privateKeyStr 私钥数据字符串
     * @throws Exception 加载私钥时产生的异常
     */
    private void loadPrivateKey(String privateKeyStr) throws Exception {
        try {
            byte[] buffer = Base64.decodeBase64(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            s_privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("私钥非法");
        } catch (NullPointerException e) {
            throw new Exception("私钥数据为空");
        }
    }
    
    /**
     * 数据验证
     *
     * @param oriData 原始数据
     * @param encryptData 加密后数据
     * @return 对比结果
     */
    public boolean verify(String oriData, String encryptData) throws Exception {
        if (m_publicKey == null) {
            throw new Exception("加密公钥为空, 请设置");
        }
        try {
            Signature sign = Signature.getInstance("SHA1withRSA");
            sign.initVerify(m_publicKey);
            sign.update(oriData.getBytes("UTF-8"));
            return sign.verify(Base64.decodeBase64(encryptData));
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此加密算法");
        } catch (InvalidKeyException e) {
            throw new Exception("加密公钥非法,请检查");
        }
    }
    
    /**
     * 数据签名
     *
     * @param oriStr 原始数据
     * @return 签名结果
     */
    public String sign(String oriStr) throws Exception {
        
        if (s_privateKey == null) {
            throw new Exception("解密私钥为空, 请设置");
        }
        
        try {
            Signature sign = Signature.getInstance("SHA1withRSA");
            sign.initSign(s_privateKey);
            sign.update(oriStr.getBytes("UTF-8"));
            return Base64.encodeBase64String(sign.sign());
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此解密算法");
        } catch (InvalidKeyException e) {
            throw new Exception("解密私钥非法,请检查");
        }
    }
    
}
