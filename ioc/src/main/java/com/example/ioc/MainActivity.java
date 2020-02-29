package com.example.ioc;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ioc.inject.BindView;
import com.example.ioc.inject.MyContentView;
import com.example.ioc.inject.OnClick;
import com.example.ioc.inject.OnLongClick;

//用注解来标识我们需要的布局文件
@MyContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @BindView(R.id.button1)
    Button btn1;
    @BindView(R.id.button2)
    Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //检测 控件注入 是否成功
        btn1.setText("我是注入的按钮01");
        btn2.setText("我是注入的按钮02");

    }

    @OnClick({R.id.button1, R.id.button2})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.button1:
                Toast.makeText(this, "短按下了", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button2:
                Toast.makeText(this, "短按下了222", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    //增加一个长按事件，注意这里的方法返回类型要和系统中的保持一致
    @OnLongClick({R.id.button1, R.id.button2})
    public boolean longClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                Toast.makeText(this, "好好学习", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button2:
                Toast.makeText(this, "天天向上", Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }
}


