package com.wojiushiwo.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by myk
 * 2020/1/29 下午7:02
 */
public class CustomCodecServerHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println("调用了CustomCodecServerHandler#channelRead0");
        System.out.println("from client:" + msg);

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("调用了CustomCodecServerHandler#channelReadComplete");
        ctx.writeAndFlush(123456789L);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
