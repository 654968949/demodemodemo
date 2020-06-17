package com.example.demo.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author:wuyuzi
 * @date:2020-06-01 16:21
 */
public class testProxy {
    public static void main(String[] args) {
        //要代理的真实的对象
        people pe = new Teacher();
        //代理对象的调用处理程序，
        InvocationHandler handler = new WorkHandler(pe);

        people proxy  = (people) Proxy.newProxyInstance(handler.getClass().getClassLoader(),pe.getClass().getInterfaces(),handler);

        System.out.printf(proxy.work());
    }
}
