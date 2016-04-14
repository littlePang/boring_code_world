package com.little.pang.boring.code.netty.protocal.netty.server;

import com.google.common.collect.Maps;
import com.little.pang.boring.code.netty.protocal.netty.common.NettyProtocolConstant;
import com.little.pang.boring.code.netty.protocal.netty.enums.MessageType;
import com.little.pang.boring.code.netty.protocal.netty.model.NettyMessage;
import com.little.pang.boring.code.netty.protocal.netty.model.NettyMessageHeader;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.Random;

/**
 * Created by jaky on 4/13/16.
 */
public class LoginAuthResponseHelper extends SimpleChannelInboundHandler<NettyMessage> {

    private static Logger logger = LoggerFactory.getLogger(LoginAuthResponseHelper.class);

    private Map<String, Boolean> loginCheckMap = Maps.newConcurrentMap();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, NettyMessage msg) throws Exception {
        try {
            if (msg.getHeader().getType() == MessageType.HANDSHAKE_REQUEST.getCode()) {

                String remoteHostAddress = ((InetSocketAddress) (ctx.channel().remoteAddress())).getAddress().getHostAddress();

                if (loginCheckMap.get(remoteHostAddress) != null) {
                    logger.info("ip {} 重复申请登录", remoteHostAddress);
                    ctx.writeAndFlush(buildHandShakeResponse((byte) -1)); // 登录失败
                    return;
                }

                if (NettyProtocolConstant.ALLOW_LOGIN_IPS.contains(remoteHostAddress)) {
                    logger.info("ip {} 在白名单中, 允许连接", remoteHostAddress);
                    loginCheckMap.put(remoteHostAddress, true);
                    ctx.writeAndFlush(buildHandShakeResponse((byte) 0)); // 登录成功

                } else {
                    logger.info("ip {} 不在白名单中, 不允许连接", remoteHostAddress);
                    ctx.writeAndFlush(buildHandShakeResponse((byte) -1)); // 登录失败

                }
                return;
            }
            ctx.fireChannelRead(msg);
        } catch (Exception e) {
            logger.error("出错了", e);
            throw e;
        }
    }

    private NettyMessage buildHandShakeResponse(byte result) {
        NettyMessage response = new NettyMessage();
        NettyMessageHeader header = new NettyMessageHeader();
        header.setCrcCode(NettyProtocolConstant.NETTY_PROTOCOL_IDENTIFICATION);
        header.setLength(0);
        header.setSessionId(new Random(System.currentTimeMillis()).nextLong());
        header.setType(MessageType.HANDSHAKE_RESPONSE.getCode());
        header.setPriority((byte) 1);
        header.setAttachment(Maps.<String, Object>newHashMap());

        response.setHeader(header);
        response.setBody(result);
        return response;
    }
}
