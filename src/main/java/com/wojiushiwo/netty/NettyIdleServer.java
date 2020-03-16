package com.wojiushiwo.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * Created by myk
 * 2020/1/23 下午6:12
 * 实现一个心跳检测机制
 * 当服务器超过3秒没有读时，就提示读空闲
 * 当服务器超过5秒没有写操作时，就提示写空闲
 * 当服务器超过7秒没有读或者写操作时，就提示读写空闲
 */
public class NettyIdleServer {
    public static void main(String[] args) {

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {

            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .channel(NioServerSocketChannel.class)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            /**
                             * IdleStateHandler 是 netty 提供的处理空闲状态的处理器
                             * int readerIdleTimeSeconds,表示多上时间没有读，就会发送一个心跳包来检测是否连接
                             * int writerIdleTimeSeconds,表示多上时间没有写，就会发送一个心跳包来检测是否连接
                             * int allIdleTimeSeconds 表示多上时间没有读写，就会发送一个心跳包来检测是否连接
                             *
                             */
                            ChannelPipeline pipeline = ch.pipeline();
                            //当在指定的时间内没有读、写、读写操作发生时就会触发IdleStateEvent事件
                            //当IdleStateEvent事件被触发后，就会传递给pipeline的下一个handler去处理，通过触发下一个handler的userEventTriggered方法
                            pipeline.addLast(new IdleStateHandler(3, 5, 7));
                            pipeline.addLast(new MyIdleHandler());
                        }
                    });

            ChannelFuture channelFuture = serverBootstrap.bind(8889).sync();

            channelFuture.channel().closeFuture().sync();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }
}
