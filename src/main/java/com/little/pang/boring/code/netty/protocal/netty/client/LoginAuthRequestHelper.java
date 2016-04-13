package com.little.pang.boring.code.netty.protocal.netty.client;

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
public class LoginAuthRequestHelper extends SimpleChannelInboundHandler<NettyMessage> {

    public static Logger logger = LoggerFactory.getLogger(LoginAuthRequestHelper.class);


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, NettyMessage msg) throws Exception {

        if (msg.getHeader().getType() == MessageType.HANDSHAKE_RESPONSE.getCode()) {
            logger.info("login is ok, msg {}", ToStringBuilder.reflectionToString(msg,
                    ToStringStyle.SHORT_PREFIX_STYLE));
        }
        logger.info("invoke LoginAuthRequestHelper invoke");


        ctx.fireChannelRead(msg);

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.write(buildRequest());
    }

    private NettyMessage buildRequest() {
        NettyMessage request = new NettyMessage();
        NettyMessageHeader header = new NettyMessageHeader();
        header.setCrcCode(NettyProtocalConstant.NETTY_PROTOCOL_IDENTIFICATION);
        header.setLength(0);
        header.setSessionId(new Random(System.currentTimeMillis()).nextLong());
        header.setType(MessageType.HANDSHAKE_REQUEST.getCode());
        header.setPriority((byte) 1);
        header.setAttachment(Maps.<String, Object> newHashMap());

        request.setHeader(header);
        return request;
    }
}
