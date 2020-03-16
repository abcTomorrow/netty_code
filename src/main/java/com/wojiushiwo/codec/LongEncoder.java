package com.wojiushiwo.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by myk
 * 2020/1/29 下午7:04
 */
public class LongEncoder extends MessageToByteEncoder<Long> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        System.out.println("调用编码器LongEncoder#encode");
        out.writeLong(msg);
    }

}
