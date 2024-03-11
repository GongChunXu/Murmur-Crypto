package org.murmur.crypto;

import lombok.extern.slf4j.Slf4j;
import org.murmur.crypto.annotation.MurMurEncrypt;
import org.murmur.crypto.properties.MurMurCryptoProperties;
import org.murmur.crypto.utils.MurMurCryptoUtils;
import org.murmur.crypto.utils.MurMurResult;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Resource;

/**
 * @description: 响应结果加密
 * @date: 2024-03-10 11:42
 * @author: Gong
 * @version: 1.0
 */

@EnableConfigurationProperties(MurMurCryptoProperties.class)
@ControllerAdvice
@Slf4j
public class MurMurEncryptResponse implements ResponseBodyAdvice<MurMurResult> {
    @Resource
    private MurMurCryptoProperties properties;

    /**
     * 是否含有 @MurMurEncrypt 注解
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType.hasMethodAnnotation(MurMurEncrypt.class);
    }

    @Override
    public MurMurResult beforeBodyWrite(MurMurResult body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        try {
            // 消息体message
//                if (body.getMsg() != null) {
//                    String msg = MurMurCryptoUtils.encryptByRsaBase64(body.getMsg(), properties);
//                    body.setMsg(msg);
//                }
            if (body.getData() != null) {
                String data = MurMurCryptoUtils.encryptByRsaBase64(body.getData(), properties);
                body.setData(data);
            }

        } catch (Exception e) {
            log.error(">>>>>>>MurMurCrypto: Encryption result processing exception!<<<<<<<", e);
        }
        return body;
    }

}
