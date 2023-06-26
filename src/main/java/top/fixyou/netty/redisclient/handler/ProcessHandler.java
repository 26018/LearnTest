package top.fixyou.netty.redisclient.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import lombok.extern.slf4j.Slf4j;
import top.fixyou.netty.redisclient.util.ResponseParser;

import java.nio.charset.StandardCharsets;

/**
 * @author Lsk
 * @description
 * @date 2023/4/8 10:38
 */

@Slf4j
public class ProcessHandler extends ChannelDuplexHandler {


    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        log.info("ctx:" + ctx.hashCode());

        ByteBuf byteBuf = (ByteBuf) msg;
//        log.info("write original:{}", (byteBuf.toString(StandardCharsets.UTF_8)));
        ctx.write(byteBuf, promise);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("ctx:" + ctx.hashCode());


        if (msg instanceof String) {
            return;
        }
        ByteBuf byteBuf = (ByteBuf) msg;
        String string = byteBuf.toString(StandardCharsets.UTF_8);
        String parse = ResponseParser.parse(string.split("\r\n"));
        System.out.println("> " + parse);
        ctx.fireChannelRead(byteBuf);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
