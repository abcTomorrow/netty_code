package com.wojiushiwo.protobuf.multi;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by myk
 * 2020/1/29 下午5:41
 */
public class MultiProtoBufClientHandler extends ChannelInboundHandlerAdapter {

    public static void main(String[] args) {
        System.out.println((int) (Math.random() * 10));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        int randomNum = (int) (Math.random() * 10);
        RolePOJO.Role role;
        if (randomNum % 2 == 0) {
            RolePOJO.Student student = RolePOJO.Student.newBuilder().setId(1).setName("student").build();
            role = RolePOJO.Role.newBuilder().setDataType(RolePOJO.Role.DataType.Student)
                    .setStudent(student).build();

        } else {
            RolePOJO.Worker worker = RolePOJO.Worker.newBuilder().setAge(20).setName("worker").build();
            role = RolePOJO.Role.newBuilder().setDataType(RolePOJO.Role.DataType.Worker)
                    .setWorker(worker).build();

        }
        ctx.writeAndFlush(role);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
