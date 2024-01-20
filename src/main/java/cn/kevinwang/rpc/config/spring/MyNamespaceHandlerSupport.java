package cn.kevinwang.rpc.config.spring;

import cn.kevinwang.rpc.config.spring.bean.ConsumerBean;
import cn.kevinwang.rpc.config.spring.bean.ProviderBean;
import org.apache.zookeeper.server.quorum.ServerBean;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author wang
 * @create 2024-01-20-17:43
 */
public class MyNamespaceHandlerSupport extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("server", new MyBeanDefinitionParser(ServerBean.class));
        registerBeanDefinitionParser("provider", new MyBeanDefinitionParser(ProviderBean.class));
        registerBeanDefinitionParser("consumer", new MyBeanDefinitionParser(ConsumerBean.class));
    }
}
