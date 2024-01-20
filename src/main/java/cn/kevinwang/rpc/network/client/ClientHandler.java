package cn.kevinwang.rpc.network.client;

import cn.kevinwang.rpc.network.future.SyncWriteMap;
import cn.kevinwang.rpc.network.future.WriteFuture;
import cn.kevinwang.rpc.network.msg.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author wang
 * @create 2024-01-20-20:16
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Response response = (Response) msg;
        String requestId = response.getRequestId();

        WriteFuture future = SyncWriteMap.syncKey.get(requestId);
        if(null != future){
            future.setResponse(response);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
