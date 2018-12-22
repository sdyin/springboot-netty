package com.sdyin.netty.server.demo.socket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * netty 非阻塞网络编程
 * @author: liuye
 * @Date: 2018/12/5 15:01
 * @Description
 */
public class NettyNioServer {

  public void server(int port) throws Exception {
    final ByteBuf buf = Unpooled.copiedBuffer("Hi!\r\n",
            Charset.forName("UTF-8"));
    //为非阻塞模式使用NioEventLoopGroup
    EventLoopGroup group = new NioEventLoopGroup();
    try {
      ServerBootstrap b = new ServerBootstrap();
      b.group(group).channel(NioServerSocketChannel.class)
              .localAddress(new InetSocketAddress(port))
              .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch)
                        throws Exception{
                  ch.pipeline().addLast(
                          new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelActive(
                                    ChannelHandlerContext ctx) throws Exception {
                              ctx.writeAndFlush(buf.duplicate())
                                      .addListener(
                                              ChannelFutureListener.CLOSE);
                            }
                          });
                }
              });
      ChannelFuture f = b.bind().sync();
      f.channel().closeFuture().sync();
    } finally {
      group.shutdownGracefully().sync();
    }
  }
}
