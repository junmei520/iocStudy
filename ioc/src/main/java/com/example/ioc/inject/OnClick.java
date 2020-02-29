package com.example.ioc.inject;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//     btn1.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//
//        }
//    });

@Target(ElementType.METHOD) //该注解是用在方法上的
@Retention(RetentionPolicy.RUNTIME) //该注解可以保持到程序运行时
@BaseEvent(enventType = View.OnClickListener.class,
        setterMethod = "setOnClickListener",
        callbackMethod = "onClick")
public @interface OnClick {
    int[] value() default -1; //由于可能是多个id,所以此处要用数组来接收
}
