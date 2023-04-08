package top.fixyou.netty.server.decode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * @author Lsk
 * @description
 * @date 2023/4/6 17:30
 */

@Slf4j
public class Decoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

        String string = byteBuf.toString(StandardCharsets.UTF_8);
        byteBuf.readerIndex(string.getBytes().length);
        list.add(string);

        log.info("\n=====解码=====");
        log.info("message:{}", byteBuf.toString(StandardCharsets.UTF_8));
        log.info("List:{}", Arrays.toString(new List[]{list}));
        log.info("====解码完成====\n");

    }
}
