package com.example.demo.proxy;

/**
 * @author:wuyuzi
 * @date:2020-06-01 16:12
 */
public class Teacher implements  people {
    @Override
    public String work() {
        System.out.printf("老师就该教书！");
        return "教书";
    }
}
