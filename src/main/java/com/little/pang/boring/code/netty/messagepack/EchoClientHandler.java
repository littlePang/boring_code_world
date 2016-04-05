package com.little.pang.boring.code.netty.messagepack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by jaky on 3/30/16.
 */
public class EchoClientHandler extends SimpleChannelInboundHandler {

    public static final int USER_INFO_COUNT = 100;
    private static Logger logger = LoggerFactory.getLogger(EchoClientHandler.class);

    private UserInfo[] userInfos;

    public EchoClientHandler() {
        System.out.println("here_1");
        userInfos = new UserInfo[USER_INFO_COUNT];
        for (int i = 0; i < USER_INFO_COUNT; ++i) {
            userInfos[i] = new UserInfo(i, "name_" + i);
        }


    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("here_2");
        for (int i = 0; i < USER_INFO_COUNT; i++) {
            ctx.writeAndFlush(userInfos[i]);
        }
        System.out.println("here_3");

        System.out.println("here_4");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {

        System.out.println("here_5");
        System.out.println("client receive user info is " + msg);
        System.out.println("here_6");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.warn("unexpected exception form downstream : {}", cause.getMessage());
        System.out.println("exceptionCaught");
        ctx.close();
    }
}
