package com.wojiushiwo.tcp.packingunpacking;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * Created by myk
 * 2020/1/29 下午8:27
 */
public class PackingUnpackingServerHandler extends ChannelInboundHandlerAdapter {

    private int count = 0;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("=======================");
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("from client:" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("服务器端接收到的消息量=" + (++this.count));
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
