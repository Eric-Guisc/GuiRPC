package priv.gsc.test.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import priv.gsc.rpc.api.HelloObject;
import priv.gsc.rpc.api.HelloService;

/**
 * HelloService接口的实现类
 */
public class HelloServiceImpl implements HelloService {

    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);

    @Override
    public String hello(HelloObject object) {
        logger.info("接收到：{}", object.getMessage());
        return "调用返回值，id = " + object.getId() * 10;
    }
}
