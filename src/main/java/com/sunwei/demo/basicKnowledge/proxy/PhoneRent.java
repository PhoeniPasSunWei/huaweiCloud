package com.sunwei.demo.basicKnowledge.proxy;

public class PhoneRent implements Rent{
    @Override
    public void rent() {
        System.out.println("手机出租");
    }
}
