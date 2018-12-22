package com.sdyin.netty.server.demo.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author: liuye
 * @Date: 2018/12/4 20:10
 * @Description
 */
@ChannelHandler.Sharable //标识一个channelHandler 可被多个 Channel 安全共享
public class EchoServerHandler extends ChannelInboundHandlerAdapter{

  //对于每个传入的消息都要调用
  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    ByteBuf in = (ByteBuf) msg;
    System.out.println("Server received: "+ in.toString(CharsetUtil.UTF_8));
    //将接收到的消息写给发送者，而不冲刷出站消息
    ctx.write(in);
  }

  /**
   * 通知ChannelInboundHandler最后一次对channel-
   Read()的调用是当前批量读取中的最后一条消息
   * @param ctx
   * @throws Exception
   */
  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    //将未决消息冲刷到远程节点，并且关闭该 Channel
    ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
            .addListener(ChannelFutureListener.CLOSE);
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    cause.printStackTrace();
    //关闭Channel
    ctx.close();
  }
}
