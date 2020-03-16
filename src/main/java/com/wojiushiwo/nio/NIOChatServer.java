package com.wojiushiwo.nio;

import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by myk
 * 2020/1/8 上午11:18
 * 实现服务器端与客户端之间的数据简单通讯
 * 服务器端:检测用户上线、离线 并实现消息转发功能
 */
public class NIOChatServer {

    private ServerSocketChannel serverSocketChannel;
    private Selector selector;

    public NIOChatServer() throws Exception {

        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(6669));

        selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

    }

    public static void main(String[] args) throws Exception {

        NIOChatServer server = new NIOChatServer();
        server.chat();
    }

    private void chat() throws Exception {

        String user = null;

        while (true) {

            int connectNum = selector.select();
            if (connectNum <= 0) {
                continue;
            }


            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {

                SelectionKey key = iterator.next();

                if (key.isAcceptable()) {

                    SocketChannel socketChannel = serverSocketChannel.accept();

                    System.out.println(socketChannel.getRemoteAddress() + "已经上线");

                    socketChannel.configureBlocking(false);

                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));

                }

                if (key.isReadable()) {
                    readMsg(key);
                }
                if (key.isWritable()) {
                    writeMsg(key);
//                    key.interestOps(SelectionKey.OP_READ);
                }

                iterator.remove();


            }


        }

    }

    private void readMsg(SelectionKey key) {

        SocketChannel channel = null;
        try {

            channel = (SocketChannel) key.channel();

            ByteBuffer buffer = ByteBuffer.allocate(1024);

            int len = channel.read(buffer);
            if (len > 0) {
                String content = new String(buffer.array(), 0, len);
                System.out.println(content.trim());
                key.interestOps(SelectionKey.OP_WRITE);
//                channel.register(selector, SelectionKey.OP_WRITE, content);
                sendMsgToOtherChannel(content,channel);
            }

        } catch (IOException e) {
            try {
                System.out.println(channel.getRemoteAddress() + " 离线了..");
                key.cancel();
                channel.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }

    }


    private void writeMsg(SelectionKey key) {

        SocketChannel channel = null;
        try {

            channel = (SocketChannel) key.channel();

            ByteBuffer buffer=ByteBuffer.allocate(1024);

             channel.read(buffer);


            sendMsgToOtherChannel(new String(buffer.array()).trim(), channel);

        } catch (IOException e) {
            try {
                System.out.println(channel.getRemoteAddress() + " 离线了..");
                key.cancel();
                channel.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }

    }

    private void sendMsgToOtherChannel(String msg, SocketChannel self) throws IOException {
        Set<SelectionKey> keys = selector.keys();
        for (SelectionKey key : keys) {

            SelectableChannel targetChannel = key.channel();

            if (targetChannel instanceof SocketChannel && targetChannel != self) {

                SocketChannel socketChannel = (SocketChannel) targetChannel;
                socketChannel.write(ByteBuffer.wrap(msg.getBytes()));
            }


        }
    }

}
