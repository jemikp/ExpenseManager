package com.example.expensemanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    Activity activity;
    private ArrayList expense_id,expense_name,expense_amount,expense_date_full;

    Animation translate_anim;

    CustomAdapter(Activity activity, Context context,
                  ArrayList expense_id,
                  ArrayList expense_name,
                  ArrayList expense_amount,
                  ArrayList expense_date_full){
        this.activity = activity;
        this.context = context;
        this.expense_id = expense_id;
        this.expense_name = expense_name;
        this.expense_amount = expense_amount;
        this.expense_date_full = expense_date_full;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {


        holder.expense_id_text.setText(String.valueOf(expense_id.get(position)));
        holder.expense_name_text.setText(String.valueOf(expense_name.get(position)));
        holder.expense_amount_text.setText(String.valueOf(expense_amount.get(position)));
        holder.expense_date_full_text.setText(String.valueOf(expense_date_full.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Update_Entered_Data.class);
                //When we click on recycler view item,we transfer data to new activity
                intent.putExtra("id",String.valueOf(expense_id.get(position)));
                intent.putExtra("name",String.valueOf(expense_name.get(position)));
                intent.putExtra("amount",String.valueOf(expense_amount.get(position)));
                intent.putExtra("date_full",String.valueOf(expense_date_full.get(position)));

                activity.startActivityForResult(intent,1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return expense_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView expense_id_text,expense_name_text,expense_amount_text,expense_date_full_text;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            expense_id_text = itemView.findViewById(R.id.expense_id_text);
            expense_name_text = itemView.findViewById(R.id.expense_name_text);
            expense_amount_text = itemView.findViewById(R.id.expense_amount_text);
            expense_date_full_text = itemView.findViewById(R.id.expense_date_full_text);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            //Animate RecyclerView;
            translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }
    }
}
