package org.murmur.crypto.utils;

import lombok.Builder;
import lombok.Data;

/**
 * @description: MurMurResult
 * @date: 2024-03-11 20:50
 * @author: Gong
 * @version: 1.0
 */
@Data
@Builder
public class MurMurResult {
    private int code;
    private String msg;
    private Object data;

    public static MurMurResult ok() {
        return MurMurResult.builder().code(200).msg("Success").build();
    }

    public static MurMurResult ok(Object data) {
        return MurMurResult.builder().code(200).msg("Success").data(data).build();
    }

    public static MurMurResult ok(int code, Object data) {
        return MurMurResult.builder().code(code).msg("Success").data(data).build();
    }

    public static MurMurResult fail() {
        return MurMurResult.builder().code(500).msg("fail").build();
    }

    public static MurMurResult fail(int code, String msg) {
        return MurMurResult.builder().code(code).msg(msg).build();
    }


    public static MurMurResult fail(Object data) {
        return MurMurResult.builder().code(500).msg("fail").data(data).build();
    }

}
