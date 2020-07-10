package com.example.expensemanager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Update_Entered_Data extends AppCompatActivity {

    EditText expense_name2,expense_amount2,expense_date2;
    Button button_updateExpense,button_deleteExpense;

    String id,name,amount,date_full;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__entered__data);

        expense_name2 = findViewById(R.id.expense_name2);
        expense_amount2 = findViewById(R.id.expense_amount2);
        expense_date2 = findViewById(R.id.expense_date2);
        button_updateExpense = findViewById(R.id.button_updateExpense);
        button_deleteExpense = findViewById(R.id.button_deleteExpense);

        //first we call this
        getAndSetIntentData();

        //Set action bar title after getAndSetIntentData method
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(name);
        }

        button_updateExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(Update_Entered_Data.this);
                //And only then we call this
                name = expense_name2.getText().toString().trim();
                amount = expense_amount2.getText().toString().trim();
                date_full = expense_date2.getText().toString().trim();
                myDB.updateData(id,name,amount,date_full);
            }
        });
        button_deleteExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDailog();
            }
        });

    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("name") &&
                getIntent().hasExtra("amount") && getIntent().hasExtra("date_full")){
            //Getting data from intent
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            amount = getIntent().getStringExtra("amount");
            date_full = getIntent().getStringExtra("date_full");

            //Setting intent data
            expense_name2.setText(name);
            expense_amount2.setText(amount);
            expense_date2.setText(date_full);
        }else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDailog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete "+ name + " ?");
        builder.setMessage("Are you sure you want to delete " + name + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(Update_Entered_Data.this);
                myDB.deleteOneRow(id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

}