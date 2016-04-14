package com.little.pang.boring.code.netty.protocal.netty.server;

import com.little.pang.boring.code.netty.protocal.netty.codec.NettyMessageDecoder;
import com.little.pang.boring.code.netty.protocal.netty.codec.NettyMessageEncoder;
import com.little.pang.boring.code.netty.protocal.netty.common.NettyProtocolConstant;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by jaky on 3/29/16.
 */
public class NettyProtocolServer {

    public void bind(int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyMessageDecoder(NettyProtocolConstant.NETTY_MESSAGE_MAX_LENGTH,
                                    NettyProtocolConstant.NETTY_MESSAGE_LENGTH_FIELD_OFFSET,
                                    NettyProtocolConstant.NETTY_MESSAGE_LENGTH_FIELD_LENGTH));
                            ch.pipeline().addLast(new NettyMessageEncoder());
                            ch.pipeline().addLast(new LoginAuthResponseHelper());
                        }
                    });

            // 绑定端口, 同步等待成功
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();

            // 等待服务端监听端口关闭
            channelFuture.channel().closeFuture().sync();
        } finally {
            // 优雅退出, 释放线程池资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;
        new NettyProtocolServer().bind(port);
    }

}
