package com.little.pang.boring.code.netty.protocal.netty.server;

import com.google.common.collect.Maps;
import com.little.pang.boring.code.netty.protocal.netty.common.NettyProtocalConstant;
import com.little.pang.boring.code.netty.protocal.netty.enums.MessageType;
import com.little.pang.boring.code.netty.protocal.netty.model.NettyMessage;
import com.little.pang.boring.code.netty.protocal.netty.model.NettyMessageHeader;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * Created by jaky on 4/13/16.
 */
public class LoginAuthResponseHelper extends SimpleChannelInboundHandler<NettyMessage> {

    private static Logger logger = LoggerFactory.getLogger(LoginAuthResponseHelper.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, NettyMessage msg) throws Exception {
        logger.info("invoke LoginAuthResponseHelper nettyMessage {}",
                ToStringBuilder.reflectionToString(msg, ToStringStyle.SHORT_PREFIX_STYLE));

        if (msg.getHeader().getType() == MessageType.HANDSHAKE_REQUEST.getCode()) {

            NettyMessage response = new NettyMessage();
            NettyMessageHeader header = new NettyMessageHeader();
            header.setCrcCode(NettyProtocalConstant.NETTY_PROTOCOL_IDENTIFICATION);
            header.setLength(0);
            header.setSessionId(new Random(System.currentTimeMillis()).nextLong());
            header.setType(MessageType.HANDSHAKE_RESPONSE.getCode());
            header.setPriority((byte) 1);
            header.setAttachment(Maps.<String, Object> newHashMap());

            response.setHeader(header);

            ctx.write(response);
            return;
        }
        ctx.fireChannelRead(msg);
    }
}
