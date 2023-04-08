package top.fixyou.netty.redisclient.code;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * @author Lsk
 * @description
 * @date 2023/4/8 10:39
 */

@Slf4j
public class Encode extends MessageToByteEncoder<ByteBuf> {

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) throws Exception {
        System.out.println(("encode:" + msg.toString(StandardCharsets.UTF_8)));
        ctx.writeAndFlush(msg);
    }
}
