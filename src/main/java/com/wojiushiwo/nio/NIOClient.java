package com.wojiushiwo.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by myk
 * 2020/1/8 上午10:30
 */
public class NIOClient {
    private static String IP = "127.0.0.1";
    private static int PORT = 6668;

    public static void main(String[] args) throws Exception {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        InetSocketAddress inetAddress = new InetSocketAddress(IP, PORT);
        if (!socketChannel.connect(inetAddress)) {
            while (!socketChannel.finishConnect()) {
                System.out.println("因为连接需要时间，客户端不会阻塞，可以做其它工作..");
            }
            String str = "Hello";
            ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());
            socketChannel.write(buffer);
            System.in.read();
        }
    }
}
