package cn.kevinwang.rpc.config.spring.bean;

import cn.hutool.json.JSONUtil;
import cn.kevinwang.rpc.config.ProviderConfig;
import cn.kevinwang.rpc.domain.LocalServerInfo;
import cn.kevinwang.rpc.domain.RpcProvideConfig;
import cn.kevinwang.rpc.registry.RedisRegistryCenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author wang
 * @create 2024-01-20-19:22
 */
public class ProviderBean extends ProviderConfig implements ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(ProviderBean.class);
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        RpcProvideConfig provideConfig = new RpcProvideConfig();
        provideConfig.setAlias(alias);
        provideConfig.setHost(LocalServerInfo.LOCAL_HOST);
        provideConfig.setPort(LocalServerInfo.LOCAL_PORT);
        provideConfig.setRef(ref);
        provideConfig.setNozzle(nozzle);

        long count = RedisRegistryCenter.registryProvider(nozzle, alias, JSONUtil.toJsonStr(provideConfig));

        logger.info("注册生产者信息：{} {} {}", nozzle, alias, count);
    }
}
