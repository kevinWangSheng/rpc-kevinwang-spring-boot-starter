package cn.kevinwang.rpc.network.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;

/**
 * @author wang
 * @create 2024-01-20-16:24
 */
public class SocketClient implements Runnable{
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(SocketClient.class);

    private String inetHost;

    private int port;
    private ChannelFuture future;
    @Override
    public void run() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {

            Bootstrap bs = new Bootstrap();
            bs.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.AUTO_READ,true)
                    .handler(new ClientChannelInitializer());
            ChannelFuture future = bs.connect(inetHost, port).sync();
            this.future = future;
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }
    }

    public ChannelFuture getFuture() {
        return future;
    }

    public void setFuture(ChannelFuture future) {
        this.future = future;
    }
}
