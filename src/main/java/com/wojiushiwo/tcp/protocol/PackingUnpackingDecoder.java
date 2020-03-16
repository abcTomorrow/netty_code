package com.wojiushiwo.tcp.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * Created by myk
 * 2020/1/29 下午9:49
 */
public class PackingUnpackingDecoder extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        //这里的in代表的类型是ReplayingDecoderByteBuf
        int length = in.readInt();
        byte[] content = new byte[length];
        in.readBytes(content);

        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setContent(content);
        messageProtocol.setLength(length);
        //封装成 MessageProtocol 对象，放入 out， 传递下一个 handler 业务处理
        out.add(messageProtocol);

    }
}
