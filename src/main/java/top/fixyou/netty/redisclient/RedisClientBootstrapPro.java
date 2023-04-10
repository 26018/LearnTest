package top.fixyou.netty.redisclient;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import top.fixyou.netty.redisclient.code.Decode;
import top.fixyou.netty.redisclient.code.Encode;
import top.fixyou.netty.redisclient.handler.ProcessHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;

/**
 * @author Lsk
 * @description
 * @date 2023/4/10 16:25
 */

public class RedisClientBootstrapPro {

    public static void main(String[] args) throws InterruptedException, IOException {
        // TODO
        Bootstrap bootstrap = new Bootstrap();
        ByteBuf byteBuf = Unpooled.copiedBuffer("\r\n".getBytes());

        bootstrap.group(new NioEventLoopGroup(1))
                .channel(NioSocketChannel.class)
                .remoteAddress(new InetSocketAddress("127.0.0.1", 6379))
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, byteBuf));
                        ch.pipeline().addLast(new Decode());
                        ch.pipeline().addLast(new Encode());
                        ch.pipeline().addLast("process", new ProcessHandler());
                    }
                });

        Channel channel = bootstrap.connect().sync().channel();
        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String read = buffer.readLine();
            channel.writeAndFlush(Unpooled.copiedBuffer(read.getBytes()));
        }
    }


}
