package com.little.pang.boring.code.netty.messagepack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;
import org.msgpack.template.Templates;

/**
 * Created by jaky on 4/5/16.
 */
public class MessagePackEncoder extends MessageToByteEncoder<UserInfo> {
    int count = 1;
    @Override
    protected void encode(ChannelHandlerContext ctx, UserInfo msg, ByteBuf out) throws Exception {
        MessagePack messagePack = new MessagePack();
        messagePack.register(UserInfo.class); // 这里需要注册这个类,不然就会出错 0.0
        // serialize
        try {
            out.writeBytes(messagePack.write(msg));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("wsfkmlwkmflkwmflkwemflewmeflewkmfwlmefwlfkme123123");

        ctx.writeAndFlush(out);
    }
}
