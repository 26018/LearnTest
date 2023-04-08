package top.fixyou.netty.messagepush;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * @author Lsk
 * @description
 * @date 2023/4/7 21:36
 */

@Slf4j
public class MainBootstrap {

    public static void main(String[] args) throws InterruptedException {

        final String host = "localhost";
        final int port = 4444;

        ByteBuf buffer = Unpooled.buffer();
        buffer.writeBytes("\r\n".getBytes());

        EventLoopGroup bossGroup = new NioEventLoopGroup(2);
        EventLoopGroup workerGroup = new NioEventLoopGroup(4);
        ServerBootstrap serverBootstrap = new ServerBootstrap()
                .group(bossGroup, workerGroup)
                .localAddress(new InetSocketAddress(host, port))
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {

                    }
                });
        serverBootstrap.bind().sync();
        log.info("server -- netty启动成功\n");
    }
}
