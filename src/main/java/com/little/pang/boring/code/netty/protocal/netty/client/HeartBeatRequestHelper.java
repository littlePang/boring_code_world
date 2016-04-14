package com.little.pang.boring.code.netty.protocal.netty.client;

import com.little.pang.boring.code.netty.protocal.netty.common.NettyProtocolConstant;
import com.little.pang.boring.code.netty.protocal.netty.enums.MessageType;
import com.little.pang.boring.code.netty.protocal.netty.model.NettyMessage;
import com.little.pang.boring.code.netty.protocal.netty.model.NettyMessageHeader;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.msgpack.type.IntegerValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by jaky on 4/14/16.
 */
public class HeartBeatRequestHelper extends SimpleChannelInboundHandler<NettyMessage> {

    private static Logger logger = LoggerFactory.getLogger(HeartBeatRequestHelper.class);



    @Override
    protected void channelRead0(final ChannelHandlerContext ctx, NettyMessage msg) throws Exception {
        try {
            if (msg != null && msg.getHeader().getType() == MessageType.HANDSHAKE_RESPONSE.getCode()) {
                byte handShakeResult = ((IntegerValue) msg.getBody()).getByte();
                if (0 == handShakeResult) { // 握手成功.开始发送心跳
                    ctx.executor().scheduleAtFixedRate(new Runnable() {
                        @Override
                        public void run() {
                            ctx.writeAndFlush(buildHeartBeatRequest());
                        }
                    }, 0, 5, TimeUnit.SECONDS);
                }
            } else if (msg != null && msg.getHeader().getType() == MessageType.HEART_RESPONSE.getCode()) {
                logger.info("receive heart beat msg : {}", msg);
            } else {
               ctx.fireChannelRead(msg);
            }
        } catch (Exception e) {
            logger.error("something is wrong ", e);
            throw e;
        }
    }

    private NettyMessage buildHeartBeatRequest() {
        NettyMessage heartBeat = new NettyMessage();
        NettyMessageHeader header = new NettyMessageHeader();
        header.setCrcCode(NettyProtocolConstant.NETTY_PROTOCOL_IDENTIFICATION);
        header.setType(MessageType.HEART_REQUEST.getCode());
        heartBeat.setHeader(header);
        return heartBeat;
    }
}
