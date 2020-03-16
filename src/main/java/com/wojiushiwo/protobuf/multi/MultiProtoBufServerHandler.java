package com.wojiushiwo.protobuf.multi;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Objects;

/**
 * Created by myk
 * 2020/1/29 下午5:40
 */
public class MultiProtoBufServerHandler extends SimpleChannelInboundHandler<RolePOJO.Role> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RolePOJO.Role msg) throws Exception {

        RolePOJO.Role.DataType dataType = msg.getDataType();
        if (Objects.equals(dataType, RolePOJO.Role.DataType.Student)) {

            RolePOJO.Student student = msg.getStudent();
            System.out.println("获取到客户端发送的信息：" + student.getId() + "," + student.getName());

        } else if (Objects.equals(dataType, RolePOJO.Role.DataType.Worker)) {
            RolePOJO.Worker worker = msg.getWorker();
            System.out.println("获取到客户端发送的信息：" + worker.getAge() + "," + worker.getName());
        } else {
            System.out.println("获取到客户端发送的信息");
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
