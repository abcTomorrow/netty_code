package com.wojiushiwo.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;

/**
 * Created by myk
 * 2020/1/23 下午3:42
 * 1) 编写一个 Netty 群聊系统，实现服务器端和客户端之间的数据简单通讯(非阻塞)
 * 2) 实现多人群聊
 * 3) 服务器端:可以监测用户上线，离线，并实现消息转发功能
 * 4) 客户端:通过channel可以无阻塞发送消息给其它所有用户，同时可以接受其它用户发送的消息(由服务器转发
 * 得到)
 */
public class NettyChatServer {

    private static final int PORT = 8888;

    public NettyChatServer() {

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {

            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .handler(new LoggingHandler())
                    .channel(NioServerSocketChannel.class)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {

                            ChannelPipeline pipeline = ch.pipeline();
                            //加入字符串编解码器
                            pipeline.addLast("decoder", new StringDecoder());
                            pipeline.addLast("encoder", new StringEncoder());

                            pipeline.addLast(new NettyCharServerHandler());

                        }
                    });


            ChannelFuture channelFuture = serverBootstrap.bind(PORT).sync();

            channelFuture.addListener(future -> {
                if (channelFuture.isSuccess()) {
                    System.out.println("服务端Netty群聊服务器启动OK~");
                }
            });

            channelFuture.channel().closeFuture().sync();

        } catch (Exception e) {
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) {
        new NettyChatServer();
    }
}
