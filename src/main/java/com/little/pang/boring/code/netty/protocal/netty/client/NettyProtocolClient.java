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

/**
 * Created by jaky on 4/13/16.
 */
public class NettyProtocolClient {

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
                            ch.pipeline().addLast(new LoginAuthRequestHelper());
                        }
                    });

            // 发起异步连接操作
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();

            // 等待客户端链路关闭
            channelFuture.channel().closeFuture().sync();
        } finally {
            // 优雅退出,释放NIO线程组
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;
        new NettyProtocolClient().connect("127.0.0.1", port);
    }

}
