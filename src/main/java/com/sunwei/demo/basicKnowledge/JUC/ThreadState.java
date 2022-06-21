package com.sunwei.demo.basicKnowledge.JUC;


public enum ThreadState {
    // 新生
    NEW,

    // 运行
    RUNNABLE,

    // 阻塞
    BLOCKED,

    // 等待
    WAITING,

    //超时等待
    TIMED_WAITING,

    //终止
    TERMINATED;
}
