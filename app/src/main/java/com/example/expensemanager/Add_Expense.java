package com.example.expensemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class Add_Expense extends AppCompatActivity {

    EditText title_input,author_input,pages_input;
    Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__expense);

        title_input = findViewById(R.id.expense_name);
        author_input = findViewById(R.id.expense_date);
        pages_input = findViewById(R.id.expense_amount);
        add_button = findViewById(R.id.add_button_expense);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);



        author_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Add_Expense.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month  = month + 1;
                        String date = dayOfMonth + "/" + month + "/" + year;
                        author_input.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }

        });

//        String full_date = author_input.getText().toString();
//
//        System.out.println(full_date);
//
//        String[] arr = full_date.split("/");


//        final String day1 = arr[0];
//        final String day2 = arr[1];
//        final String day3 = arr[2];

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(Add_Expense.this); //add context here
                myDB.addBook(title_input.getText().toString().trim(),
                        author_input.getText().toString().trim(),
                        Integer.valueOf(pages_input.getText().toString().trim()));
            }
        });
    }
}