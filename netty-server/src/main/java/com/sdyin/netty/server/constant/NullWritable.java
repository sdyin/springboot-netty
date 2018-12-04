package com.sdyin.netty.server.constant;

import java.io.Serializable;

/**
 * 服务器可能返回空的处理
 * @author: liuye
 * @Date: 2018/12/4 10:11
 * @Description
 */
public class NullWritable implements Serializable{

  private static final long serialVersionUID = -8191640400484155111L;
  private static NullWritable instance = new NullWritable();

  private NullWritable() {
  }

  public static NullWritable nullWritable() {
    return instance;
  }
}
