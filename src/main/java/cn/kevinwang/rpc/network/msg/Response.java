package cn.kevinwang.rpc.network.msg;

import io.netty.channel.Channel;

/**
 * @author wang
 * @create 2024-01-20-18:17
 */
public class Response {
    private Channel channel;

    private Object result;

    private String requestId;

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
