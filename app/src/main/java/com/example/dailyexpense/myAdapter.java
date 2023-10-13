package com.example.dailyexpense;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myAdapter extends RecyclerView.Adapter<Holder> {

    ArrayList<ArrayList<String>> list = new ArrayList<>();
    Context context2;

//    MyDatabase myDb = new MyDatabase(context.getApplicationContext()); this will give Null PointerException
    MyDatabase myDb;

    RecyclerView recyclerView;
    private ItemClickListener itemClickListener;

    boolean isClicked = true;


    public myAdapter(ArrayList<ArrayList<String>> list, Context context,  ItemClickListener itemClickListener) {
        this.list = list;
        this.context2 = context;
        this.itemClickListener = itemClickListener;
        this.recyclerView = recyclerView;
        myDb = new MyDatabase(context2);

    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_row,parent,false);
        return new Holder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.type.setText(list.get(position).get(1));
        holder.price.setText(list.get(position).get(2));

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(position);
            }
        });

        holder.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onDeleteImageClick(position,  v);
            }
        });


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface ItemClickListener{
        void onItemClick(int i);
        void onDeleteImageClick(int i, View v);
    }
}

class Holder extends RecyclerView.ViewHolder{

    TextView type, price;
    LinearLayout layout;
    ImageView imageViewDelete;
    public Holder(@NonNull View itemView) {
        super(itemView);
        type = itemView.findViewById(R.id.type);
        price = itemView.findViewById(R.id.price);
        layout = itemView.findViewById(R.id.layout);
        imageViewDelete = itemView.findViewById(R.id.imageViewDelete);
//        final boolean isClicked = true;
//        imageViewDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(isClicked){
//                    imageViewDelete.setImageResource(R.drawable.baseline_delete_24_red);
//                    isClicked = false;
//                }
//
//            }
//        });

//        imageViewDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder alertDialog = new AlertDialog.Builder();
//                alertDialog.setTitle("Delete");
//                alertDialog.setMessage("Sure you want to delete this?");
//                alertDialog.setCancelable(false);
//                alertDialog.setPositiveButton("Delete anyway", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        String id = list.get(position).get(0);
//
//                        myDb.delete(id);
//                    }
//                });
//                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                    }
//                });
////                alertDialog.show();
//
//
//            }
//        });

    }
}
