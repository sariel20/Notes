package com.sariel.handler;

import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.sariel.R;

/**
 * Created by LiangCheng on 2018/1/23.
 */

public class ThreadLocalActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    /**
     * ThreadLocal 本地存储对象，各线程中存储数据互不干扰，可共享数据
     */
    private ThreadLocal<Boolean> threadLocal = new ThreadLocal<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        threadLocal.set(true);
        Log.e(TAG, "ThreadLocal(main): " + threadLocal.get() + "");

        new Thread("Thread#1") {
            @Override
            public void run() {
                threadLocal.set(false);
                Log.e(TAG, "Thread#1: " + threadLocal.get() + "");
            }
        }.start();

        new Thread("Thread#2") {
            @Override
            public void run() {
                Log.e(TAG, "Thread#2: " + threadLocal.get() + "");
            }
        }.start();

        new Thread("Thread#Loop") {
            @Override
            public void run() {
                Looper.prepare();//初始化线程为looper线程

                Looper.loop();//开始
            }
        };
    }
}
