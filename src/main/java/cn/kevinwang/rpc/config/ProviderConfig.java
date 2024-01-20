package cn.kevinwang.rpc.config;

/** 接口提供基础配置类
 * @author wang
 * @create 2024-01-20-16:36
 */
public class ProviderConfig {
    protected String nozzle; // 接口

    protected String alias; // 别名

    protected String ref; // 引用

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

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }
}
