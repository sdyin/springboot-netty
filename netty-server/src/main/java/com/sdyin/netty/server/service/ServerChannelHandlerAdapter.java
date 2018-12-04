package com.sdyin.netty.server.service;

import com.sdyin.netty.server.constant.MethodInvokeMeta;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author: liuye
 * @Date: 2018/10/31 19:48
 * @Description
 */
@Slf4j
@Component
@ChannelHandler.Sharable //多线程共享
public class ServerChannelHandlerAdapter extends ChannelHandlerAdapter{

  /**
   * 注入请求分排器
   */
  @Resource
  private RequestDispatcher dispatcher;

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    cause.printStackTrace();
    ctx.close();
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) {
    MethodInvokeMeta invokeMeta = (MethodInvokeMeta) msg;
    // 屏蔽toString()方法
    if (invokeMeta.getMethodName().endsWith("toString()")
            && !"class java.lang.String".equals(invokeMeta.getReturnType().toString()))
      log.info("客户端传入参数 :{},返回值：{}",
              invokeMeta.getArgs(), invokeMeta.getReturnType());
    dispatcher.dispatcher(ctx, invokeMeta);
  }

}
