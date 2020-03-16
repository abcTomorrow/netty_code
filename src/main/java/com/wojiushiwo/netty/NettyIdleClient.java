package com.wojiushiwo.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Created by myk
 * 2020/1/23 下午6:27
 */
public class NettyIdleClient {
    public static void main(String[] args) {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            ChannelFuture channelFuture = bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .connect("127.0.0.1", 8889).sync();

            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }


    }
}
