package cn.kevinwang.rpc.reflect;

import cn.kevinwang.rpc.network.msg.Request;

import java.lang.reflect.InvocationHandler;

/**
 * @author wang
 * @create 2024-01-20-19:43
 */
public class JDKProxy {
    public static<T> T getProxy(Class<T> interfaceClass, Request request){
        InvocationHandler handler = new MyJDKInvicationHandler(request);
        T result = (T) java.lang.reflect.Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, handler);
        return result;
    }
}
