package cn.kevinwang.rpc.annotation;

import cn.kevinwang.rpc.config.ServerAutoConfigure;
import cn.kevinwang.rpc.config.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wang
 * @create 2024-01-20-20:34
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Import(ServerAutoConfigure.class)
@EnableConfigurationProperties(ServerProperties.class)
@ComponentScan("cn.kevinwang.rpc.*")
public @interface EnableRpc {
}
