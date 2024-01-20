package cn.kevinwang.rpc.util;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wang
 * @create 2024-01-20-18:49
 */
public class SerializationUtil {
    private static Map<Class<?>, Schema<?>> schemaCache = new ConcurrentHashMap<>();

    private static Objenesis objenesis = new ObjenesisStd();

    private SerializationUtil(){

    }

    public static<T> byte[] serialize(T obj) {
        Class<T> cls = (Class<T>) obj.getClass();
        LinkedBuffer allocate = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try {
            Schema schema = getSchema(cls);
            return ProtostuffIOUtil.toByteArray(obj, schema, allocate);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            allocate.clear();
        }
    }

    public static <T> T deserialize(byte[] data,Class<T> cls){
        try {
            T obj = objenesis.newInstance(cls);
            Schema<T> schema = getSchema(cls);
            ProtostuffIOUtil.mergeFrom(data,obj,schema);
            return obj;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(),e);
        }
    }

    public static Schema getSchema(Class cls){
        Schema schema = schemaCache.get(cls);
        if (schema == null) {
            schema = RuntimeSchema.createFrom(cls);
            schemaCache.put(cls, schema);
        }
        return schema;
    }
}
