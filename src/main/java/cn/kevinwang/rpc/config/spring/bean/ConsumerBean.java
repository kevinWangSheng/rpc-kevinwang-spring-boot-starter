package cn.kevinwang.rpc.config.spring.bean;

import cn.hutool.json.JSONUtil;
import cn.kevinwang.rpc.config.ConsumerConfig;
import cn.kevinwang.rpc.domain.RpcProvideConfig;
import cn.kevinwang.rpc.network.client.SocketClient;
import cn.kevinwang.rpc.network.msg.Request;
import cn.kevinwang.rpc.reflect.JDKProxy;
import cn.kevinwang.rpc.registry.RedisRegistryCenter;
import cn.kevinwang.rpc.util.ClassLoaderUtil;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author wang
 * @create 2024-01-20-19:27
 */
public class ConsumerBean<T> extends ConsumerConfig implements FactoryBean {
    private static final Logger logger = LoggerFactory.getLogger(ConsumerBean.class);

    private RpcProvideConfig provideConfig;

    private ChannelFuture future;

    @Override
    public Object getObject() throws Exception {
        // 从注册中心获取生产者(接口)信息
        if(null == provideConfig){
            String providerJsonStr = RedisRegistryCenter.obtainProvider(nozzle, alias);
            provideConfig = JSONUtil.toBean(providerJsonStr, RpcProvideConfig.class);
        }

        // 获取通信channel
        if(null == future) {
            SocketClient client = new SocketClient();
            new Thread(client).start();
            for(int i = 0;i<10;i++) {
                if (null != future) {
                    break;
                }
                Thread.sleep(500);
                future = client.getFuture();
            }
        }
        if(null == future){
            throw new RuntimeException("netty客户端获取通信channel失败");
        }

        Request request = new Request();
        request.setAlias(alias);
        request.setNozzle(nozzle);
        request.setRef(provideConfig.getRef());
        request.setChannel(future.channel());

        return (T)JDKProxy.getProxy(ClassLoaderUtil.forName(nozzle),request);
    }

    @Override
    public Class<?> getObjectType() {
        try {
            return ClassLoaderUtil.forName(nozzle);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
