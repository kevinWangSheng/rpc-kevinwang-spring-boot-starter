package cn.kevinwang.rpc.config.spring.bean;

import cn.kevinwang.rpc.config.ServerConfig;
import cn.kevinwang.rpc.domain.LocalServerInfo;
import cn.kevinwang.rpc.network.server.ServerSocket;
import cn.kevinwang.rpc.registry.RedisRegistryCenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author wang
 * @create 2024-01-20-17:44
 */
public class ServerBean extends ServerConfig implements ApplicationContextAware {
    private final Logger logger = LoggerFactory.getLogger(ServerBean.class);
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        logger.info("启动注册中心 ...");
        RedisRegistryCenter.init(host, port,password);
        logger.info("启动注册中心完成 {} {}", host, port);

        logger.info("开始初始化生产端服务 ...");
        ServerSocket serverSocket = new ServerSocket(applicationContext);
        while(serverSocket.isActiveSocketServer()){
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignore) {
            }
        }
        logger.info("初始化生产端服务完成 {} {}", LocalServerInfo.LOCAL_HOST, LocalServerInfo.LOCAL_PORT);
    }
}
