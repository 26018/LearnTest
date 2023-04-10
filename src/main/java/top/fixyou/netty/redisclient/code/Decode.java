package top.fixyou.netty.redisclient.code;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lsk
 * @description
 * @date 2023/4/8 10:39
 */

@Slf4j
public class Decode extends ByteToMessageDecoder {

    private int count = 0;
    private final List<String> output = new ArrayList<>();

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // TODO 解码 如何判断接收完毕？
        byte[] target = new byte[in.readableBytes()];
        in.readBytes(target);
        String string = new String(target);

        if (string.startsWith("*") && count == 0 ) {
            count = Integer.parseInt(string.substring(1));
        } else if (string.startsWith("$") && count == 0) {
            output.add("*1");
            count = 1;
        }

//        log.info("接收:{}", string);

        out.add(string);
        output.add(string);
        if (count == 0) {
            StringBuffer stringBuffer = new StringBuffer();
            output.forEach(s -> stringBuffer.append(s).append("\r\n"));
            output.clear();
            ByteBuf buffer = in.alloc().buffer();
            buffer.writeBytes(stringBuffer.toString().getBytes());
            ctx.fireChannelRead(buffer);
        } else if (string.startsWith("$")) {
            count--;
        }
    }
}
