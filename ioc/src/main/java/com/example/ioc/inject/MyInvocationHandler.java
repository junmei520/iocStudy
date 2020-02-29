package com.example.ioc.inject;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

//代理的是 new View.OnClickLisener()对象
//并且最终执行的是activity的click()方法
public class MyInvocationHandler implements InvocationHandler {
    private Object activity;
    private Method activityMethod;

    public MyInvocationHandler(Object activity, Method activityMethod) {
        this.activity = activity;
        this.activityMethod = activityMethod;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        return activityMethod.invoke(activity, objects);
    }
}
