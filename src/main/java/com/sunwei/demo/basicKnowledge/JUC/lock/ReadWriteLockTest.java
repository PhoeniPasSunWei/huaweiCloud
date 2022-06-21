package com.sunwei.demo.basicKnowledge.JUC.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * 独占锁(写锁) 一次只能由一个线程占有
 * 共享锁(读锁) 一次可以有多个线程占有
 * readWriteLock
 * 读-读 可以共存
 * 读-写  不能共存
 * 写-写 不能共存
 */
public class ReadWriteLockTest {
    public static void main(String[] args) {
        MyCacheLock myCache = new MyCacheLock();

        //写入操作
        for (int i = 0; i < 6; i++) {
            int temp = i;
            new Thread(() -> {
                myCache.put(temp + "", temp + "");
            }, String.valueOf(i)).start();
        }

        //读取操作
        for (int i = 0; i < 6; i++) {
            int temp = i;
            new Thread(() -> {
                myCache.get(temp + "");
            }, String.valueOf(i)).start();
        }
    }
}

class MyCacheLock {

    private volatile Map<String, Object> map = new HashMap<>();
    //读写锁
    private java.util.concurrent.locks.ReadWriteLock lock = new ReentrantReadWriteLock();

    // 存,写入的时候只有一个人操作
    public Object get(String key) {
        lock.readLock().lock();
        Object o = null;
        try {
            System.out.println(Thread.currentThread().getName() + "读取");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            o = map.get(key);
            System.out.println(Thread.currentThread().getName() + "读取ok" + o);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.readLock().unlock();
        }
        return o;
    }

    public void put(String key, Object value) {
        lock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "写入" + key);
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "写入完毕");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }
    }

}

//class MyCache {
//
//    private volatile Map<String, Object> map = new HashMap<>();
//
//    public Object get(String key) {
//        System.out.println(Thread.currentThread().getName() + "读取");
//
//        Object o = map.get(key);
//        System.out.println(Thread.currentThread().getName() + "读取ok" + o);
//
//        return o;
//    }
//
//    public void put(String key, Object value) {
//        System.out.println(Thread.currentThread().getName() + "写入" + key);
//        map.put(key, value);
//        System.out.println(Thread.currentThread().getName() + "写入完毕");
//    }
//}
