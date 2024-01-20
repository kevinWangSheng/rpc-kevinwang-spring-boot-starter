package cn.kevinwang.rpc.network.server;

import cn.kevinwang.rpc.domain.LocalServerInfo;
import cn.kevinwang.rpc.network.codec.RpcDecoder;
import cn.kevinwang.rpc.network.codec.RpcEncoder;
import cn.kevinwang.rpc.network.msg.Request;
import cn.kevinwang.rpc.network.msg.Response;
import cn.kevinwang.rpc.util.NetUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.context.ApplicationContext;

/**
 * @author wang
 * @create 2024-01-20-17:45
 */
public class ServerSocket implements Runnable{
    private ChannelFuture future;

    private transient ApplicationContext applicationContext;

    public ServerSocket(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public boolean isActiveSocketServer() {
        if(null == future){
            return false;
        }
        return future.channel().isActive();
    }
    @Override
    public void run() {
        ServerBootstrap bs = new ServerBootstrap();
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            bs.group(bossGroup,workGroup).channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childHandler(new ChannelInitializer<ServerSocketChannel>(){
                        @Override
                        protected void initChannel(ServerSocketChannel serverSocketChannel) throws Exception {
                            serverSocketChannel.pipeline().addLast(
                                    new RpcDecoder(Request.class),
                                    new RpcEncoder(Response.class),
                                    new MyServerHandler(applicationContext));
                        }
                    });
            int port = 7890;
            while(NetUtil.isPortUsing(port)){
                port++;
            }
            LocalServerInfo.LOCAL_HOST = NetUtil.getHost();
            LocalServerInfo.LOCAL_PORT = port;
            this.future = bs.bind(port).sync();
            this.future.channel().closeFuture().sync();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
