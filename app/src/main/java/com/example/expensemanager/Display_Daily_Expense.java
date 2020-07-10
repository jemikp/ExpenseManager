package com.example.expensemanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class Display_Daily_Expense extends AppCompatActivity {

    RecyclerView recyclerView;

    MyDatabaseHelper myDB;
    ArrayList<String> expense_id,expense_name,expense_amount,expense_date_full,expense_day,expense_month,expense_year;

    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display__daily__expense);

        recyclerView = findViewById(R.id.recyclerView);

        myDB = new MyDatabaseHelper(Display_Daily_Expense.this);
        expense_id = new ArrayList<>();
        expense_name = new ArrayList<>();
        expense_amount = new ArrayList<>();
        expense_date_full = new ArrayList<>();
        expense_day = new ArrayList<>();
        expense_month = new ArrayList<>();
        expense_year = new ArrayList<>();

        storeDataInArrays();



        customAdapter = new CustomAdapter(Display_Daily_Expense.this,this,expense_id,expense_name,expense_amount,expense_date_full);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Display_Daily_Expense.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    void storeDataInArrays(){

        Cursor cursor = myDB.getDailyData();
        if(cursor.getCount() == 0){
            Toast.makeText(this,"No data",Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()){
                expense_id.add(cursor.getString(0));
                expense_name.add(cursor.getString(1));
                expense_amount.add(cursor.getString(2));
                expense_date_full.add(cursor.getString(3));
            }
        }
    }


}