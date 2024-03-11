package org.murmur.crypto.annotation;

import java.lang.annotation.*;

/**
 * @description: 解密注解
 * @date: 2024-03-10 12:14
 * @author: Gong
 * @version: 1.0
 */

@Documented
@Target({ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface MurMurDecrypt {

}
