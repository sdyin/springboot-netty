package com.sdyin.netty.server.demo.socket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.oio.OioServerSocketChannel;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * netty 阻塞式网络编程
 * @author: liuye
 * @Date: 2018/12/5 14:58
 * @Description
 */
public class NettyOioServer {

  public void server(int port) throws Exception {
    final ByteBuf buf = Unpooled.unreleasableBuffer(
            Unpooled.copiedBuffer("Hi!\r\n", Charset.forName("UTF-8")));
    EventLoopGroup group = new OioEventLoopGroup();
    try {
      ServerBootstrap b = new ServerBootstrap();
      b.group(group)
              //使用 OioEventLoopGroup以允许阻塞模式（旧的I/O）
              .channel(OioServerSocketChannel.class)
              .localAddress(new InetSocketAddress(port))
              .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch)
                        throws Exception {
                  ch.pipeline().addLast(
                          new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelActive(
                                    ChannelHandlerContext ctx)
                                    throws Exception {
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
