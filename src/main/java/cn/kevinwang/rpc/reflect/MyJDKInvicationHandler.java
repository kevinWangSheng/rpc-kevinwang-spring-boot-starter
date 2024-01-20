package cn.kevinwang.rpc.reflect;

import cn.kevinwang.rpc.network.future.SyncWrite;
import cn.kevinwang.rpc.network.msg.Request;
import cn.kevinwang.rpc.network.msg.Response;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author wang
 * @create 2024-01-20-19:47
 */
public class MyJDKInvicationHandler implements InvocationHandler {
    private Request request;

    public MyJDKInvicationHandler(Request request) {
        this.request = request;
    }

    // 在动态代理的过程中吧对应的提供方的配置信息填充完整
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        if("toString".equals(methodName) && method.getParameterTypes().length == 0) {
            return request.toString();
        }else if("hashCode".equals(methodName) && method.getParameterTypes().length == 0) {
            return request.hashCode();
        }else if("equals".equals(methodName) && method.getParameterTypes().length == 1) {
            return request.equals(args[0]);
        }
        Class<?>[] parameterTypes = method.getParameterTypes();
        request.setMethodName(methodName);
        request.setParameterTypes(parameterTypes);
        request.setArgs(args);

        Response response = new SyncWrite().writeAndSync(request.getChannel(), request, 5000);

        // 返回调用的结果
        return response.getResult();
    }
}
