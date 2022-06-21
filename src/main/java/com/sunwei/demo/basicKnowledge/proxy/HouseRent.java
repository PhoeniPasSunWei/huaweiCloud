package com.sunwei.demo.basicKnowledge.proxy;

public class HouseRent implements Rent{

    @Override
    public void rent() {
        System.out.println("房子出租");
    }
}
