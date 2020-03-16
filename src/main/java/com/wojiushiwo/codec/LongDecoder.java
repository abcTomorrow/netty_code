package com.wojiushiwo.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by myk
 * 2020/1/29 下午7:03
 */
public class LongDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        System.out.println("调用LongDecoder#decode");
        //long 8个字节
        if (in.readableBytes() >= 8) {
            out.add(in.readLong());
        }

    }
}
