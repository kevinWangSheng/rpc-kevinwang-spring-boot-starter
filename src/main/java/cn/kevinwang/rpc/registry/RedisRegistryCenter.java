package cn.kevinwang.rpc.registry;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author wang
 * @create 2024-01-20-19:10
 */
public class RedisRegistryCenter {
    private static Jedis jedis;

    public static void init(String host,int port,String password){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(5);
        config.setTestOnBorrow(false);
        JedisPool jedisPool = new JedisPool(host,port,"",password);
        jedis = jedisPool.getResource();
    }

    public static long registryProvider(String nozzle,String alias,String info ){
        return jedis.sadd(nozzle + "_" + alias, info);
    }

    public static String obtainProvider(String nozzle,String alias){
        return jedis.srandmember(nozzle+"_"+alias);
    }

    public static Jedis jedis(){
        return jedis;
    }
}
