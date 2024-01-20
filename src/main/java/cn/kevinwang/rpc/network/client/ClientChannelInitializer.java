package cn.kevinwang.rpc.network.client;

import cn.kevinwang.rpc.network.codec.RpcDecoder;
import cn.kevinwang.rpc.network.codec.RpcEncoder;
import cn.kevinwang.rpc.network.msg.Request;
import cn.kevinwang.rpc.network.msg.Response;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;

/**
 * @author wang
 * @create 2024-01-20-20:19
 */
public class ClientChannelInitializer extends ChannelInitializer {
    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new RpcDecoder(Response.class));
        pipeline.addLast(new RpcEncoder(Request.class));
        pipeline.addLast(new ClientHandler());
    }
}
