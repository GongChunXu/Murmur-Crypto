package org.murmur.crypto.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description: 密文属性配置类
 * @date: 2024-03-10 11:31
 * @author: Gong
 * @version: 1.0
 */

@Data
@ConfigurationProperties(prefix = "murmur-crypto")
public class MurMurCryptoProperties {
    /**
     * 加密开关
     */
    private Boolean enabled;

    /**
     * 响应加密公钥
     */
    private String publicKey;

    /**
     * 请求解密私钥
     */
    private String privateKey;
    /**
     * 是否打印日志
     */
    private boolean log = false;
}
