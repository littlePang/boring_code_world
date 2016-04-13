package com.little.pang.boring.code.netty.protocal.netty.codec;

import io.netty.buffer.ByteBuf;
import org.msgpack.MessagePack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jaky on 4/13/16.
 */
public class MessagePackEncoder {
    private static Logger logger = LoggerFactory.getLogger(MessagePackDecoder.class);

    private MessagePack messagePack;

    public MessagePackEncoder() {
        this.messagePack = new MessagePack();
    }

    public void encodeObject(Object obj, ByteBuf byteBuf) throws Exception {
        byte[] objByteArr = messagePack.write(obj);
        int objLength = objByteArr.length;

        byteBuf.writeInt(objLength);
        byteBuf.writeBytes(objByteArr);


    }

}
