package priv.gsc.rpc.common.entity;

import lombok.Data;
import priv.gsc.rpc.common.enumeration.ResponseCode;

import java.io.Serializable;

/**
 * 服务端向客户端返回的响应对象
 * @param <T>
 */
@Data
public class RpcResponse<T> implements Serializable {

    /**
     * 状态码
     */
    private Integer statusCode;

    /**
     * 状态信息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 返回成功的静态方法
     * @param data
     * @param <T>
     * @return
     */
    public static <T> RpcResponse<T> success(T data) {
        RpcResponse<T> response = new RpcResponse<>();
        response.setStatusCode(ResponseCode.SUCCESS.getCode());
        response.setMessage(ResponseCode.SUCCESS.getMessage());
        response.setData(data);
        return response;
    }

    /**
     * 返回失败的静态方法
     * @param code
     * @param <T>
     * @return
     */
    public static <T> RpcResponse<T> fail(ResponseCode code) {
        RpcResponse<T> response = new RpcResponse<>();
        response.setStatusCode(code.getCode());
        response.setMessage(code.getMessage());
        return response;
    }
}
