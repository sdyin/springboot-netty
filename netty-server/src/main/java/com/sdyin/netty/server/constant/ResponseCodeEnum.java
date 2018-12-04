package com.sdyin.netty.server.constant;

import java.io.Serializable;

/**
 * @author: liuye
 * @Date: 2018/12/4 10:17
 * @Description
 */
public enum ResponseCodeEnum implements Serializable{

  // region authentication code
  REQUEST_SUCCESS(10000, "请求成功"),
  SERVER_ERROR(99999, "服务器内部错误");

  //region 提供对外访问的方法,无需更改
  /**
   响应码
   */
  private Integer code;
  /**
   响应信息
   */
  private String msg;

  ResponseCodeEnum(Integer code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public Integer getCode() {
    return code;
  }

  public String getMsg() {
    return msg;
  }
}
