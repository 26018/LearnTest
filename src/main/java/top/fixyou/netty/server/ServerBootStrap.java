package top.fixyou.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import top.fixyou.netty.server.decode.Decoder;

import java.net.InetSocketAddress;

/**
 * @author Lsk
 * @description
 * @date 2023/4/6 15:34
 */

@Slf4j
public class ServerBootStrap {

    public static void main(String[] args) throws InterruptedException {

        ByteBuf buffer = Unpooled.buffer();
        buffer.writeBytes("\r\n".getBytes());

        EventLoopGroup bossGroup = new NioEventLoopGroup(2);
        EventLoopGroup workerGroup = new NioEventLoopGroup(4);
        ServerBootstrap serverBootstrap = new ServerBootstrap()
                .group(bossGroup, workerGroup)
                .localAddress(new InetSocketAddress("localhost", 2048))
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new Decoder());
//                        ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024 * 8, buffer));
//                        ch.pipeline().addLast(new Decoder());
//                        ch.pipeline().addLast(new MessageFirstInBoundHandler());
//                        ch.pipeline().addLast(new Encoder());
//                        ch.pipeline().addLast(new MessageSecondInBoundHandler());
                    }
                });
        Channel channel = serverBootstrap.bind().sync().channel();
        log.info("server -- netty启动成功\n");
    }
}
