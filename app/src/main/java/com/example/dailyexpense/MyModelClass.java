package com.example.dailyexpense;

import java.util.ArrayList;

public class MyModelClass {

    static ArrayList<ArrayList<String>> arrayList = new ArrayList<>();

    String s;
    String price;

    public MyModelClass(String s, String price) {
        this.s = s;
        this.price = price;

        ArrayList<String> l = new ArrayList<>();
        l.add(s);
        l.add(price);
        arrayList.add(l);
    }

}
