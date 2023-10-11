package com.example.dailyexpense;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class RecyclerActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    myAdapter adapter;
    TextView total_expenditure, dailyAvg,temp;

    MyDatabase myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        total_expenditure = findViewById(R.id.total_expenditure);
        temp = findViewById(R.id.temp);
        dailyAvg = findViewById(R.id.dailyAvg);

        myDb = new MyDatabase(getApplicationContext());

        recyclerView = findViewById(R.id.recylerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();

        String intentValue = intent.getStringExtra("regular");
        Cursor res;
        if(intentValue.equals("showData")){
            res = myDb.showData();
        }
        else{
            res = myDb.showDailyData();
        }

        if(res.getCount() == 0){
            Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_SHORT).show();
        }
        else{
            setDataToModelClassArraylist(res);
        }



        adapter = new myAdapter(MyModelClass.arrayList, getApplicationContext(), new myAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int i) {
                String s = MyModelClass.arrayList.get(i).get(0);
                Intent intent = new Intent(getApplicationContext(), MainActivity3.class);
                try{
                    Date d = new SimpleDateFormat("dd-MM-yyyy").parse(s);

                    Cursor res = myDb.getTypePrice(s);
                    setDataToModelClassArraylist(res);

                    intent.putExtra("string", s);

                }catch (Exception e) {
                    Cursor res = myDb.getDatePrice(s);
                    setDataToModelClassArraylist(res);
                    intent.putExtra("string", s);
                }

                startActivity(intent);
            }
        });

        recyclerView.setAdapter(adapter);

        String sum = getTotalExpense();

        total_expenditure.setText("Total Expenditure: "+sum);

        dailyAvg.setText("Daily Avg: "+getDailyAverageExpense(sum));

    }

    void setDataToModelClassArraylist(Cursor res){
        MyModelClass.arrayList.clear();
        while (res.moveToNext()) {

            String type = res.getString(0);
            String price = res.getString(1);

            new MyModelClass(type, price);

        }

    }
    String getTotalExpense(){
        Cursor res = myDb.sumExpense();
        res.moveToNext();
        return res.getString(0);
    }

    Float getDailyAverageExpense(String sum){

        long difference_in_days=0;
        long difference_in_time=0;

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        Cursor resMinDate = myDb.minDate();
        resMinDate.moveToNext();
        String minimumDateFromDatabase = resMinDate.getString(0);

        try {

            Date d1 = sdf.parse(minimumDateFromDatabase);
            Date d2 = sdf.parse(currentDate);

            difference_in_time =  (d2.getTime() - d1.getTime());
            difference_in_days =  TimeUnit.MILLISECONDS.toDays(difference_in_time)%365;

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        temp.setText("No. of days taking while calculating avg: "+(difference_in_days+1));


        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        Float f = Float.parseFloat(sum)/(difference_in_days+1);

        return Float.parseFloat(df.format(f));

    }
}