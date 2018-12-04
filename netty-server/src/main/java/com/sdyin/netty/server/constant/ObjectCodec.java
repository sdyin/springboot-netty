package com.sdyin.netty.server.constant;

import com.sdyin.netty.server.util.ObjectSerializerUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

/**
 * @author: liuye
 * @Date: 2018/12/4 10:01
 * @Description
 */
public class ObjectCodec extends MessageToMessageCodec<ByteBuf,Object>{


  @Override
  protected void encode(ChannelHandlerContext ctx, Object msg, List<Object> out) throws Exception {
    byte[] data = ObjectSerializerUtils.serilizer(msg);
    ByteBuf buf = Unpooled.buffer();
    buf.writeBytes(data);
    out.add(buf);
  }

  @Override
  protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
    byte[] bytes = new byte[msg.readableBytes()];
    msg.readBytes(bytes);
    Object deSerilizer = ObjectSerializerUtils.deSerilizer(bytes);
    out.add(deSerilizer);
  }
}
