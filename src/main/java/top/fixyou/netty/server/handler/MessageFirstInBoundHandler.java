package top.fixyou.netty.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Lsk
 * @description
 * @date 2023/4/6 15:56
 */

@Slf4j
public class MessageFirstInBoundHandler extends ChannelInboundHandlerAdapter {

    List<String> content = new ArrayList<>();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        content.add(String.valueOf(msg));
        log.info("first -- 管道读取:{}", msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.info("读取完成：{}", Arrays.toString(new List[]{content}));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("first -- 触发异常");
        cause.printStackTrace();
        Channel channel = ctx.channel();
        if (channel.isActive()) {
            channel.close();
        }
    }
}
