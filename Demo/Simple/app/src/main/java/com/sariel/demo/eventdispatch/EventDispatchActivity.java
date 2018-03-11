package com.sariel.demo.eventdispatch;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.sariel.R;

/**
 * Created by ${LiangCheng} on 2018/3/7.
 */

public class EventDispatchActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        Button btn = findViewById(R.id.btn1);
        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e("TAG", "执行动作" + event.getAction());
                //设置true拦截事件，不会继续向下调用onClick方法
                return true;
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG", "执行onClick()");
            }
        });
    }
}
