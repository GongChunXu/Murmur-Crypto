package org.murmur.crypto;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.murmur.crypto.annotation.MurMurDecrypt;
import org.murmur.crypto.properties.MurMurCryptoProperties;
import org.murmur.crypto.utils.MurMurCryptoUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import javax.annotation.Resource;
import java.io.*;
import java.lang.reflect.Type;
import java.util.stream.Collectors;

/**
 * @description: 请求参数的解密
 * @date: 2024-03-10 13:55
 * @author: Gong
 * @version: 1.0
 */

@EnableConfigurationProperties(MurMurCryptoProperties.class)
@ControllerAdvice
@Slf4j
public class MurMurDecryptRequest extends RequestBodyAdviceAdapter {
    @Resource
    private MurMurCryptoProperties properties;

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return methodParameter.hasMethodAnnotation(MurMurDecrypt.class) || methodParameter.hasParameterAnnotation(MurMurDecrypt.class);
    }

    /**
     * 方法会在参数转换成具体的对象之前执行，先从流中加载到数据，
     * 然后对数据进行解密，解密完成后再重新构造 HttpInputMessage 对象返回
     */
    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        try {
            String content = new BufferedReader(new InputStreamReader(inputMessage.getBody())).lines().collect(Collectors.joining(System.lineSeparator()));
            JSONObject jsonObject = JSONUtil.parseObj(content);
            String body = MurMurCryptoUtils.decryptByRsa(jsonObject.getStr("body"), properties);
            body = body.replace("=", ":").replace(",", ", ");
            // JSON格式
            JSONObject obj = JSONUtil.parseObj(body);
            byte[] decrypt = obj.toString().getBytes();
            ByteArrayInputStream decryptByte = new ByteArrayInputStream(decrypt);
            return new HttpInputMessage() {
                @Override
                public InputStream getBody() throws IOException {
                    return decryptByte;
                }

                @Override
                public HttpHeaders getHeaders() {
                    return inputMessage.getHeaders();
                }
            };
        } catch (Exception e) {
            log.error(">>>>>>>MurMurCrypto: Decryption result processing exception!<<<<<<<", e);
        }
        return super.beforeBodyRead(inputMessage, parameter, targetType, converterType);

    }
}
