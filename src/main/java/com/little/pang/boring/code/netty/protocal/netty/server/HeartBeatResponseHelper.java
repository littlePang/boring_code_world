package com.little.pang.boring.code.netty.protocal.netty.server;

import com.little.pang.boring.code.netty.protocal.netty.common.NettyProtocolConstant;
import com.little.pang.boring.code.netty.protocal.netty.enums.MessageType;
import com.little.pang.boring.code.netty.protocal.netty.model.NettyMessage;
import com.little.pang.boring.code.netty.protocal.netty.model.NettyMessageHeader;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * Created by jaky on 4/14/16.
 */
public class HeartBeatResponseHelper extends SimpleChannelInboundHandler<NettyMessage> {
    private static Logger logger = LoggerFactory.getLogger(HeartBeatResponseHelper.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, NettyMessage msg) throws Exception {
        try {
            if (msg != null && msg.getHeader().getType() == MessageType.HEART_REQUEST.getCode()) {
                String remoteHostAddress = ((InetSocketAddress) (ctx.channel().remoteAddress())).getAddress().getHostAddress();
                logger.info("receive heart beat message from {} , message:{}", remoteHostAddress, msg);
                ctx.writeAndFlush(buildHeartBeatResponse());
            } else {
                ctx.fireChannelRead(msg);
            }
        } catch (Exception e) {
            logger.error("something is wrong", e);
            throw e;
        }
    }

    private NettyMessage buildHeartBeatResponse() {

        NettyMessage heartBeatMessage = new NettyMessage();
        NettyMessageHeader header = new NettyMessageHeader();
        header.setCrcCode(NettyProtocolConstant.NETTY_PROTOCOL_IDENTIFICATION);
        header.setType(MessageType.HEART_RESPONSE.getCode());
        heartBeatMessage.setHeader(header);

        return heartBeatMessage;
    }
}
