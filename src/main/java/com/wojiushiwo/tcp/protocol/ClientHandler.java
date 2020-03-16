package com.wojiushiwo.tcp.protocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by myk
 * 2020/1/29 下午9:53
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {

            MessageProtocol messageProtocol = new MessageProtocol();
            String content = "hello,packing&unpacking," + (i + 1);
            messageProtocol.setContent(content.getBytes());
            messageProtocol.setLength(content.getBytes().length);
            ctx.writeAndFlush(messageProtocol);

        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
