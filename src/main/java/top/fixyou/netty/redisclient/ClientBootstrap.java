package top.fixyou.netty.redisclient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import top.fixyou.netty.redisclient.handler.ProcessHandler;

import java.net.InetSocketAddress;

/**
 * @author Lsk
 * @description
 * @date 2023/4/8 10:34
 */

@Slf4j
public class ClientBootstrap {

    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(new NioEventLoopGroup(2))
                .channel(NioSocketChannel.class)
                .remoteAddress(new InetSocketAddress("127.0.0.1", 6379))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ProcessHandler());
                    }
                });
        Channel channel = bootstrap.connect().sync().channel();
    }
}
