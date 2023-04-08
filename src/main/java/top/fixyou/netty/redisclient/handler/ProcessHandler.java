package top.fixyou.netty.redisclient.handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Lsk
 * @description
 * @date 2023/4/8 10:38
 */

@Slf4j
public class ProcessHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("读：" + msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("读完了");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String cmd = "*2\\r\\n$4\\r\\nLLEN\\r\\n$6\\r\\nmylist\\r\\n";
        System.out.println("发送数据:" + cmd);
        ctx.write(Unpooled.copiedBuffer(cmd.getBytes()));
    }

}
