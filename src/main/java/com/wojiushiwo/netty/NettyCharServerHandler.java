package com.wojiushiwo.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by myk
 * 2020/1/23 下午3:50
 */
public class NettyCharServerHandler extends SimpleChannelInboundHandler<String> {


    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channels.add(channel);
        System.out.println("---------" + channel.remoteAddress() + "-------------");
        //该方法会遍历ChannelGroup中的所有channel
        channels.writeAndFlush("[客户端]" + channel.remoteAddress() + sdf.format(new
                Date()) + " 加入聊天");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("[Server]" + ctx.channel().remoteAddress() + "已经上线了~" + "\n");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("[Server]" + ctx.channel().remoteAddress() + "已经下线了~" + "\n");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("服务器收到消息时间" + sdf.format(new Date()) + "->" + channel.remoteAddress() + "发送的消息：" + msg);
        System.out.println("服务器进行消息转发...");

        channels.forEach(ch -> {
            //如果遍历的channel不是当前channel，则将消息转发出去
            if (ch != channel) {
                ch.writeAndFlush("[客户]" + channel.remoteAddress() + " 发送了消息：" + msg + "\n");
            } else {
                //自己回显消息
                ch.writeAndFlush("[自己]发送了消息：" + msg + "\n");
            }
        });

    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channels.writeAndFlush("[客户端]" + channel.remoteAddress() + sdf.format(new
                Date()) + " 已经离开");
        //handlerRemoved事件执行后 channels中channel数量会减少1
        System.out.println("channels.size()" + channels.size());
    }
}
