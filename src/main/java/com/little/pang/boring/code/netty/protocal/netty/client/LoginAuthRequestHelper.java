package com.little.pang.boring.code.netty.protocal.netty.client;

import com.google.common.collect.Maps;
import com.little.pang.boring.code.netty.protocal.netty.common.NettyProtocolConstant;
import com.little.pang.boring.code.netty.protocal.netty.enums.MessageType;
import com.little.pang.boring.code.netty.protocal.netty.model.NettyMessage;
import com.little.pang.boring.code.netty.protocal.netty.model.NettyMessageHeader;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.msgpack.type.IntegerValue;
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
        try {
            if (msg.getHeader().getType() == MessageType.HANDSHAKE_RESPONSE.getCode()) {

                byte handShakeResult = ((IntegerValue) msg.getBody()).getByte();
                if (handShakeResult != 0) {
                    logger.info("连接登录失败, handShakeResult {}", handShakeResult);
                    ctx.close();
                    return;
                } else {
                    logger.info("login is ok, msg {}", ToStringBuilder.reflectionToString(msg,
                            ToStringStyle.SHORT_PREFIX_STYLE));
                }
            }

            ctx.fireChannelRead(msg);
        } catch (Exception e) {
            logger.error("出错了", e);
            throw e;
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(buildRequest()); // 这里一定要调用flush,否则可能并不会发送消息
    }

    private NettyMessage buildRequest() {
        NettyMessage request = new NettyMessage();
        NettyMessageHeader header = new NettyMessageHeader();
        header.setCrcCode(NettyProtocolConstant.NETTY_PROTOCOL_IDENTIFICATION);
        header.setLength(0);
        header.setSessionId(new Random(System.currentTimeMillis()).nextLong());
        header.setType(MessageType.HANDSHAKE_REQUEST.getCode());
        header.setPriority((byte) 1);
        header.setAttachment(Maps.<String, Object> newHashMap());

        request.setHeader(header);
        return request;
    }
}
