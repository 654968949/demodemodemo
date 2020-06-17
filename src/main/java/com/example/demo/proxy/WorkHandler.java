package com.example.demo.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author:wuyuzi
 * @date:2020-06-01 16:13
 */
public class WorkHandler implements InvocationHandler {
    private Object object;

    public WorkHandler(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //在真实对象执行之前我们可以添加自己的操作
        System.out.printf("before invoke...");
        Object invoke = method.invoke(object,args);
        //在真实对象执行之后我们可以添加自己的操作
        System.out.printf("after invoke");
        return invoke;
    }
}
