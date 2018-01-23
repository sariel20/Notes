package com.sariel.handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.sariel.R;


/**
 * Created by LiangCheng on 2017/11/22.
 */

public class HandlerThread2Activity extends Activity implements View.OnClickListener {
    public static final String TAG = "HandlerThread2Activity";
    //主线程Handler
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Message message = new Message();
            Log.e(TAG, "handleMessage: MainHandler");
            //向子线程发送消息
            threadHandler.sendMessageDelayed(message, 1000);
        }
    };
    //子线程Handler 与HandlerThread关联
    private Handler threadHandler;
    private HandlerThread handlerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_thread);

        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);

        handlerThread = new HandlerThread("handlerThread");
        handlerThread.start();
        //子线程handler
        threadHandler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                Message message = new Message();
                Log.e(TAG, "handleMessage: ThreadHandler");
                //向主线程发送消息
                handler.sendMessageDelayed(message, 1000);
            }
        };
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                handler.sendEmptyMessage(1);
                break;
            case R.id.button2:
                handler.removeMessages(1);
                break;
        }
    }
}
