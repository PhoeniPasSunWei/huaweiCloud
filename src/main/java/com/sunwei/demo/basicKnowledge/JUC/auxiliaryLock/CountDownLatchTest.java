package com.sunwei.demo.basicKnowledge.JUC.auxiliaryLock;

import java.util.concurrent.CountDownLatch;



/**
 * CountDownLatch 同步辅助
 * 原理:
 * countDownLatch.countDown(); //数量减1
 *
 * countDownLatch.await();// 等待计数器归零,然后再向下执行
 *
 * 每次有线程调用countDown()数量-1,假设计数器变为0,countDownLatch.await();就会被唤醒,继续执
 * ————————————————
 * 版权声明：本文为CSDN博主「YANG-Π」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/weixin_43888181/article/details/116546374
 */
public class CountDownLatchTest {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        for (int i = 0; i < 6; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"==>start");
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        System.out.println("main线程继续向下执行");

    }

}
