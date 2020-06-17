package com.example.demo;

/**
 * @author:wuyuzi
 * @date:2020-06-01 15:26
 */
public class ProxyPatterDemo {

    public static void main(String[] args) {
        Image ge = new ProxyImage("test_10mb.jpg");

        ge.display();
        System.out.println("");
        ge.display();
    }
}
