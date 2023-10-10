package com.example.dailyexpense;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText priceET, typeET;
    private AutoCompleteTextView autoCompleteTV;
    private Button addBtn, showBtn;

    MyDatabase myDb;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.daily_expenses,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId() == R.id.daily){

            Intent intent = new Intent(MainActivity.this, RecyclerActivity.class);
            intent.putExtra("regular", "showDailyData");
            startActivity(intent);

            return  true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        priceET = findViewById(R.id.priceET);
//        typeET = findViewById(R.id.typeET);
        addBtn = findViewById(R.id.addBtn);
        showBtn = findViewById(R.id.showBtn);
        autoCompleteTV = findViewById(R.id.autoCompleteTV);


        myDb = new MyDatabase(getApplicationContext());

        Cursor res = myDb.getDistinctType();
        ArrayList<String> arrayList = new ArrayList<>();
        int i=0;
        while(res.moveToNext()){
           arrayList.add(res.getString(0));
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, arrayList);
        autoCompleteTV.setAdapter(arrayAdapter);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  price = priceET.getText().toString().trim();
                String type = autoCompleteTV.getText().toString().trim();

                if(TextUtils.isEmpty(price)){
                    priceET.setError("Required...");
                    return;
                }
                if(TextUtils.isEmpty(type)){
                    typeET.setError("Required...");
                    return;
                }

                Date date = Calendar.getInstance().getTime();
                String currentDateAndTime = date.toString();

                String dt = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                String time = new SimpleDateFormat("KK:mm aaa", Locale.getDefault()).format(new Date());

//                boolean bool = myDb.addData(currentDateAndTime, type, price);


                if(myDb.addData(currentDateAndTime, type, price, dt, time))
                    Toast.makeText(MainActivity.this, "Added your expense", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Insertion failed", Toast.LENGTH_SHORT).show();

                priceET.setText("");
                autoCompleteTV.setText("");

            }
        });

        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, RecyclerActivity.class);
                intent.putExtra("regular", "showData");
                startActivity(intent);
            }
        });

    }
}