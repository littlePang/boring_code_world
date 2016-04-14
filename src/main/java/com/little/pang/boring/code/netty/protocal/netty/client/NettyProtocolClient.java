package com.little.pang.boring.code.netty.protocal.netty.client;

import com.little.pang.boring.code.netty.protocal.netty.codec.NettyMessageDecoder;
import com.little.pang.boring.code.netty.protocal.netty.codec.NettyMessageEncoder;
import com.little.pang.boring.code.netty.protocal.netty.common.NettyProtocolConstant;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by jaky on 4/13/16.
 */
public class NettyProtocolClient {

    private static Logger logger = LoggerFactory.getLogger(NettyProtocolClient.class);

    private ScheduledExecutorService executor = Executors
            .newScheduledThreadPool(1);

    public void connect(String host, int port) throws Exception {
        //配置客户端NIO线程组
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyMessageDecoder(NettyProtocolConstant.NETTY_MESSAGE_MAX_LENGTH,
                                    NettyProtocolConstant.NETTY_MESSAGE_LENGTH_FIELD_OFFSET,
                                    NettyProtocolConstant.NETTY_MESSAGE_LENGTH_FIELD_LENGTH));
                            ch.pipeline().addLast(new NettyMessageEncoder());
                            ch.pipeline().addLast(new ReadTimeoutHandler(50));// 读超时
                            ch.pipeline().addLast(new LoginAuthRequestHelper());
                            ch.pipeline().addLast(new HeartBeatRequestHelper());
                        }
                    });

            // 发起异步连接操作
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();

            // 等待客户端链路关闭
            channelFuture.channel().closeFuture().sync();
        } finally {
            // 所有资源释放完成之后，清空资源，再次发起重连操作
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                        try {
                            connect("127.0.0.1", 8080);// 发起重连操作
                        } catch (Exception e) {
                            logger.error("something is wrong", e);
                        }
                    } catch (InterruptedException e) {
                        logger.error("something is wrong", e);
                    }
                }
            });


        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;
        new NettyProtocolClient().connect("127.0.0.1", port);
    }

}
