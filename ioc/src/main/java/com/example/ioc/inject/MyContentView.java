package com.example.ioc.inject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//④自定义注解MyContentView
@Target(ElementType.TYPE)   //表明：注解将来是使用在类上面的
@Retention(RetentionPolicy.RUNTIME) //表明注解的存活周期，我们希望可以在运行时读取到它的信息
public @interface MyContentView {
    int value();
}
