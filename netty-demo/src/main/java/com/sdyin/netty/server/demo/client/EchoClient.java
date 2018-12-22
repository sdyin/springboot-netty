package com.sdyin.netty.server.demo.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author: liuye
 * @Date: 2018/12/4 21:45
 * @Description
 */
public class EchoClient {
  private final String host;
  private final int port;

  public EchoClient(String host, int port) {
    this.host = host;
    this.port = port;
  }

  public void start() throws Exception{

    EventLoopGroup group = new NioEventLoopGroup();

    try {
      Bootstrap b = new Bootstrap();
      b.group(group)
              .channel(NioSocketChannel.class)
              .remoteAddress(new InetSocketAddress(host,port))
              .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel channel) throws Exception {
                  channel.pipeline().addLast(new EchoClientHandler());
                }
              });
      //连接到远程节点，阻塞等待直到连接完成
      ChannelFuture f = b.connect().sync();
      //阻塞，直到Channel 关闭
      f.channel().closeFuture().sync();
    } finally {
      group.shutdownGracefully().sync();
    }
  }

  public static void main(String[] args) throws Exception{
    if (args.length != 2) {
      System.err.println(
              "Usage: " + EchoClient.class.getSimpleName() +
                      " <host> <port>");
      return;
    }

    String host = args[0];
    int port = Integer.parseInt(args[1]);
    new EchoClient(host,port).start();
  }
}
