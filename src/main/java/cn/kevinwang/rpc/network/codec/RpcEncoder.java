package cn.kevinwang.rpc.network.codec;

import cn.kevinwang.rpc.util.SerializationUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author wang
 * @create 2024-01-20-19:06
 */
public class RpcEncoder extends MessageToByteEncoder {
    private Class<?> genericClass;

    public RpcEncoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        if(genericClass.isInstance(o)){
            byte[] data = SerializationUtil.serialize(o);
            // 第一个字符表示对应的序列化数据的长度
            byteBuf.writeInt(data.length);
            byteBuf.writeBytes(data);
        }
    }
}
