package com.example.dailyexpense;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.ColorSpace;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyModelClass {

    static ArrayList<ArrayList<String>> arrayList = new ArrayList<>();


    String id;
    String s;
    String price;

    public MyModelClass(String id, String s, String price) {
        this.id = id;
        this.s = s;
        this.price = price;

        ArrayList<String> l = new ArrayList<>();
        l.add(id);
        l.add(s);
        l.add(price);
        arrayList.add(l);
    }

    static void changeImageTo(int resourceId, View view){
        ((ImageView) view).setImageResource(resourceId);
    }

    public static void showDialog(int position, View view, myAdapter adapter, MyDatabase myDb, Context context) {
        changeImageTo(R.drawable.black_delete, view);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Delete");
        alertDialog.setMessage("Sure you want to delete this?");
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Delete anyway", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String id = arrayList.get(position).get(0);

                myDb.delete(id);
                arrayList.remove(position);
                adapter.notifyItemRemoved(position);
//                        recyclerView.setAdapter(adapter); this will also work

            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
                changeImageTo(R.drawable.red_delete, view);

            }
        });
        alertDialog.show();
    }
}
