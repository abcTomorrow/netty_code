package com.wojiushiwo.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.util.Objects;

/**
 * Created by myk
 * 2020/1/21 下午2:28
 * 1、SimpleChannelInboundHandler是ChannelInboundHandlerAdapter的子类
 * 2、客户端和服务器端通讯的数据放在HttpObject中
 */
public class NettyBrowserHandler extends SimpleChannelInboundHandler<HttpObject> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {


        //判断是否为Http请求
        if (msg instanceof HttpRequest) {
            System.out.println("pipeline hashcode" + ctx.pipeline().hashCode() + ",NettyBrowserHandler.hash=" + this.hashCode());
            System.out.println("消息类型" + msg.getClass());
            System.out.println("客户端地址:" + ctx.channel().remoteAddress());
            HttpRequest httpRequest = (HttpRequest) msg;
            //浏览器请求时会请求图标favicon.ico 这里没有这个图标因此将这个给过滤掉
            if (Objects.equals(httpRequest.uri(), "/favicon.ico")) {
                return;
            }

            //回复信息给浏览器
            ByteBuf byteBuf = Unpooled.copiedBuffer("hello,这是浏览器", CharsetUtil.UTF_16);
            //给浏览器回复信息 要符合http协议规范'
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);
            //设置content-type=text-plain
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            //设置content-length
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, byteBuf.readableBytes());
            //将构建好 response 返回
            ctx.writeAndFlush(response);

        }

    }
}
