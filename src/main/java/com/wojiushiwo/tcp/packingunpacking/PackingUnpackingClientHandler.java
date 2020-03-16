package com.wojiushiwo.tcp.packingunpacking;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * Created by myk
 * 2020/1/29 下午8:27
 */
public class PackingUnpackingClientHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //使用客户端发送5次消息
        for (int i = 0; i < 5; i++) {
            ctx.writeAndFlush(Unpooled.copiedBuffer("from client" + (i + 1) + " ", CharsetUtil.UTF_8));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
