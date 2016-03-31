package com.little.pang.boring.code.netty.time.packagesplicing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by jaky on 3/30/16.
 */
public class TimeClientHandler extends SimpleChannelInboundHandler {

    private static Logger logger = LoggerFactory.getLogger(TimeClientHandler.class);

    private byte[] req;

    private int counter;

    public TimeClientHandler() {
        req = ("query time order" + System.getProperty("line.separator")).getBytes();


    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf message;
        for (int i = 0; i < 100; i++) {
            message = Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.writeAndFlush(message);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req, Charsets.UTF_8);
        System.out.println("now is " + body + " this counter " + ++counter);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.warn("unexpected exception form downstream : {}", cause.getMessage());
        ctx.close();
    }
}
