package com.wojiushiwo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by myk
 * 2020/1/8 下午5:53
 * 客户端与服务端的链接是channel,channel中只要有数据 客户端就能读/写
 */
public class WebServer {

    public static void main(String[] args) throws Exception{

        //1、打开服务器套接字通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //2、设定为非阻塞、调整此通道的阻塞模式。
        serverSocketChannel.configureBlocking(false);
        //3、检索与此通道关联的服务器套接字。
        ServerSocket serverSocket = serverSocketChannel.socket();
        //4、此类实现 ip 套接字地址 (ip 地址 + 端口号)
        InetSocketAddress address = new InetSocketAddress(8090);
        //5、将服务器绑定到选定的套接字地址
        serverSocket.bind(address);
        //6、打开Selector来处理Channel
        Selector selector = Selector.open();
        //7、将ServerSocket注册到Selector已接受连接，注册会判断是否为非阻塞模式
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        ByteBuffer readBuff = ByteBuffer.allocate(1024);
        final ByteBuffer msg = ByteBuffer.wrap("Hi!\r\n".getBytes());
        while (true){
            try {
                //等到需要处理的新事件：阻塞将一直持续到下一个传入事件
                selector.select();
            }catch (IOException e){
                e.printStackTrace();
                break;
            }
            //获取所有接收事件的SelectionKey实例
            Set<SelectionKey> readykeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = readykeys.iterator();
            while(iterator.hasNext()){
                SelectionKey key = iterator.next();
                iterator.remove();
                try {
                    //检查事件是否是一个新的已经就绪可以被接受的连接
                    if (key.isAcceptable()){
                        //channel：返回为其创建此键的通道。 即使在取消密钥后, 此方法仍将继续返回通道。
                        ServerSocketChannel server = (ServerSocketChannel)key.channel();
                        //可选择的通道, 用于面向流的连接插槽。
                        SocketChannel client = server.accept();
                        //设定为非阻塞
                        client.configureBlocking(false);
                        //接受客户端，并将它注册到选择器，并添加附件
                        client.register(selector,SelectionKey.OP_WRITE | SelectionKey.OP_READ,msg.duplicate());
                        System.out.println("Accepted connection from " + client);
                    }
                    //检查套接字是否已经准备好读数据
                    if (key.isReadable()){
                        SocketChannel client = (SocketChannel)key.channel();
                        readBuff.clear();
                        client.read(readBuff);
                        readBuff.flip();
                        System.out.println("received:"+new String(readBuff.array()).trim());
                        //将此键的兴趣集设置为给定的值。 OP_WRITE
                        //这种方式优于client.register设置SelectionKey.OP_WRITE的方式
                        key.interestOps(SelectionKey.OP_WRITE);
                    }
                    //检查套接字是否已经准备好写数据
                    if (key.isWritable()){
                        SocketChannel client = (SocketChannel)key.channel();
                        //attachment : 检索当前附件
                        ByteBuffer buffer = (ByteBuffer)key.attachment();
                        buffer.rewind();
                        client.write(buffer);
                        //将此键的兴趣集设置为给定的值。 OP_READ
                        key.interestOps(SelectionKey.OP_READ);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

}
