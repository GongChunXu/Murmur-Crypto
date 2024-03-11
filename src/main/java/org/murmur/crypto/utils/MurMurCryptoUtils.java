package org.murmur.crypto.utils;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import lombok.extern.slf4j.Slf4j;
import org.murmur.crypto.properties.MurMurCryptoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.util.StopWatch;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 加解密工具类
 * @date: 2024-03-10 13:31
 * @author: Gong
 * @version: 1.0
 */
@EnableConfigurationProperties(MurMurCryptoProperties.class)
@Slf4j
public class MurMurCryptoUtils {
    public static final String PUBLIC_KEY = "publicKey";
    public static final String PRIVATE_KEY = "privateKey";

    /**
     * 生成RSA公私钥对Base64
     */
    public static Map<String, String> generateRsaBase64() {
        Map<String, String> keyMap = new HashMap<>(2);
        RSA rsa = new RSA();
        keyMap.put(PRIVATE_KEY, rsa.getPrivateKeyBase64());
        keyMap.put(PUBLIC_KEY, rsa.getPublicKeyBase64());
        return keyMap;
    }

    /**
     * RSA公钥加密: Base64编码
     */
    public static String encryptByRsaBase64(String data, MurMurCryptoProperties pro) {
        if (StrUtil.isBlank(pro.getPublicKey())) {
            throw new IllegalArgumentException(">>>>>>>MurMurCrypto: Require passing in public key for encryption!<<<<<<<");
        }
        StopWatch sw = new StopWatch();
        sw.start("【Encryption Task(Base64)】");
        RSA rsa = SecureUtil.rsa(null, pro.getPublicKey());
        String encryptData = rsa.encryptBase64(data, CharsetUtil.CHARSET_UTF_8, KeyType.PublicKey);
        sw.stop();
        log.info(">>>>>>>MurMurCrypto: Encryption statistics {}<<<<<<<", sw.prettyPrint());
        log.info(">>>>>>>MurMurCrypto: Time:{} Second!<<<<<<<", sw.getTotalTimeSeconds());
        if (pro.isLog()) {
            log.info(">>>>>>>MurMurEncrypt: Data encryption successful! Before Msg:{}, After Msg:{}<<<<<<<", data, encryptData);
        }
        return encryptData;
    }

    public static String encryptByRsaBase64(Object obj, MurMurCryptoProperties pro) {
        if (StrUtil.isBlank(pro.getPublicKey())) {
            log.error(">>>>>>>MurMurCrypto: Require passing in public key for encryption!<<<<<<<");
        }
        StopWatch sw = new StopWatch();
        sw.start("【Encryption Task】");
        RSA rsa = SecureUtil.rsa(null, pro.getPublicKey());
        String encryptObj = rsa.encryptBase64(obj.toString(), CharsetUtil.CHARSET_UTF_8, KeyType.PublicKey);
        sw.stop();
        log.info(">>>>>>>MurMurCrypto:Encryption statistics {}<<<<<<<", sw.prettyPrint());
        log.info(">>>>>>>MurMurCrypto: Time:{} Second!<<<<<<<", sw.getTotalTimeSeconds());
        if (pro.isLog()) {
            log.info(">>>>>>>MurMurCrypto: Data encryption successful! Before Data:{}, After Data:{}<<<<<<<", obj, encryptObj);
        }
        return encryptObj;
    }

    /**
     * RSA私钥解密
     */
    public static String decryptByRsa(String data, MurMurCryptoProperties pro) {
        if (StrUtil.isBlank(pro.getPrivateKey())) {
            log.error(">>>>>>>MurMurCrypto:Private key needs to be passed in for decryption!<<<<<<<");
        }
        StopWatch sw = new StopWatch();
        sw.start("【Decryption Task】");
        RSA rsa = SecureUtil.rsa(pro.getPrivateKey(), null);
        String decryptData = rsa.decryptStr(data, KeyType.PrivateKey, CharsetUtil.CHARSET_UTF_8);
        sw.stop();
        log.info(">>>>>>>MurMurCrypto: Decryption statistics {}<<<<<<<", sw.prettyPrint());
        log.info(">>>>>>>MurMurCrypto: Time:{} Second!<<<<<<<", sw.getTotalTimeSeconds());
        if (pro.isLog()) {
            log.info(">>>>>>>MurMurCrypto: Data decryption successful! Before Data:{}, After Data:{}<<<<<<<", data, decryptData);
        }
        return decryptData;
    }
}
