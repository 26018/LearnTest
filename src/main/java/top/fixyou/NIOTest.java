package top.fixyou;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOTest {
    public static void main(String[] args) throws IOException {

        final String serverPath = "127.0.0.1";
        final int port = 6666;
        final int bufferSize = 1024;
        Selector selector = Selector.open();

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(serverPath, port));
        serverSocketChannel.configureBlocking(false);
        // 注册一个接收连接的channel
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT, ByteBuffer.allocate(bufferSize));

        while (true) {
            if (selector.selectNow() == 0) {
                continue;
            }
            /*
             此selectionKey(activeKeys)集合中的key在处理完数据后，需要从此集合中删除。
             理由：
                  从selector.select()系列方法之后，将存在响应事件的channel封装为
                  selectionKey放入selector的 publicSelectedKeys 中

                  如果在响应事件发生，处理完业务后不删除此key，当下一次遍历到此key的时候
                  再次获取accept，但是此key对应的channel并没有响应事件发生，也就获取不到accept
                  由此发生NullPointerException

                  相关回答：https://cloud.tencent.com/developer/article/1894102
            */
            Set<SelectionKey> activeKeys = selector.selectedKeys();

            Iterator<SelectionKey> iterator = activeKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey activeKey = iterator.next();
                // serverSocketChannel 请求连接时，此selectionKey被激活
                if (activeKey.isAcceptable()) {
                    // channel 和 serverSocketChannel是同一个对象
                    ServerSocketChannel channel = (ServerSocketChannel) activeKey.channel();
                    // 接收一个新的连接acceptChannel
                    SocketChannel acceptChannel = channel.accept();
                    acceptChannel.configureBlocking(false);
                    acceptChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(bufferSize));
                }
                // 由serverSocketChannel创建的新连接channel(acceptChannel)监听read请求
                if (activeKey.isReadable()) {
                    SocketChannel channel = (SocketChannel) activeKey.channel();
                    ByteBuffer buffer = (ByteBuffer) activeKey.attachment();
                    channel.read(buffer);
                    buffer.clear();

                    String string = new String(buffer.array()).trim();
                    String lan = String.format("from %s say :%s", channel.getRemoteAddress(), string);
                    System.out.println(lan);
                }
                iterator.remove();
            }
        }
    }
}