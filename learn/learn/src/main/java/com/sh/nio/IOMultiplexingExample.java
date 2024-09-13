package com.sh.nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class IOMultiplexingExample {
    public static void main(String[] args) throws IOException {
        // 创建一个Selector
        Selector selector = Selector.open();
        // 创建需要监听的Channel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress("localhost", 8080));
        serverSocketChannel.configureBlocking(false);
        // 将Channel注册到Selector，并指定监听事件为接收连接
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            // 阻塞等待就绪的事件
            selector.select();
            // 获取就绪的事件集合
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
            // 处理就绪的事件
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                if (key.isAcceptable()) {
                    // 接收连接事件
                    ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
                    SocketChannel clientChannel = serverChannel.accept();
                    clientChannel.configureBlocking(false);
                    clientChannel.register(selector, SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    // 可读事件
                    SocketChannel clientChannel = (SocketChannel) key.channel();
                    // 读取数据
                    ByteBuffer buffer = ByteBuffer.allocate(1024*14);
                    int bytesRead = clientChannel.read(buffer);
                    if (bytesRead > 0) {
                        buffer.flip();

                        byte[] data = new byte[buffer.remaining()];
                        buffer.get(data);
                        String message = new String(data);
                        System.out.println("Received from client: " + message);
                    }
                    // ...
                }
                // 处理完事件后，需要从集合中移除该事件
                keyIterator.remove();
            }
        }
    }
}
