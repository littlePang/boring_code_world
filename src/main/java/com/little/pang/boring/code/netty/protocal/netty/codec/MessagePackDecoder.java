package com.little.pang.boring.code.netty.protocal.netty.codec;

import io.netty.buffer.ByteBuf;
import org.msgpack.MessagePack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jaky on 4/13/16.
 */
public class MessagePackDecoder {

    private static Logger logger = LoggerFactory.getLogger(MessagePackDecoder.class);

    private MessagePack messagePack;

    public MessagePackDecoder() {
        this.messagePack = new MessagePack();
    }

    public Object decodeObject(ByteBuf byteBuf) {
        try {
            int objectLength = byteBuf.readInt();
            byte[] objByteArr = new byte[objectLength];

            byteBuf.readBytes(objectLength);

            return messagePack.read(objByteArr);
        } catch (Exception e) {
            logger.error("解析对象失败", e);
        }
        return null;
    }
}
