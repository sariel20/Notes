package com.sariel.demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sariel.BaseActivity;
import com.sariel.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by LiangCheng on 2018/2/1.
 */

public class MainActivity extends BaseActivity {


    EditText etData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnSendBroadcast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.example.broadcastbestpractice.FORCE_OFFLINE");
                sendBroadcast(intent);
            }
        });

        etData = findViewById(R.id.etData);
        findViewById(R.id.saveFile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFile(etData.getText().toString());
            }
        });
        findViewById(R.id.loadFile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etData.setText(load());
            }
        });
        findViewById(R.id.saveSp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSp();
            }
        });
        findViewById(R.id.loadSp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadSp();
            }
        });
        findViewById(R.id.contactsList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ContactsListActivity.class));
            }
        });
    }

    private void loadSp() {
        SharedPreferences preferences = getSharedPreferences("data", 0);
        etData.setText(preferences.getString("key", ""));
    }

    private void saveSp() {
        SharedPreferences.Editor editor = getSharedPreferences("data", 0).edit();
        editor.putString("key", etData.getText().toString());
        editor.apply();
        Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_SHORT).show();
        etData.setText("");
    }

    private void saveFile(String str) {
        FileOutputStream outputStream = null;
        BufferedWriter bufferedWriter = null;
        try {
            outputStream = openFileOutput("data", Context.MODE_PRIVATE);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            bufferedWriter.write(str);
            Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_SHORT).show();
            etData.setText("");
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "保存失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String load() {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuffer content = new StringBuffer();

        try {
            in = openFileInput("data");
            reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content.toString();
    }


}
