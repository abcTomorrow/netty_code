package com.wojiushiwo.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by myk
 * 2020/1/29 下午7:02
 */
public class CustomCodecClientHandler extends SimpleChannelInboundHandler<Long> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端CustomCodecClientHandler#channelActive");
        ctx.writeAndFlush(1999999L);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println("客户端CustomCodecClientHandler#channelRead0");
        System.out.println("from server:" + msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
