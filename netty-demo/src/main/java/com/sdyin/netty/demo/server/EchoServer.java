package com.sdyin.netty.demo.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author: liuye
 * @Date: 2018/12/4 20:24
 * @Description
 */
public class EchoServer {

  private final int port;

  public EchoServer(int port) {
    this.port = port;
  }

  public static void main(String[] args) throws Exception {
    //服务端默认端口
    int port=8080;
    new EchoServer(port).start();
  }

  private void start() throws Exception{
    final EchoServerHandler serverHandler = new EchoServerHandler();
    NioEventLoopGroup group = new NioEventLoopGroup();
    try {
      ServerBootstrap b = new ServerBootstrap();
      b.group(group)
              .channel(NioServerSocketChannel.class)
              .localAddress(new InetSocketAddress(port))
              .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                  /**
                   * EchoServerHandler被标注为@Shareable，所以我们可以总是使用同样的实例
                   */
                 ch.pipeline().addLast(serverHandler);
                }
              });
      //异步地绑定服务器；调用 sync()方法阻塞 等待直到绑定完成
      ChannelFuture f = b.bind().sync();
      //获取 Channel 的CloseFuture，并且阻塞当前线程直到它完成
      f.channel().closeFuture().sync();
    } finally {
      //关闭 EventLoopGroup，释放所有的资源
      group.shutdownGracefully().sync();
    }
  }
}
