package priv.gsc.rpc.core.client;

import priv.gsc.rpc.common.entity.RpcRequest;
import priv.gsc.rpc.common.entity.RpcResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 使用JDK动态代理实现的客户端代理类
 */
public class RpcClientProxy implements InvocationHandler {

    /**
     * 服务端的IP地址
     */
    private String host;

    /**
     * 服务端的端口号
     */
    private int port;

    public RpcClientProxy(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * 用于为指定类加载器、一组接口及调用处理器生成动态代理类实例
     * @param clazz
     * @param <T>
     * @return clazz类对象实例
     */
    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }


    /**
     * 指明代理对象的方法（目前仅有一个方法）被调用时的动作
     * 生成一个RpcRequest对象 并发送出去，然后从服务端接收返回的结果
     * @param proxy
     * @param method
     * @param args
     * @return RpcResponse对象的data
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 使用RpcRequest的Builder模式生成RpcRequest对象
        RpcRequest rpcRequest = RpcRequest.builder()
                .interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameters(args)
                .paramTypes(method.getParameterTypes())
                .build();
        // 通过RpcClient类的sendRequest()方法，发送RpcRequest对象、接收RpcResponse对象
        RpcClient rpcClient = new RpcClient();
        return ((RpcResponse) rpcClient.sendRequest(rpcRequest, host, port)).getData();
    }
}
