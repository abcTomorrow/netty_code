package com.wojiushiwo.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * Created by myk
 * 2020/1/23 下午6:22
 */
public class MyIdleHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (evt instanceof IdleStateEvent) {
            String idleType = null;
            IdleStateEvent event = (IdleStateEvent) evt;
            switch (event.state()) {
                case READER_IDLE:
                    idleType = "读空闲";
                    break;
                case WRITER_IDLE:
                    idleType = "写空闲";
                    break;
                case ALL_IDLE:
                    idleType = "读写空闲";
                    break;
            }
            System.out.println(ctx.channel().remoteAddress() + "-----超时事件----" + idleType);
        }
    }
}
