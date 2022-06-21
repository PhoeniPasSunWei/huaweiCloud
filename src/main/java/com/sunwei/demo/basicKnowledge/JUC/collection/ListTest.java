package com.sunwei.demo.basicKnowledge.JUC.collection;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class ListTest {

    public static void main(String[] args) {
        //并发下 arrayList 是不安全的
        /**
         * 解决方案
         * 1. 使用vector解决
         * 2. List<String> arrayList = Collections.synchronizedList(new ArrayList<>());
         * 3. List<String> arrayList = new CopyOnWriteArrayList<>();
         */
        //copyOnWrite 写入时复制  COW 计算机程序设计领域的一种优化策略
        //多个线程调用的时候, list, 读取的时候固定的,写入的时候,可能会覆盖
        //在写入的时候避免覆盖造成数据问题
        //CopyOnWriteArrayList 比 vector牛逼在哪里

        //普通的报错 用同一个位置之前的值会被后来的额值覆盖掉
//        List<String> arrayList = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            new Thread(()->{
//                arrayList.add(UUID.randomUUID().toString().substring(0,5));
//                System.out.println(arrayList);
//            },String.valueOf(i)).start();
//        }


        //读写分离
        List<String> cpArrayList = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                cpArrayList.add(UUID.randomUUID().toString().substring(0,5));
                System.out.println(cpArrayList);
            },String.valueOf(i)).start();
        }

    }
}
