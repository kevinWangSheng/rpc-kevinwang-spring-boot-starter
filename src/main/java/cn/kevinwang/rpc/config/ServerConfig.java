package cn.kevinwang.rpc.config;

/**
 * @author wang
 * @create 2024-01-20-16:38
 */
public class ServerConfig {
    protected String host; // redis server address ip

    protected int port; // redis port

    protected String password; // password

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
