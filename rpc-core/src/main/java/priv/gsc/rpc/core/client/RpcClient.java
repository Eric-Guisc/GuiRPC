package priv.gsc.rpc.core.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import priv.gsc.rpc.common.entity.RpcRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * RPC客户端，负责发送RpcRequest对象、接收RpcResponse对象
 */
public class RpcClient {

    private static final Logger logger = LoggerFactory.getLogger(RpcClient.class);

    /**
     * 基于JDK原生序列化与Socket通讯的发送RpcRequest、接收RpcResponse方法
     * @param rpcRequest
     * @param host
     * @param port
     * @return
     */
    public Object sendRequest(RpcRequest rpcRequest, String host, int port) {
        try (Socket socket = new Socket(host, port)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream.writeObject(rpcRequest); // 输出流写入RpcRequest对象
            objectOutputStream.flush();
            return objectInputStream.readObject();      // 从输入流读取RpcResponse对象
        } catch (IOException | ClassNotFoundException e) {
            logger.error("sendRequest/readResponse时有错误发生：", e);
            return null;
        }
    }
}
