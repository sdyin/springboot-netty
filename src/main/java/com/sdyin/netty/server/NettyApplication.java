package com.sdyin.netty.server;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NettyApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(NettyApplication.class, args);
	}

	/**
	 * 实现该接口之后，实现其run方法，可以在run方法中自定义实现任务
 	 */
	@Override
	public void  run(String... args) throws Exception {
		int port=8080;
		new ChatServer().bind(port);
	}
}
