package com.sariel.handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by LiangCheng on 2017/11/22.
 */

public class HandlerThreadActivity extends Activity {
    private static final String TAG = "HandlerThreadActivity";
    TextView text;
    HandlerThread handlerThread;
    Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        text = new TextView(this);
        text.setText("handler Thread");
        setContentView(text);

        handlerThread = new HandlerThread("handlerThread");//指定handlerthread名称
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                Log.e(TAG, "handleMessage: " + Thread.currentThread());//打印执行线程
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    handler.sendEmptyMessage(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handlerThread.quit();//避免内存泄露
    }
}
