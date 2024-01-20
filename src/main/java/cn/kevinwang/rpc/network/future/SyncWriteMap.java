package cn.kevinwang.rpc.network.future;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wang
 * @create 2024-01-20-19:53
 */
public class SyncWriteMap {
    public static Map<String,WriteFuture> syncKey = new ConcurrentHashMap<>();
}
