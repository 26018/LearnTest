package top.fixyou.netty.redisclient;

import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.CodecException;
import io.netty.handler.codec.redis.*;
import io.netty.util.CharsetUtil;
import io.netty.bootstrap.Bootstrap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class RedisClient {
    private Channel channel ;
    public void openConnection(String host, int port) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(new NioEventLoopGroup(1)).
                channel(NioSocketChannel.class).
                handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline().addLast("decoder",new RedisDecoder());
                        ch.pipeline().addLast("bulk-aggregator",new RedisBulkStringAggregator());
                        ch.pipeline().addLast("array-aggregator",new RedisArrayAggregator());
                        ch.pipeline().addLast("encode",new RedisEncoder());
                        ch.pipeline().addLast("handler",new MyRedisHandler());
                    }
                });
        channel = bootstrap.connect(host, port).sync().channel();
        System.out.println("连接成功");
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        RedisClient client = new RedisClient();
        client.openConnection("127.0.0.1",6379);
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String s = in.readLine();
            System.out.print(">");
            client.channel.writeAndFlush(s);
        }
    }
    private class MyRedisHandler extends ChannelDuplexHandler {

        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
            if(!(msg instanceof String)){
                ctx.write(msg);
                return;
            }
            String cmd = (String) msg;
            String[] split = cmd.split("\\s+");
            List<RedisMessage> redisMessages = new ArrayList<>(split.length);
            for (String commend : split) {
                redisMessages.add(new FullBulkStringRedisMessage(Unpooled.wrappedBuffer(commend.getBytes())));
            }
            RedisMessage arrayRedisMessage = new ArrayRedisMessage(redisMessages);
            super.write(ctx, arrayRedisMessage, promise);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            printAggregatedRedisResponse((RedisMessage) msg);
        }
        private void printAggregatedRedisResponse(RedisMessage msg) {
            if (msg instanceof SimpleStringRedisMessage) {
                System.out.println(((SimpleStringRedisMessage) msg).content());
            } else if (msg instanceof ErrorRedisMessage) {
                System.out.println(((ErrorRedisMessage) msg).content());
            } else if (msg instanceof IntegerRedisMessage) {
                System.out.println(((IntegerRedisMessage) msg).value());
            } else if (msg instanceof FullBulkStringRedisMessage) {
                System.out.println(getString((FullBulkStringRedisMessage) msg));
            } else if (msg instanceof ArrayRedisMessage) {
                for (RedisMessage child : ((ArrayRedisMessage) msg).children()) {
                    printAggregatedRedisResponse(child);
                }
            } else {
                throw new CodecException("unknown message type: " + msg);
            }
        }
        private  String getString(FullBulkStringRedisMessage msg) {
            if (msg.isNull()) {
                return "(null)";
            }
            return msg.content().toString(CharsetUtil.UTF_8);
        }
    }


}
