package cn.kevinwang.rpc.network.future;

import cn.kevinwang.rpc.network.msg.Response;

import java.util.concurrent.Future;

/**
 * @author wang
 * @create 2024-01-20-19:52
 */
public interface WriteFuture<T> extends Future {

    Throwable cause();

    void setCause(Throwable cause);

    boolean isWriteSuccess();

    void setWriteResult(boolean result);

    String requestId();

    T response();

    void setResponse(Response response);

    boolean isTimeout();
}
