package com.example.ioc;

import android.view.View;

import com.example.ioc.inject.BaseEvent;
import com.example.ioc.inject.BindView;
import com.example.ioc.inject.MyContentView;
import com.example.ioc.inject.MyInvocationHandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

//①造了一个女朋友
public class InjectUtils {
    public static void inject(Object context) {
        //布局的注入
        injectLayout(context);

        //控件的注入
        injectView(context);

        //事件的注入
        injectEvent(context);
    }

    //这种方式不可行，把代码写死了
//    private static void injectEvent(Object context) {
//        //利用反射
//        //1.获取activity的所有方法
//        //2.获取方法上的 OnClick 注解，进而得到注解后面的id ，然后得到button
//        //3.反射执行 btn1.setOnClickListener(new View.OnClickListener() {...}
//    }

    private static void injectEvent(Object context) {
        Class<?> clazz = context.getClass();
        //1.获取该activity上的所有方法
        Method[] methods = clazz.getDeclaredMethods();

        //2.循环遍历方法，拿到每一个方法上的所有注解
        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            //3.循环遍历注解，拿到注解类型对应的Class，通过class去找，看看有没有BaseEvent
            for (Annotation annotation : annotations) {
                //拿到注解类型对应的Class
                Class<?> annotationClass = annotation.annotationType();
                //通过Class去找，看看有没有BaseEvent
                BaseEvent baseEvent = annotationClass.getAnnotation(BaseEvent.class);

                //如果没有BaseEvent，则表示当前方法不是一个事件处理的方法
                if (baseEvent == null) {
                    continue;
                }

                //4.如果有BaseEvent,则表示是事件处理的方法,那我们就去拿事件的三要素
                //拿到三要素
                Class<?> eventType = baseEvent.enventType();
                String setterMethodStr = baseEvent.setterMethod();
                String callbackMethod = baseEvent.callbackMethod();

                //5.接下来我们要反射执行
//                   btn1.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//
//                        }
//                    });

                //5.1首先我们需要拿到事件源，（即对应的view控件,此处即指btn按钮）
                try {
                    //先获取注解中的value方法，即 OnClick中的value()
                    Method valueMethod = annotationClass.getDeclaredMethod("value");
                    //再反射执行 OnClick注解的 value()方法，得到id
                    int[] ids = (int[]) valueMethod.invoke(annotation);
                    for (int id : ids) {
                        //反射执行context.findViewById(id)得到对应的view
                        Method findViewByIdMethod = clazz.getMethod("findViewById", int.class);
                        View view = (View) findViewByIdMethod.invoke(context, id);
                        if (view == null) {
                            continue;
                        }

                        //5.2要拿到事件（即[new View.OnClickListener()]）,这个我们通过上面的BaseEvent已经得到了，即eventTpye。
                        //5.3拿到订阅关系（即setOnClickListener()),即根据setterMethodStr得到setterMethod

                        //5.4动态代理了  //activity==context    click===method
                        MyInvocationHandler myInvocationHandler = new MyInvocationHandler(context, method);
                        Object proxy = Proxy.newProxyInstance(eventType.getClassLoader(),
                                new Class[]{eventType}, myInvocationHandler);

                        //  让proxy执行的click()
                        //参数1  setOnClickListener（）的名称
                        //参数2  new View.OnClickListener()对象
                        Method setterMethod = view.getClass().getMethod(setterMethodStr, eventType);
                        // 反射执行  view.setOnClickListener（new View.OnClickListener()）
                        setterMethod.invoke(view, proxy);
                        //这时候，点击按钮时就会去执行代理类中的invoke方法()了
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private static void injectView(Object context) {
        //获取clazz
        Class<?> clazz = context.getClass();
        //获取clazz上的所有属性
        Field[] fields = clazz.getDeclaredFields();
        //循环遍历每一个属性
        for (Field field : fields) {
            //获取属性上的BindView注解
            BindView bindView = field.getAnnotation(BindView.class);
            if (bindView != null) {//如果该属性上找到了BindView注解
                //拿到注解后面的viewId
                int viewId = bindView.value();
                //运行到这里，每个按钮的ID已经取到了
                //下面就是反射执行findViewById方法
                try {
                    Method method = clazz.getMethod("findViewById", int.class);
                    View view = (View) method.invoke(context, viewId);
                    //对 field 做相关操作
                    //注意：如果获取的字段是私有的，不管是读还是写，都要先 field.setAccessible(true);才可以。否则会报：IllegalAccessException。
                    field.setAccessible(true);
                    //即把这个按钮view交给了activity(context)身上的field字段
                    field.set(context, view);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    //⑤实现injectLayout()方法
    private static void injectLayout(Object context) {
        // a.获取到Activity对应的Class
        Class<?> clazz = context.getClass();
        // b.拿到该Class上的MyContentView注解
        MyContentView myContentView = clazz.getAnnotation(MyContentView.class);
        if (myContentView != null) { //如果有MyContentView注解就执行以下操作
            // c.取到注解括号后面的内容，即布局id
            int layoutId = myContentView.value();
            //====== 接下来就要 反射去执行setContentView
            try {
                // d.利用反射获取setContentView()对应的method
                Method method = clazz.getMethod("setContentView", int.class);
                // e.反射执行setContentView()方法。即相当于context.method(layoutId);
                method.invoke(context, layoutId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
