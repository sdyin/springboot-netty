package com.sdyin.netty.server.demo.socket;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * 阻塞网络编程
 * @author: liuye
 * @Date: 2018/12/5 14:51
 * @Description
 */
public class PlainOioServer {

  public void serve(int port) throws IOException {
    final ServerSocket socket = new ServerSocket(port);
    try {
      for (;;) {
        final Socket clientSocket = socket.accept();
        System.out.println(
                "Accepted connection from " + clientSocket);
        new Thread(new Runnable() {
          @Override
          public void run() {
            OutputStream out;
            try {
              out = clientSocket.getOutputStream();
              out.write("Hi!\r\n".getBytes(
                      Charset.forName("UTF-8")));
              out.flush();
              clientSocket.close();
            }
            catch (IOException e) {
              e.printStackTrace();
            }
            finally {
              try {
                clientSocket.close();
              }
              catch (IOException ex) {
              // ignore on close
              }
            }
          }
        }).start();
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {

  }

}
