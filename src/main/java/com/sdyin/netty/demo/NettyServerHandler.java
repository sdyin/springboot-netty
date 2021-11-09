package com.sdyin.netty.demo;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @Description:
 * @Author: liuye
 * @time: 2021/10/14$ 4:06 下午$
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    //channel 组
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        final Channel channel = ctx.channel();
        System.out.println("客户端 " + channel.remoteAddress() + " 上线了");
        //批量发送
        channelGroup.writeAndFlush("客户端 " + channel.remoteAddress() + " 上线了");
        channelGroup.add(channel);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        final Channel channel = ctx.channel();
        System.out.println("客户端 " + channel.remoteAddress() + " 下线了");
        channelGroup.remove(channel);
        channelGroup.writeAndFlush("客户端 " + channel.remoteAddress() + " 下线了");
    }

    /**
     * 读取客户端发送的数据
     *
     * @param ctx 上下文对象, 含有通道channel，管道pipeline
     * @param msg 就是客户端发送的数据
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        final Channel channel = ctx.channel();
        System.out.println("客户端 " + channel.remoteAddress() +  " 发送消息:" + msg);
        for (Channel ch: channelGroup) {
            if(channel.equals(ch)){
                ch.writeAndFlush("当前客户端 " + channel.remoteAddress() + " 发送消息:" + msg);
            } else {
                ch.writeAndFlush("客户端 " + channel.remoteAddress() + " 发送消息:" + msg);
            }
        }
    }


    /**
     * 数据读取完毕处理方法
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        /*ByteBuf buf = Unpooled.copiedBuffer("HelloClient", CharsetUtil.UTF_8);
        ctx.writeAndFlush(buf);*/
    }

    /**
     * 处理异常, 一般是需要关闭通道
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

}
