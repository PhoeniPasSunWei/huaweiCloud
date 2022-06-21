package com.sunwei.demo.basicKnowledge.proxy;

public class ProxyTest {
    public static void main(String[] args) {
        ProxyInvocationHandler pid = new ProxyInvocationHandler().setTarget(new HouseRent());
        Rent rent = (Rent) pid.getProxy();
        rent.rent();
    }
}
