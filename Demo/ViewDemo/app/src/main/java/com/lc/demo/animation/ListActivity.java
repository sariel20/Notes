package com.lc.demo.animation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lc.demo.R;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    RecyclerView rlv_data;
    ListAdapter adapter;
    List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        init();
    }

    private void init() {
        rlv_data = findViewById(R.id.rlv_data);
        list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            list.add("第" + i + "个元素");
        }

        adapter = new ListAdapter(this, list);
        rlv_data.setLayoutManager(new LinearLayoutManager(this));
        rlv_data.setAdapter(adapter);

    }
}
