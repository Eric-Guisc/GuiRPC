package priv.gsc.rpc.core.registry;

/**
 * 注册服务接口
 */
public interface ServiceRegistry {
    <T> void register(T service);
    Object getService(String serviceName);
}
