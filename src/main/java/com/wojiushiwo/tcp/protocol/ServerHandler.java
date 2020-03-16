package com.wojiushiwo.tcp.protocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * Created by myk
 * 2020/1/29 下午9:53
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //Object类型的msg即是解码器处理的数据类型
        MessageProtocol messageProtocol = (MessageProtocol) msg;
        System.out.println("from client[length:" + messageProtocol.getLength() + "内容:" + new String(messageProtocol.getContent(), CharsetUtil.UTF_8) + "]");

    }

//    @Override
//    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
//        System.out.println("from client[length:" + msg.getLength() + "内容:" + new String(msg.getContent(), CharsetUtil.UTF_8) + "]");
//    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
