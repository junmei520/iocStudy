package com.example.ioc.inject;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//     btn1.setOnClickListener(new View.OnClickListener() {
////        @Override
////        public void onClick(View view) {
////
////        }
////    });
@Target(ElementType.ANNOTATION_TYPE) //该注解是用在自定义注解上的
@Retention(RetentionPolicy.RUNTIME)  //可以保留到程序运行时
public @interface BaseEvent {
    Class<?> enventType();  //事件 ---> 即相当于 new View.OnClickListener()

    String setterMethod(); //订阅关系 ---> 即相当于 setOnClickListener()

    String callbackMethod(); // 事件回调方法 ---> 即相当于 onClick()

}
