package com.little.pang.boring.code.netty.time.packagesplicing;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.google.common.base.Charsets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by jaky on 3/29/16.
 */
public class TimeServerHandler extends SimpleChannelInboundHandler {

    private int counter;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req, Charsets.UTF_8).substring(0, req.length -
        System.getProperty("line.separator").length());

        System.out.println("the time server receive order : " + body + "this counter " + ++counter);

        String currentTime = "query time order".equalsIgnoreCase(body) ? DateFormatUtils.format(new Date(),
                "yyyy-MM-dd HH:mm:ss") : "bad order";

        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.write(resp);

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
