package cn.kevinwang.rpc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author wang
 * @create 2024-01-20-16:37
 */
@ConfigurationProperties(prefix = "cn.kevinwang.rpc.server")
public class ServerProperties {

    private String host; // 这里使用redis作为注册中心，所以这里是redis的host

    private int port; // redis port

    private String password; // redis password

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
