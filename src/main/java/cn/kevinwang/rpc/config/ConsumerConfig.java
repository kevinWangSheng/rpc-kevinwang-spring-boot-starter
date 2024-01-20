package cn.kevinwang.rpc.config;

/** 接口消费基础配置类
 * @author wang
 * @create 2024-01-20-16:35
 */
public class ConsumerConfig {
    protected String nozzle; // 接口

    protected String alias; // 别名

    public String getNozzle() {
        return nozzle;
    }

    public void setNozzle(String nozzle) {
        this.nozzle = nozzle;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
