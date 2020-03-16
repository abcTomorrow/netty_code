package com.wojiushiwo.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by myk
 * 2020/1/8 上午10:09
 * 实现服务器端和客户端之间的数据通讯(非阻塞)
 */
public class NIOServer {
    private static String IP = "127.0.0.1";
    private static int PORT = 6668;

    public static void main(String[] args) throws Exception {

        Selector selector = Selector.open();

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(PORT));

        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {

            if (selector.select(1000L) == 0) {
                continue;
            }

            //如果客户端连接上了
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();
            while (iterator.hasNext()) {

                SelectionKey key = iterator.next();

                if (key.isAcceptable()) {
                    SocketChannel channel = serverSocketChannel.accept();
                    System.out.println(" 客 户 端 连 接 成 功 生 成 了 一 个 socketChannel " +
                            channel.hashCode());
                    channel.configureBlocking(false);
                    channel.register(selector, SelectionKey.OP_READ,ByteBuffer.allocate(1024));
                }

                if (key.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    socketChannel.read(buffer);
                    System.out.println("from 客户端:" + new String(buffer.array()).trim());
                }

                iterator.remove();

            }


        }

    }
}
