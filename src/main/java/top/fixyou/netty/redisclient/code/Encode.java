package top.fixyou.netty.redisclient.code;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
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
        String string = msg.toString(StandardCharsets.UTF_8);
        // 已修复 双引号内的空格不分割
        String[] commands = string.split(" (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        String respCommand = toRESPCommand(commands);
        ByteBuf byteBuf = Unpooled.copiedBuffer(respCommand.getBytes());
//        log.info("编码:{}", byteBuf.toString(StandardCharsets.UTF_8).replace("\r\n", "\\r\\n"));
        ctx.writeAndFlush(byteBuf);
    }

    private String toRESPCommand(String[] commands) {
        StringBuilder builder = new StringBuilder();
        int length = commands.length;
        String end = "\r\n";
        builder.append("*").append(length).append(end);
        for (String command : commands) {
            builder.append("$").append(command.length()).append(end);
            builder.append(command).append(end);
        }
        return builder.toString();
    }

}
