package cn.kevinwang.rpc.domain;

/**
 * @author wang
 * @create 2024-01-20-16:49
 */
public class RpcProvideConfig {
    private String nozzle; // 接口

    private String ref; // 引用

    private String alias; // 别名

    private String host; // ip

    private int port; // port

    public String getNozzle() {
        return nozzle;
    }

    public void setNozzle(String nozzle) {
        this.nozzle = nozzle;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

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
}
