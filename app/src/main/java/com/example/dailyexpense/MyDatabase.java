package com.example.dailyexpense;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDatabase extends SQLiteOpenHelper {

    public static final String DatabaseName = "MyDb";
    public static final String TableName = "mytable";
    public static final String Col_1 = "Id";
    public static final String Col_2 = "DateAndTime";
    public static final String Col_3 = "Type";
    public static final String Col_4 = "Price";
    public static final String Col_5 = "Date";
    public static final String Col_6 = "Time";

    public MyDatabase(@Nullable Context context) {
        super(context, DatabaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table "+TableName+"(Id Integer primary key autoincrement, " +
                "DateAndTime Text, " +
                "Type Text, " +
                "Price Text, " +
                "Date Text, " +
                "Time Text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists "+TableName);
    }

    public Boolean addData(String dateAndTime, String type, String price, String date, String time){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(Col_2, dateAndTime);
        cv.put(Col_3, type);
        cv.put(Col_4, price);
        cv.put(Col_5, date);
        cv.put(Col_6, time);
        int flag = 0;
        try{
            db.insert(TableName, null, cv);
        }catch (Exception e){
            flag = 1;
        }
        if(flag == 0)
            return true;
        else
            return false;

    }
    public void updateSerialNo(int pos) {
        SQLiteDatabase db = this.getWritableDatabase();
        long noOfRows = DatabaseUtils.queryNumEntries(db, TableName);
        //Log.d("error","not reachable");
        for (long i = pos; i <= noOfRows; i++) {
            db.execSQL("update Notes_table set Id = Id-1 where Id =" + i + "+2");
        }
    }

    public Cursor showData(){
        SQLiteDatabase db = getWritableDatabase();
        String s = " GROUP BY Type";
        return db.rawQuery("select  Type, (sum(Price))    from "+TableName+s, null);
    }
    public Cursor showDailyData(){
        SQLiteDatabase db = getWritableDatabase();
        String s = " GROUP BY Date";
        return db.rawQuery("select  Date, (sum(Price))   from "+TableName+s, null);
    }

    public Cursor getDistinctType(){
        SQLiteDatabase db = getWritableDatabase();
        return db.rawQuery("select Distinct Type from "+TableName, null);
    }

    public Cursor sumExpense(){
        SQLiteDatabase db = getWritableDatabase();
        return db.rawQuery("select (sum(Price)) from "+ TableName,null);
    }

    public Cursor getTypePrice(String d){
        SQLiteDatabase db = getWritableDatabase();
//        String s = "select Type, Price from "+TableName+" "
        return db.rawQuery("select Type, Price from "+TableName+" where Date like '%"+d+"%'", null);
    }
    public Cursor getDatePrice(String type){
        SQLiteDatabase db = getWritableDatabase();
        return db.rawQuery("select Date, Price from "+TableName+" where Type like '%"+type+"%'", null);
    }

//    public Cursor getDailyAvg(){
//        SQLiteDatabase db = getWritableDatabase();
//        return db.rawQuery("select (count(distinct Date)) from "+TableName, null);
//    }

    public Cursor showDateAndTime(){
        SQLiteDatabase db = getWritableDatabase();

        return db.rawQuery("select DateAndTime, Date, Time from "+TableName, null);
    }

    public Cursor minDate(){
        SQLiteDatabase db = getWritableDatabase();
        return db.rawQuery("select (min(Date)) from "+TableName, null);
    }

    public Integer delete(String id){
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TableName, "id = ?",new String[]{id});
    }


}
