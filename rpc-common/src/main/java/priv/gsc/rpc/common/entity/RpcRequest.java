package priv.gsc.rpc.common.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 客户端向服务端发送的请求对象
 */
@Data
@Builder
public class RpcRequest implements Serializable {

    /**
     * 接口名称
     */
    private String interfaceName;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 实参
     */
    private Object[] parameters;

    /**
     * 参数类型
     */
    private Class<?>[] paramTypes;
}
