package com.sdyin.netty.server.constant;

import org.springframework.stereotype.Component;

/**
 * @author: liuye
 * @Date: 2018/12/4 9:57
 * @Description
 */
@Component
public class NettyConstant {
  /**
   * 最大线程量
   */
  private static final int MAX_THREADS = 1024;
  /**
   * 数据包最大长度
   */
  private static final int MAX_FRAME_LENGTH = 65535;

  public static int getMaxFrameLength() {
    return MAX_FRAME_LENGTH;
  }

  public static int getMaxThreads() {
    return MAX_THREADS;
  }

}
