package com.example.dailyexpense;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity3 extends AppCompatActivity {

    TextView headTV;
    RecyclerView recyclerView2;

    myAdapter adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        headTV = findViewById(R.id.headTV);
        recyclerView2 = findViewById(R.id.recyclerView2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        String s = intent.getStringExtra("string");

        headTV.setText(" size: "+MyModelClass.arrayList.size()+"   string"+s);

        adapter2 = new myAdapter(MyModelClass.arrayList, getApplicationContext(), new myAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int i) {

            }
        });
        recyclerView2.setAdapter(adapter2);

    }
}