package top.fixyou.netty.server.encode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Lsk
 * @description
 * @date 2023/4/6 17:29
 */
@Slf4j
public class Encoder extends MessageToByteEncoder<Object> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        log.info("======编码=======");
        log.info("编码：{}", o);
        log.info("=====编码完成======");
        channelHandlerContext.writeAndFlush(o);
    }

}
