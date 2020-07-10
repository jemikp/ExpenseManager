package com.example.expensemanager;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Third#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Third extends Fragment {

    private AnyChartView yearly_chart;

    String months[] = {"January","February","March"};
    int[] earnings = {500, 800, 2000};
    MyDatabaseHelper myDB;

    Activity DisplayYearlyExpenses;
    Button display_yearly_data;

    public Third() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Third newInstance(String param1, String param2) {
        Third fragment = new Third();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_third, container, false);

        yearly_chart = view.findViewById(R.id.yearly_chart);
        display_yearly_data = view.findViewById(R.id.display_yearly_data);


        myDB = new MyDatabaseHelper(getActivity());


        Cursor cursor2 = myDB.getYearlyData();
        int x = cursor2.getCount();
        String names[] = new String[x];
        String amount[] = new String[x];
        int count1 = 0;
        int count2 = 0;

        Cursor cursor = myDB.getYearlyDataForGraph();
        if(cursor.getCount() == 0){
            Toast.makeText(DisplayYearlyExpenses, "No data.Please enter data", Toast.LENGTH_SHORT).show();
        }else {
            if (cursor.moveToFirst()) {
                names = new String[cursor.getCount()];
                int colIndex = cursor.getColumnIndex("COLUMN_NAME");
                do {
                    names[cursor.getPosition()] = cursor.getString(colIndex+1);
                } while (cursor.moveToNext());
                count1 = 1;
            }
        }

        Cursor cursor1 = myDB.getYearlyDataForGraph1();
        if (cursor1.getCount() == 0){
            Toast.makeText(DisplayYearlyExpenses, "No amount,empty value", Toast.LENGTH_SHORT).show();
        }else {
            if (cursor1.moveToFirst()) {
                amount = new String[cursor1.getCount()];
                int colIndex = cursor1.getColumnIndex("COLUMN_AMOUNT");
                do {
                    amount[cursor1.getPosition()] = cursor1.getString(colIndex+1);
                } while (cursor1.moveToNext());
                count2 = 1;
            }
        }

        int[] amount1= new int[x];
        for(int j=0;j<amount.length;j++){
            amount1[j] = Integer.parseInt(amount[j]);
        }

        Pie pie = AnyChart.pie();
        List<DataEntry> dataEntries = new ArrayList<>();

        if(count1==1 && count2==1) {
            for (int i = 0; i < names.length; i++) {
                dataEntries.add(new ValueDataEntry(names[i], amount1[i]));
            }
            pie.data(dataEntries);
            pie.title("Current Year Expenses");
            yearly_chart.setChart(pie);
        }else {
            for (int i = 0; i < months.length; i++) {
                dataEntries.add(new ValueDataEntry(months[i], earnings[i]));
            }
            pie.data(dataEntries);
            pie.title("Current Year Expenses");
            yearly_chart.setChart(pie);
        }


        DisplayYearlyExpenses = getActivity();

        display_yearly_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), DisplayYearlyExpenses.class);
                getActivity().startActivity(intent);
            }
        });


        return view;
    }
}