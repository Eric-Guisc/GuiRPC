package priv.gsc.test.server;

import priv.gsc.rpc.api.ByeService;
import priv.gsc.rpc.api.HelloService;
import priv.gsc.rpc.core.registry.DefaultServiceRegistry;
import priv.gsc.rpc.core.registry.ServiceRegistry;
import priv.gsc.rpc.core.server.RpcServer;

public class TestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ByeService byeService = new ByeServiceImpl();
        ServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        serviceRegistry.register(helloService);
        serviceRegistry.register(byeService);
        RpcServer rpcServer = new RpcServer(serviceRegistry);
        rpcServer.start(9000); // 启动服务端
    }
}
