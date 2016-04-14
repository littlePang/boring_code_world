package com.little.pang.boring.code.netty.protocal.netty.codec;

import com.google.common.base.Charsets;
import com.little.pang.boring.code.netty.protocal.netty.model.NettyMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Map;

/**
 * Created by jaky on 4/13/16.
 */
public class NettyMessageEncoder extends MessageToByteEncoder<NettyMessage> {

    private static Logger logger = LoggerFactory.getLogger(NettyMessageDecoder.class);

    private MessagePackEncoder messagePackEncoder;

    public NettyMessageEncoder() {
        this.messagePackEncoder = new MessagePackEncoder();
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, NettyMessage msg, ByteBuf out) throws Exception {
        try {

        out.writeInt(msg.getHeader().getCrcCode());

        out.writeInt(msg.getHeader().getLength());// 消息总长度占位

        out.writeLong(msg.getHeader().getSessionId());

        out.writeByte(msg.getHeader().getType()); // 消息类型

        out.writeByte(msg.getHeader().getPriority()); // 优先级默认为1

        out.writeInt(msg.getHeader().getAttachment().size());

        if (MapUtils.isNotEmpty(msg.getHeader().getAttachment())) {
            for (Map.Entry<String, Object> entry : msg.getHeader().getAttachment().entrySet()) {
                byte[] keyByteArr = entry.getKey().getBytes(Charsets.UTF_8);
                out.writeInt(keyByteArr.length);
                out.writeBytes(keyByteArr);

                messagePackEncoder.encodeObject(entry.getValue(), out);
            }
        }

        if (null != msg.getBody()) {
            messagePackEncoder.encodeObject(msg.getBody(), out);
        } else {
            out.writeInt(0);
        }
        out.setInt(4, out.readableBytes() - 8); // todo 为何这里要减8?

        } catch (Exception e) {
            logger.error("出错了", e);
        }


    }

}
