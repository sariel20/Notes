package com.sariel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.sariel.demo.LoginActivity;

/**
 * Created by LiangCheng on 2018/2/1.
 */

public class BaseActivity extends AppCompatActivity {

    ForceOffLineReceiver forceOffLineReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.broadcastbestpractice.FORCE_OFFLINE");
        forceOffLineReceiver = new ForceOffLineReceiver();
        registerReceiver(forceOffLineReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (forceOffLineReceiver != null) {
            unregisterReceiver(forceOffLineReceiver);
            forceOffLineReceiver = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    class ForceOffLineReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, final Intent intent) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("警告");
            builder.setMessage("此账号已在其他地方登录，请重试");
            builder.setCancelable(false);
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCollector.finishAll();
                    Intent intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                }
            });
            builder.show();
        }
    }
}
