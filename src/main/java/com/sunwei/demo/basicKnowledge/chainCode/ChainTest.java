package com.sunwei.demo.basicKnowledge.chainCode;


public class ChainTest {

    private String name;
    private String subject;
    private Integer age;

    public ChainTest setName(String name) {
        this.name = name;
        return this;
    }

    public ChainTest setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public ChainTest setAge(Integer age) {
        this.age = age;
        return this;
    }

    @Override
    public String toString() {
        return "ChainTest{" +
                "name='" + name + '\'' +
                ", subject='" + subject + '\'' +
                ", age=" + age +
                '}';
    }

    public static void main(String[] args) {
        ChainTest chainTest = new ChainTest().setName("张三").setSubject("MATH").setAge(20);
        System.out.println(chainTest.toString());
    }
}

