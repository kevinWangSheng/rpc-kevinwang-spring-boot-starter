package cn.kevinwang.rpc.config;

import cn.kevinwang.rpc.domain.LocalServerInfo;
import cn.kevinwang.rpc.network.server.ServerSocket;
import cn.kevinwang.rpc.registry.RedisRegistryCenter;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;



/**
 * @author wang
 * @create 2024-01-20-16:40
 */

public class ServerAutoConfigure implements ApplicationContextAware {
    private final Logger logger = LoggerFactory.getLogger(ServerAutoConfigure.class);

    @Resource
    private ServerProperties serverProperties;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        logger.info("启动Redis模拟注册中心开始");
        RedisRegistryCenter.init(serverProperties.getHost(), serverProperties.getPort(),serverProperties.getPassword());
        logger.info("启动Redis模拟注册中心完成，{} {}", serverProperties.getHost(), serverProperties.getPort());

        logger.info("初始化生产端服务开始");
        ServerSocket serverSocket = new ServerSocket(applicationContext);
        Thread thread = new Thread(serverSocket);
        thread.start();
        while (!serverSocket.isActiveSocketServer()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignore) {
            }
        }
        logger.info("初始化生产端服务完成 {} {}", LocalServerInfo.LOCAL_HOST, LocalServerInfo.LOCAL_PORT);
    }
}
