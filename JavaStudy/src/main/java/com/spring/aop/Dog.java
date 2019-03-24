package com.spring.aop;

public class Dog {

    private String name;


    public void say(){
        System.out.println(name + "在汪汪叫!...");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
