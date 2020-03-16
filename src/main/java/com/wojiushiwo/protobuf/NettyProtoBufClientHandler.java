package com.wojiushiwo.protobuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * Created by myk
 * 2020/1/26 下午4:42
 */
public class NettyProtoBufClientHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        StudentPOJO.Student student = StudentPOJO.Student.newBuilder().setId(1).setName("abc").build();
        ctx.writeAndFlush(student);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf buf= (ByteBuf) msg;
        System.out.println(buf.toString(CharsetUtil.UTF_8));


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    public static void main(String[] args) {
        String chars="AaBbCcDdEeFfGgHhXYyZz23456789,./+-{}[],./+-_()";
        String s = RandomStringUtils.random(12,chars);
        System.out.println(s);
    }
}
