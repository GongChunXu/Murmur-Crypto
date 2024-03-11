package org.murmur.crypto.annotation;

import java.lang.annotation.*;

/**
 * @description: 加密注解
 * @date: 2024-03-10 12:14
 * @author: Gong
 * @version: 1.0
 */

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MurMurEncrypt {
    /**
     * 响应加默认不加密，为 true 加密
     */
    boolean response() default false;
}
