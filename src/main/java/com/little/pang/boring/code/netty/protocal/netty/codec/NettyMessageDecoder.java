package com.little.pang.boring.code.netty.protocal.netty.codec;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.little.pang.boring.code.netty.protocal.netty.model.NettyMessage;
import com.little.pang.boring.code.netty.protocal.netty.model.NettyMessageHeader;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by jaky on 4/11/16.
 */
public class NettyMessageDecoder extends LengthFieldBasedFrameDecoder {

    private static Logger logger = LoggerFactory.getLogger(NettyMessageDecoder.class);

    private MessagePackDecoder messagePackageDecoder;

    public NettyMessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
        messagePackageDecoder = new MessagePackDecoder();
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        try {
            ByteBuf message = (ByteBuf) super.decode(ctx, in);
            if (null == message) {
                return null;
            }

            NettyMessage nettyMessage = new NettyMessage();

            nettyMessage.setHeader(buildMessageHeader(message));

            if (message.readableBytes() > 4) {
                nettyMessage.setBody(messagePackageDecoder.decodeObject(message));
            }

            return nettyMessage;
        } catch (Exception e) {
            logger.error("出错了", e);
        }
        return null;
    }

    private NettyMessageHeader buildMessageHeader(ByteBuf message) {
        NettyMessageHeader header = new NettyMessageHeader();
        header.setCrcCode(message.readInt());
        header.setLength(message.readInt());
        header.setSessionId(message.readLong());
        header.setType(message.readByte());
        header.setPriority(message.readByte());

        Map<String, Object> attachmentMap = Maps.newHashMap();
        int attachmentSize = message.readInt();
        if (attachmentSize > 0) {
            for (int i = 0; i < attachmentSize; i++) {
                int keyLength = message.readInt();
                byte[] keyByteArr = new byte[keyLength];
                message.readBytes(keyByteArr);

                String key = new String(keyByteArr, Charsets.UTF_8);
                attachmentMap.put(key, messagePackageDecoder.decodeObject(message));

            }
        }
        return header;
    }
}
