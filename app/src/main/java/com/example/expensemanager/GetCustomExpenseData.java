package com.example.expensemanager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class GetCustomExpenseData extends AppCompatActivity {

    EditText customexpense_name,customexpense_amount,customexpense_date;
    Button custom_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_custom_expense_data);

//        customexpense_name = findViewById(R.id.customexpense_name);
//        customexpense_amount = findViewById(R.id.customexpense_amount);
        customexpense_date = findViewById(R.id.customexpense_date);
        custom_search = findViewById(R.id.custom_search);

        //final String name = customexpense_name.getText().toString();

        //final String amount = customexpense_amount.getText().toString();

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);



        customexpense_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        GetCustomExpenseData.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month  = month + 1;
                        String date = dayOfMonth + "/" + month + "/" + year;
                        customexpense_date.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }

        });

        //Set action bar title after getAndSetIntentData method
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("Search Data By Date");
        }


        custom_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str = customexpense_date.getText().toString().trim();

                Intent intent = new Intent(getApplicationContext(),Display_Custom_Expense.class);
                //intent.putExtra("name",name);
                intent.putExtra("message_key",str);
                //intent.putExtra("amount",amount);
                startActivity(intent);
                startActivity(intent);
            }
        });
    }
}