package com.example.ioc.inject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//① 自定义一个BindView注解
@Target(ElementType.FIELD) //说明该注解是用在属性上的
@Retention(RetentionPolicy.RUNTIME)//该注解可以保留到程序运行的时候
public @interface BindView {
    int value();
}
