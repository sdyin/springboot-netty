package com.sdyin.netty.demo;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description:
 * @Author: liuye
 * @time: 2021/10/26$ 5:29 下午$
 */
public class ExchangeDemo {

    public static Exchanger<String> exchanger = new Exchanger<String>();

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        executorService.execute(() -> {
            String oldValue = "卡特琳娜";
            System.out.println("线程1：" + Thread.currentThread().getName() + "----" + oldValue);
            String newValue = "";
            try {
                newValue = exchanger.exchange(oldValue);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程1:" + Thread.currentThread().getName() + "----" + newValue);

        });


        executorService.execute(() -> {
            String oldValue = "皎月女神";
            System.out.println("线程2：" + Thread.currentThread().getName() + "----" + oldValue);
            String newValue = "";
            try {
                newValue = exchanger.exchange(oldValue);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程2:" + Thread.currentThread().getName() + "----" + newValue);
        });

        Thread.sleep(1000);
        System.out.println("执行完成");

    }
}
