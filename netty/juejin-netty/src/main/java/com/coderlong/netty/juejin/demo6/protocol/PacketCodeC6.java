package com.coderlong.netty.juejin.demo6.protocol;


import com.coderlong.netty.juejin.demo6.protocol.request.LoginRequestPacket6;
import com.coderlong.netty.juejin.demo6.serialize.Serializer6;
import com.coderlong.netty.juejin.demo6.serialize.impl.JSONSerializer6;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.HashMap;
import java.util.Map;

import static com.coderlong.netty.juejin.demo6.protocol.command.Command6.LOGIN_REQUEST;


/**
 *
 */
public class PacketCodeC6 {
    private static final int MAGIC_NUMBER = 0x12345678;

    public static final PacketCodeC6 INSTANCE = new PacketCodeC6();

    private static final Map<Byte, Class<? extends Packet6>> packetTypeMap;

    private static final Map<Byte, Serializer6> serializerMap;

    static {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(LOGIN_REQUEST, LoginRequestPacket6.class);

        serializerMap = new HashMap<>();
        Serializer6 serializer = new JSONSerializer6();
        serializerMap.put(serializer.getSerializerAlgorithm(), serializer);
    }
    /**
     * 编码：封装成二进制的过程
     * @param packet
     * @return
     */
    public ByteBuf encode(Packet6 packet){
        return encode(ByteBufAllocator.DEFAULT, packet);
    }

    /**
     * 编码：封装成二进制的过程
     * @param packet
     * @return
     */
    public ByteBuf encode(ByteBufAllocator byteBufAllocator, Packet6 packet){
        //1.创建ByteBuf对象
        //ioBuffer()方法会返回适配io读写相关的内存，它会尽可能创建一个直接内存，
        //直接内存可以理解为不受jvm堆管理的内存空间，写到IO缓冲区的效果更高
        ByteBuf byteBuf = byteBufAllocator.ioBuffer();

        //2.序列化Java对象成二进制包
        byte[] bytes = Serializer6.DEFAULT.serialize(packet);

        //3.实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer6.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;

    }

    public Packet6 decode(ByteBuf byteBuf){
        //跳过magic number
        byteBuf.skipBytes(4);

        //跳过版本号
        byteBuf.skipBytes(1);

        //序列化算法标识
        byte serializeAlgorithm = byteBuf.readByte();

        //指令
        byte command = byteBuf.readByte();

        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];

        byteBuf.readBytes(bytes);

        Class<? extends Packet6> requestType = getRequestType(command);

        Serializer6 serializer = getSerializer(serializeAlgorithm);
        if(requestType != null && serializer != null){
            return serializer.deserialize(requestType, bytes);
        }

        return null;

    }

    private Serializer6 getSerializer(byte serializeAlgorithm){
        return serializerMap.get(serializeAlgorithm);
    }

    private Class<? extends Packet6> getRequestType(byte command){
        return packetTypeMap.get(command);
    }
}
