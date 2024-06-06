package com.msb.tank.proxy.v5;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Random;

/*
如果想让LogProxy可以重用，不仅可以代理Tank，还可以代理其他可以代理的类型Object
jdk 动态代理, 这样就可以代理所有的接口（或者没有接口)
 */

class Car implements Movable{
    @Override
    public void move() throws InterruptedException {
        System.out.println("car moving");
    }
}
public class Tank implements Movable, Loggable {
    @Override
    public void move() {
        System.out.println("tank start to move");
        try {
            Thread.sleep(new Random().nextInt(10000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    // tank 是被代理对象
    // JDK dynamic proxy shortage: the class must implement interface
    // Instrument: TODO to understand it online, use very rare
    public static void main(String[] args) throws InterruptedException {
        // like decorator, but it is proxy, java classloader 双亲委派 -> proxy
        // Tank.class.getClassLoader() is the 被代理对象

        System.setProperty("jdk.proxy.ProxyGenerator.saveGeneratedFiles", "true");
        String saveGeneratedFiles = System.getProperty("jdk.proxy.ProxyGenerator.saveGeneratedFiles");
        System.out.println("Proxy generation save files: " + saveGeneratedFiles);
        Object m =  Proxy.newProxyInstance(Tank.class.getClassLoader(), new Class[]{Movable.class, Loggable.class}, new InvocationHandler() {
            Tank tank = new Tank();
            Car car = new Car();
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("inside the invocation handler start " +method.getName() + "start...");
                Object o = method.invoke(tank, args); // 哪个被代理对象调用这个方法，就传哪个
                System.out.println(args != null ? args[0] : "");
                System.out.println("inside the invocation handler end " +method.getName() + "end...");
                return o;
            }
        });

        // can create more than one proxy and just need to cast to different class
        Movable moveProxy = (Movable) m;
        //Loggable logProxy = (Loggable) m;
        moveProxy.move();
    }

    @Override
    public void log(long logTime) {
        System.out.println("logging....." + logTime);
    }

    @Override
    public void log1() {
        System.out.println("this is log1");
    }
}


interface Loggable {
    void log(long logTime);
    void log1();
}



// tank time proxy
class TankTimeProxy implements Movable {
    Movable m;

    public TankTimeProxy(Movable m) {
        this.m = m;
    }

    @Override
    public void move() throws InterruptedException {
        long start = System.currentTimeMillis();
        m.move(); // this is the 被代理对象, 这里也就是tank
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}

/*
Log proxy
 */
class TankLogProxy implements Movable {
    Movable m;

    public TankLogProxy(Movable m) {
        this.m = m;
    }

    @Override
    public void move() throws InterruptedException {
        System.out.println("log tank moving");
        m.move();
        System.out.println("stopped");
    }
}


interface Movable {
    void move() throws InterruptedException;
}
