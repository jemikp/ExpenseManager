package com.example.expensemanager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class GetCustomDataMonthly extends AppCompatActivity {

    EditText getMonth;
    Button searchMonthlyData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_custom_data_monthly);

        getMonth = findViewById(R.id.getMonth);
        searchMonthlyData = findViewById(R.id.searchMonthlyData);

        //Set action bar title after getAndSetIntentData method
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("Search Data By Month");
        }


        searchMonthlyData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str1 = getMonth.getText().toString();
                Intent intent = new Intent(GetCustomDataMonthly.this,DisplayCustomMonthlyData.class);
                intent.putExtra("month_key",str1);
                startActivity(intent);
            }
        });


    }
}