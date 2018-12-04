package com.sdyin.netty.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: liuye
 * @Date: 2018/12/4 9:55
 * @Description
 */
@Component
@ConfigurationProperties(prefix = "netty")
public class NettyConfig {

  private int port;

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

}
