package com.example.ioc.inject;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//btn2.setOnLongClickListener(new View.OnLongClickListener() {
//@Override
//public boolean onLongClick(View view) {
//        return false;
//        }
//        });

//只要多增加一个自定义的注解就可以了，传入具体的事件三要素。
@Target(ElementType.METHOD) //该注解是用在方法上的
@Retention(RetentionPolicy.RUNTIME) //该注解可以保持到程序运行时
@BaseEvent(enventType = View.OnLongClickListener.class,
        setterMethod = "setOnLongClickListener",
        callbackMethod = "onLongClick")
public @interface OnLongClick {
    int[] value() default -1; //由于可能是多个id,所以此处要用数组来接收
}
