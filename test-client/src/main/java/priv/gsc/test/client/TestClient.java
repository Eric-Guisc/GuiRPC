package priv.gsc.test.client;

import priv.gsc.rpc.api.HelloObject;
import priv.gsc.rpc.api.HelloService;
import priv.gsc.rpc.core.client.RpcClientProxy;

public class TestClient {
    public static void main(String[] args) {
        RpcClientProxy proxy = new RpcClientProxy("127.0.0.1", 9000);   // 连接上服务端（host、port）
        HelloService helloService = proxy.getProxy(HelloService.class);            // 为HelloService生成动态代理类实例
        HelloObject helloObject = new HelloObject(7, "Hello, this is a message for testing.");
        String res = helloService.hello(helloObject);   // 在动态代理类中发送了RpcRequest、接收到了RpcResponse.getData()
        System.out.println(res);
    }
}
