package priv.gsc.test.server;

import priv.gsc.rpc.api.HelloService;
import priv.gsc.rpc.core.server.RpcServer;

public class TestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        RpcServer rpcServer = new RpcServer();
        rpcServer.register(helloService, 9000); // 注册接口服务，并指定端口号
    }
}
