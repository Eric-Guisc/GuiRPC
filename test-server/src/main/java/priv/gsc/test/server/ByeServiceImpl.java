package priv.gsc.test.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import priv.gsc.rpc.api.ByeService;
import priv.gsc.rpc.api.HelloObject;

public class ByeServiceImpl implements ByeService {

    private static final Logger logger = LoggerFactory.getLogger(ByeServiceImpl.class);

    @Override
    public String bye(HelloObject object) {
        logger.info("接收到：{}", object.getMessage());
        return "返回值，id = " + object.getId();
    }
}
