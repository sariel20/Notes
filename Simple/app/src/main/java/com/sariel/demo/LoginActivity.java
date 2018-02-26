package com.sariel.demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sariel.BaseActivity;
import com.sariel.R;

/**
 * Created by LiangCheng on 2018/2/1.
 */

public class LoginActivity extends BaseActivity {
    EditText etUserName;
    EditText etPassword;
    Button btn;
    NetworkChangeReceiver networkChangeReceiver;
    IntentFilter intentFilter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver, intentFilter);
    
        initView();
    }

    private void initView() {
        etUserName = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btn = findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("admin".equals(etUserName.getText().toString()) && "123".equals(etPassword.getText().toString())) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "输入错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    class NetworkChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                btn.setText("登录");
                btn.setClickable(true);
            } else {
                btn.setText("网络连接断开");
                btn.setClickable(false);
            }
        }
    }
}
