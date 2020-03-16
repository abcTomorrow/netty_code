package com.wojiushiwo.netty;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * Created by myk
 * 2020/1/21 上午11:54
 */
public class NettyServerCustomTaskHandler extends NettyServerHandler {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //比如有个很耗时的业务 异步执行 提交到taskQueue中
        ctx.channel().eventLoop().schedule(() -> {
            try {
                Thread.sleep(5 * 1000);
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello,这是一个耗时操作", CharsetUtil.UTF_8));
                System.out.println("channel code=" + ctx.channel().hashCode());
            } catch (Exception ex) {
                System.out.println("发生异常" + ex.getMessage());
            }
        }, 5, TimeUnit.SECONDS);

    }

}
