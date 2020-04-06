package com.coderlong.netty.juejin.demo5;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.HashMap;
import java.util.Map;

import static com.coderlong.netty.juejin.demo5.Command.LOGIN_REQUEST;

/**
 *
 */
public class PacketCodeC {
    private static final int MAGIC_NUMBER = 0x12345678;

    private static final Map<Byte, Class<? extends Packet>> packetTypeMap;

    private static final Map<Byte, Serializer> serializerMap;

    static {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(LOGIN_REQUEST, LoginRequestPacket.class);

        serializerMap = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlgorithm(), serializer);
    }
    /**
     * 编码：封装成二进制的过程
     * @param packet
     * @return
     */
    public ByteBuf encode(Packet packet){
        //1.创建ByteBuf对象
        //ioBuffer()方法会返回适配io读写相关的内存，它会尽可能创建一个直接内存，
        //直接内存可以理解为不受jvm堆管理的内存空间，写到IO缓冲区的效果更高
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();

        //2.序列化Java对象成二进制包
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        //3.实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;

    }

    public Packet decode(ByteBuf byteBuf){
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

        Class<? extends Packet> requestType = getRequestType(command);

        Serializer serializer = getSerializer(serializeAlgorithm);
        if(requestType != null && serializer != null){
            return serializer.deserialize(requestType, bytes);
        }

        return null;

    }

    private Serializer getSerializer(byte serializeAlgorithm){
        return serializerMap.get(serializeAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command){
        return packetTypeMap.get(command);
    }
}
