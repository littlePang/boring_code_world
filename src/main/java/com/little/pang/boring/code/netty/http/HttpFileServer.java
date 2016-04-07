package com.little.pang.boring.code.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;


/**
 * Created by jaky on 4/6/16.
 */
public class HttpFileServer {
    private static final String DEFAULT_URL = "/"; // /src/com/little/pang/netty";

    public void run(final int port, final String url) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("http-decoder", new HttpRequestDecoder());

                            // 将多个消息转换为单一的FullHttpRequest 或者 FullHttpResponse
                            // 原因是http解码器在每个http消息中会生成多个消息对象(1.HttpRequest/HttpResponse; 2. HttpContent; 3.LastHttpContent)
                            ch.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));

                            ch.pipeline().addLast("http-encode", new HttpResponseEncoder());

                            // 支持异步发送大的码流(例如大的文件传输), 但不占用过多的内存,防止java内存溢出错误
                            ch.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
                            ch.pipeline().addLast("fileServerHandler", new HttpFileServerHandler(url));
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind("127.0.0.1", port).sync();
            System.out.println("http 文件目录服务启动,网站: http://127.0.0.1:" + port + url);
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;
        String url = DEFAULT_URL;

        new HttpFileServer().run(port, url);
    }
}
