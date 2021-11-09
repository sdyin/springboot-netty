package com.sdyin.netty.demo;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import java.util.Scanner;

/**
 * @Description:
 * @Author: liuye
 * @time: 2021/10/14$ 3:46 下午$
 */
public class NettyClient {


    public static void main(String[] args) throws Exception {

        final NioEventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            final ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("encode", new StringEncoder());
                            pipeline.addLast("decode", new StringDecoder());
                            pipeline.addLast(new NettyClientHandler());
                        }
                    });
            System.out.println("netty client start");
            final ChannelFuture cf = bootstrap.connect("127.0.0.1", 9000).sync();
            final Channel channel = cf.channel();
            Scanner sc = new Scanner(System.in);
            while (sc.hasNextLine()){
                final String s = sc.nextLine();
                System.out.println("客户端输入: " + s);
                ByteBuf buf = Unpooled.copiedBuffer(s, CharsetUtil.UTF_8);
                channel.writeAndFlush(buf);
            }
        } finally {
            group.shutdownGracefully();
        }
    }
}
