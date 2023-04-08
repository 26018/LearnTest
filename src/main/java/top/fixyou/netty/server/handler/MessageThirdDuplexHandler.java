//package top.fixyou.netty.server.handler;
//
//import io.netty.buffer.ByteBuf;
//import io.netty.channel.ChannelDuplexHandler;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.ChannelPromise;
//import lombok.extern.slf4j.Slf4j;
//
//import java.nio.charset.StandardCharsets;
//
///**
// * @author Lsk
// * @description 全双工channel处理器
// * @date 2023/4/6 20:48
// */
//
//@Slf4j
//public class MessageThirdDuplexHandler extends ChannelDuplexHandler {
//
//    @Override
//    public void read(ChannelHandlerContext ctx) throws Exception {
//        ctx.alloc().buffer().retain();
//        super.read(ctx);
//        log.info("duplex -- 读:{}",ctx.alloc().buffer().toString(StandardCharsets.UTF_8));
//    }
//
//    @Override
//    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
//        log.info("duplex -- 写:{}", ((ByteBuf) msg).toString(StandardCharsets.UTF_8));
//        ctx.write(msg);
//    }
//
//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        log.info("duplex 发生异常");
//        cause.printStackTrace();
//        if (ctx.channel().isActive()) {
//            ctx.channel().close();
//            ctx.close();
//        }
//    }
//}
