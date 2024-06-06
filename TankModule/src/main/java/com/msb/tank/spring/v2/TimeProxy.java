package com.msb.tank.spring.v2;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/*
Apect means the weaving class
 */
@Aspect
public class TimeProxy {
    @Before("within(com.msb.tank.spring.v2.*)")
    public void before(){
        System.out.println("method start.. " + System.currentTimeMillis());
    }
    @After("within(com.msb.tank.spring.v2.*)")
    public void after(){
        System.out.println("method stop.. " + System.currentTimeMillis());
    }
}
