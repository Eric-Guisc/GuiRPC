package priv.gsc.rpc.core.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import priv.gsc.rpc.common.entity.RpcRequest;
import priv.gsc.rpc.common.entity.RpcResponse;
import priv.gsc.rpc.core.registry.ServiceRegistry;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 处理RpcRequest的工作线程
 * 用于接收RpcRequest对象，并交由RequestHandler处理，生成RpcResponse对象并传输给客户端
 */
public class RequestHandlerThread implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandlerThread.class);

    /**
     * 用于传输RpcRequest、RpcResponse的socket
     */
    private Socket socket;

    /**
     * 处理逻辑的具体类
     */
    private RequestHandler requestHandler;

    /**
     * 注册好服务的服务注册表
     */
    private ServiceRegistry serviceRegistry;

    public RequestHandlerThread(Socket socket, RequestHandler requestHandler, ServiceRegistry serviceRegistry) {
        this.socket = socket;
        this.requestHandler = requestHandler;
        this.serviceRegistry = serviceRegistry;
    }

    @Override
    public void run() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())) {
            RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();
            String interfaceName = rpcRequest.getInterfaceName();       // 获取传递的RpcRequest中的接口名
            Object service = serviceRegistry.getService(interfaceName); // 根据接口名得到提供服务的对象
            Object result = requestHandler.handle(rpcRequest, service); // 具体处理逻辑，交由RequestHandler处理
            objectOutputStream.writeObject(RpcResponse.success(result));
            objectOutputStream.flush();
        } catch (IOException | ClassNotFoundException e) {
            logger.error("调用或发送时有错误发送：", e);
        }
    }
}
