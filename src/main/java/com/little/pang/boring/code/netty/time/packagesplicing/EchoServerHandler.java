package com.little.pang.boring.code.netty.time.packagesplicing;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by jaky on 3/29/16.
 */
public class EchoServerHandler extends SimpleChannelInboundHandler {

    private int counter;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;

        System.out.println("the echo server receive order : [" + body + "] this counter " + ++counter);

        ByteBuf resp = Unpooled.copiedBuffer((body + "_$").getBytes());
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
