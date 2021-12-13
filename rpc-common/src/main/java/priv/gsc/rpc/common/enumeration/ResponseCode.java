package priv.gsc.rpc.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 响应状态码 枚举类
 */
@Getter
@AllArgsConstructor
public enum ResponseCode {

    SUCCESS(200, "调用方法成功"),
    FAIL(500, "调用方法失败"),
    NOT_FOUND_CLASS(501, "未找到指定类"),
    NOT_FOUND_METHOD(502, "未找到指定方法");

    private final int code;
    private final String message;
}
