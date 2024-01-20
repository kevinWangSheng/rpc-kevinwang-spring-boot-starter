package cn.kevinwang.rpc.network.server;

import cn.kevinwang.rpc.network.msg.Request;
import cn.kevinwang.rpc.network.msg.Response;
import cn.kevinwang.rpc.util.ClassLoaderUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author wang
 * @create 2024-01-20-17:56
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {

    private final ApplicationContext context;

    public MyServerHandler(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            Request request = (Request) msg;

            // 通过请求中的配置信息获取对应的bean，以及通过反射获取他的方法，然后进行调用得到返回值.
            Class type = ClassLoaderUtil.forName(request.getNozzle());
            Method method = type.getMethod(request.getMethodName(), request.getParameterTypes());
            Object objBean = context.getBean(request.getRef());
            Object result = method.invoke(objBean, request.getArgs());

            Response response = new Response();
            response.setChannel(request.getChannel());
            response.setResult(result);
            response.setRequestId(request.getRequestId());

            ctx.writeAndFlush(response);

            ReferenceCountUtil.release(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
