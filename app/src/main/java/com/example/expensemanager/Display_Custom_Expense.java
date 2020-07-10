package com.example.expensemanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Display_Custom_Expense extends AppCompatActivity {

    RecyclerView recyclerCustomExpense;

    MyDatabaseHelper myDB;
    ArrayList<String> expense_id,expense_name,expense_amount,expense_date_full,expense_day,expense_month,expense_year;

    CustomAdapter customAdapter;
    TextView textView5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display__custom__expense);

        EditText customexpense_name,customexpense_amount,customexpense_date;
        recyclerCustomExpense = findViewById(R.id.recyclerCustomExpense);
        textView5 = findViewById(R.id.textView5);

        myDB = new MyDatabaseHelper(Display_Custom_Expense.this);
        expense_id = new ArrayList<>();
        expense_name = new ArrayList<>();
        expense_amount = new ArrayList<>();
        expense_date_full = new ArrayList<>();
        expense_day = new ArrayList<>();
        expense_month = new ArrayList<>();
        expense_year = new ArrayList<>();


//        Bundle bn = getIntent().getExtras();
//        //String name = bn.getString("name");
//        String date = bn.getString("date");
//        //String amount = bn.getString("amount");

        Intent intent = getIntent();
        String str = intent.getStringExtra("message_key");

        textView5.setText(str);
        storeDataInArrays(str);



        customAdapter = new CustomAdapter(Display_Custom_Expense.this,this,expense_id,expense_name,expense_amount,expense_date_full);
        recyclerCustomExpense.setAdapter(customAdapter);
        recyclerCustomExpense.setLayoutManager(new LinearLayoutManager(Display_Custom_Expense.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    void storeDataInArrays(String date){


        Cursor cursor = myDB.getCustomData(date);
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