package com.sunwei.demo.basicKnowledge.JUC.threadPoll;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ExecutorServiceTest {
    public static void main(String[] args) {
        String awaitTime = "1000";
        ExecutorService pool = Executors.newFixedThreadPool(10);
        List<Future> list_Future = new ArrayList<Future>();
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            Callable<Boolean> callable = new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    System.out.println(finalI+"线程执行");
                    return true;
                }
            };
            Future<Boolean> submit = pool.submit(callable);
            list_Future.add(submit);
        }

        for (Future f : list_Future) {
            // 从Future对象上获取任务的返回值，并输出到控制台
            try {
                Boolean subRes = (Boolean) f.get();
            } catch (InterruptedException e) {
                System.out.println("awaitTermination interrupted: " + e);
                pool.shutdownNow();
            } catch (ExecutionException e) {
                System.out.println("awaitTermination interrupted: " + e);
                pool.shutdownNow();
            }

        }

        try {
            //停止接收新任务，原来的任务继续执行
            pool.shutdown();
            //等待所有的任务都结束的时候，返回TRUE
            if(!pool.awaitTermination(Long.valueOf(awaitTime), TimeUnit.MILLISECONDS)){
                // 停止接收新任务，原来的任务停止执行
                pool.shutdownNow();
            }
        } catch (InterruptedException e) {
            // awaitTermination方法被中断的时候也中止线程池中全部的线程的执行。
            System.out.println("awaitTermination interrupted: " + e);
            pool.shutdownNow();
        }
    }
}
