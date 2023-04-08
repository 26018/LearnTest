package top.fixyou.netty.redisclient.code;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author Lsk
 * @description
 * @date 2023/4/8 10:39
 */

@Slf4j
public class Decode extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("decode:"+in.toString(StandardCharsets.UTF_8));
    }

}
