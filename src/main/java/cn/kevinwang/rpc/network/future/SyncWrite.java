package cn.kevinwang.rpc.network.future;

import cn.hutool.core.lang.generator.UUIDGenerator;
import cn.hutool.core.util.IdUtil;
import cn.kevinwang.rpc.network.msg.Request;
import cn.kevinwang.rpc.network.msg.Response;
import io.netty.channel.Channel;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * @author wang
 * @create 2024-01-20-19:59
 */
public class SyncWrite {
    public Response writeAndSync(Channel channel, final Request request,final long timeout) throws Exception {
        if(channel == null) {
            throw new IllegalArgumentException("channel is null");
        }
        if(request == null) {
            throw new IllegalArgumentException("request is null");
        }
        if(timeout <= 0) {
            throw new IllegalArgumentException("timeout <= 0");
        }
        String requestId = IdUtil.fastSimpleUUID();
        request.setRequestId(requestId);

        SyncWriteFuture future = new SyncWriteFuture(requestId);
        SyncWriteMap.syncKey.put(requestId, future);

        Response response = doWriteAndSync(channel, request, timeout, future);

        SyncWriteMap.syncKey.remove(requestId);
        return response;
    }

    private Response doWriteAndSync(Channel channel, Request request, long timeout,final SyncWriteFuture writeFuture) throws Exception{
        channel.writeAndFlush(request).addListener(f->{
            writeFuture.setWriteResult(f.isSuccess());
            writeFuture.setCause(f.cause());
            if(!f.isSuccess()) {
                SyncWriteMap.syncKey.remove(writeFuture.requestId());
            }
        });

        Response response = writeFuture.get();
        if(null == response){
            if(writeFuture.isTimeout()) {
                throw new TimeoutException();
            }else{
                throw new RuntimeException(writeFuture.cause());
            }
        }
        return response;
    }
}
