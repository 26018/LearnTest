//package top.fixyou.netty.server.handler;
//
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.ChannelInboundHandlerAdapter;
//import lombok.extern.slf4j.Slf4j;
//
///**
// * @author Lsk
// * @description
// * @date 2023/4/6 15:56
// */
//
//@Slf4j
//public class MessageSecondInBoundHandler extends ChannelInboundHandlerAdapter {
//
//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        log.info("second -- 管道读取:{}", msg);
//    }
//
//
//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        log.info("second -- 触发异常");
//        cause.printStackTrace();
//        if (ctx.channel().isActive()) {
//            ctx.channel().close();
//            ctx.close();
//        }
//    }
//
//}
