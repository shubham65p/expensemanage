package com.example.dailyexpense;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myAdapter extends RecyclerView.Adapter<Holder> {

    ArrayList<ArrayList<String>> list = new ArrayList<>();
    Context context;

    private ItemClickListener itemClickListener;


    public myAdapter(ArrayList<ArrayList<String>> list, Context context, ItemClickListener itemClickListener) {
        this.list = list;
        this.context = context;
        this.itemClickListener = itemClickListener;
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
        holder.type.setText(list.get(position).get(0));
        holder.price.setText(list.get(position).get(1));

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(position);
            }
        });

        holder.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface ItemClickListener{
        void onItemClick(int i);
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
    }
}
