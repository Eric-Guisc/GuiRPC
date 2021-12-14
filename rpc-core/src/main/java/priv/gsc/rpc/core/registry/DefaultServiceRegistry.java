package priv.gsc.rpc.core.registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import priv.gsc.rpc.common.enumeration.RpcError;
import priv.gsc.rpc.common.exception.RpcException;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 注册服务类
 */
public class DefaultServiceRegistry implements ServiceRegistry {

    private static final Logger logger = LoggerFactory.getLogger(DefaultServiceRegistry.class);

    /**
     * 保存服务名与提供服务的对象之间的映射关系
     * 服务名：接口的完整类名
     * 使用Map存储，说明一个接口，只能有一个对象提供服务
     */
    private final Map<String, Object> serviceMap = new ConcurrentHashMap<>();

    /**
     * 保存已经提供服务的对象的服务名
     */
    private final Set<String> registeredService = ConcurrentHashMap.newKeySet();

    /**
     * 根据提供服务对象，注册该对象实现接口与该对象之间的映射关系
     * @param service
     * @param <T>
     */
    @Override
    public synchronized  <T> void register(T service) {
        // 先根据提供服务的对象获取服务名
        String serviceName = service.getClass().getCanonicalName();

        if (registeredService.contains(serviceName))
            return;
        registeredService.add(serviceName);

        // 根据提供服务的对象实现的接口，注册服务
        Class<?>[] interfaces = service.getClass().getInterfaces();
        if (interfaces.length == 0)
            throw new RpcException(RpcError.SERVICE_NOT_IMPLEMENT_ANY_INTERFACE);
        for (Class<?> i : interfaces) {
            serviceMap.put(i.getCanonicalName(), service);
        }

        logger.info("向接口：{} 注册服务：{}", interfaces, serviceName);
    }

    /**
     * 根据服务名（接口的完整类名），找到对应的提供服务的对象
     * @param serviceName
     * @return
     */
    @Override
    public synchronized Object getService(String serviceName) {
        Object service = serviceMap.get(serviceName);
        if (service == null)
            throw new RpcException(RpcError.SERVICE_NOT_FOUND);
        return service;
    }
}
