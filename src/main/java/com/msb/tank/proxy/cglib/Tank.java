package com.msb.tank.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Random;

/*
如果想让LogProxy可以重用，不仅可以代理Tank，还可以代理其他可以代理的类型Object
jdk 动态代理, 这样就可以代理所有的接口（或者没有接口)
cglib is code generated library, see below, tank doesn't implement any interface
 */

public class Tank {

    // tank 是被代理对象
    // JDK dynamic proxy shortage: the class must implement interface
    // Instrument: TODO to understand it online, use very rare
    // if the Tank class uses final, then it is not able to use the cglib

    public void move(){
        System.out.println("Tank moving clalcalc....");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Tank.class);
        enhancer.setCallback(new TimeMethodInterceptor());
        Tank tank = (Tank) enhancer.create();
        tank.move();
    }
}

// tank time proxy
class TimeMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println(obj.getClass().getSuperclass().getName());
        System.out.println("before");
        Object result = null;
        result = proxy.invokeSuper(obj, args);
        System.out.println("after");
        return result;
    }
}
