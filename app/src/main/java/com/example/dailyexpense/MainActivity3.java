package com.example.dailyexpense;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity3 extends AppCompatActivity {

    TextView headTV;
    RecyclerView recyclerView2;

    myAdapter adapter2;

    MyDatabase myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        int numberOfColumn = 2;

        headTV = findViewById(R.id.headTV);
        recyclerView2 = findViewById(R.id.recyclerView2);
        recyclerView2.setLayoutManager(new GridLayoutManager(this, numberOfColumn));
        myDb = new MyDatabase(getApplicationContext());

        Intent intent = getIntent();
        String s = intent.getStringExtra("string");

        headTV.setText(" size: "+MyModelClass.arrayList.size()+"   string"+s);

        adapter2 = new myAdapter(MyModelClass.arrayList, this, new myAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int i) {

            }

            @Override
            public void onDeleteImageClick(int position, View view) {

                MyModelClass.showDialog(position, view, adapter2, myDb, MainActivity3.this);

//                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity3.this);
//                alertDialog.setTitle("Delete");
//                alertDialog.setMessage("Sure you want to delete this?");
//                alertDialog.setCancelable(false);
//                alertDialog.setPositiveButton("Delete anyway", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        String id = MyModelClass.arrayList.get(position).get(0);
//
//                        myDb.delete(id);
//                        MyModelClass.arrayList.remove(position);
//                        adapter2.notifyItemRemoved(position);
////                        recyclerView2.setAdapter(adapter2); this will also work
//
////                        notifyDataSetChanged();
//
//                    }
//                });
//                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
////                        dialogInterface.dismiss();
//                    }
//                });
//                AlertDialog alertDialog1 = alertDialog.create();
//                alertDialog.show();

            }


        });
        recyclerView2.setAdapter(adapter2);

    }
}