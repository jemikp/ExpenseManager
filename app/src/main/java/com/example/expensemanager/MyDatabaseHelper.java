package com.example.expensemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Calendar;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "BookLibrary.db";
    private static final int DATABASE_VERSION = 1;

    //Add table name and columns
    private static final String TABLE_NAME = "my_expense";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "expense_name";
    private static final String COLUMN_AMOUNT = "expense_amount";
    private static final String COLUMN_DATE_FULL = "expense_date_full";
    private static final String COLUMN_DAY = "expense_day";
    private static final String COLUMN_MONTH = "expense_month";
    private static final String COLUMN_YEAR = "expense_year";

    MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME + " TEXT, " +
                        COLUMN_DATE_FULL + " TEXT, " +
                        COLUMN_AMOUNT + " INTEGER, " +
                        COLUMN_DAY + " TEXT, " +
                        COLUMN_MONTH + " TEXT, " +
                        COLUMN_YEAR + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addBook(String name, String date, int amount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues(); //store all our application data and pass through database table

        //key=COLUMN_NAME    id=data
        cv.put(COLUMN_NAME,name);
        cv.put(COLUMN_DATE_FULL,date);
        cv.put(COLUMN_AMOUNT,amount);


        String[] daate= date.split("/");

        String day = daate[0];
        String month = daate[1];
        String year = daate[2];

        cv.put(COLUMN_DAY,day);
        cv.put(COLUMN_MONTH,month);
        cv.put(COLUMN_YEAR,year);
        long result = db.insert(TABLE_NAME,null,cv);
        if(result == -1){
            Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context,"Added Successfully!!",Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    void updateData(String row_id, String name, String date, String amount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_DATE_FULL, date);
        cv.put(COLUMN_AMOUNT, amount);


        //cv.put(COLUMN_DAY,day);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }

    }

    Cursor getCustomData(String date){


        SQLiteDatabase db = this.getReadableDatabase();
        String date1 = date;



        String query = "SELECT * FROM "+ TABLE_NAME + " WHERE " + COLUMN_DATE_FULL + " LIKE '" + date1 +"';";
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    Cursor getCustomDataMonthly(String month){


        SQLiteDatabase db = this.getReadableDatabase();
        String month1 = month;



        String query = "SELECT * FROM "+ TABLE_NAME + " WHERE " + COLUMN_MONTH + " LIKE '" + month1 +"';";
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?",new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to delete..", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Successfully deleted..", Toast.LENGTH_SHORT).show();
        }
    }
    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

    Cursor getDailyData(){
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        String day1 = Integer.toString(day);

        String query = "SELECT * FROM "+ TABLE_NAME + " WHERE " + COLUMN_DAY + "=" + day + ";";
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    Cursor getDailyDataForGraph(){
        Calendar calendar = Calendar.getInstance();
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        String query = "SELECT expense_name FROM "+ TABLE_NAME + " WHERE " + COLUMN_DAY + "=" + day + ";";
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    Cursor getDailyDataForGraph1(){
        Calendar calendar = Calendar.getInstance();
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        String query = "SELECT " + COLUMN_AMOUNT +" FROM "+ TABLE_NAME + " WHERE " + COLUMN_DAY + "=" + day + ";";
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    Cursor getMonthlyData(){
        Calendar calendar = Calendar.getInstance();
        final int month = calendar.get(Calendar.MONTH) +1;

        String query = "SELECT * FROM "+ TABLE_NAME + " WHERE " + COLUMN_MONTH + "=" + month + ";";
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }


    Cursor getMonthlyDataForGraph(){
        Calendar calendar = Calendar.getInstance();
        final int month = calendar.get(Calendar.MONTH) +1;

        String query = "SELECT expense_name FROM "+ TABLE_NAME + " WHERE " + COLUMN_MONTH + "=" + month + ";";
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    Cursor getMonthlyDataForGraph1(){
        Calendar calendar = Calendar.getInstance();
        final int month = calendar.get(Calendar.MONTH) +1;

        String query = "SELECT " + COLUMN_AMOUNT +" FROM "+ TABLE_NAME + " WHERE " + COLUMN_MONTH + "=" + month + ";";
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }


    Cursor getYearlyData(){
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);

        String query = "SELECT * FROM "+ TABLE_NAME + " WHERE " + COLUMN_YEAR + "=" + year + ";";
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }


    Cursor getYearlyDataForGraph(){
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);

        String query = "SELECT expense_name FROM "+ TABLE_NAME + " WHERE " + COLUMN_YEAR + "=" + year + ";";
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    Cursor getYearlyDataForGraph1(){
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);

        String query = "SELECT " + COLUMN_AMOUNT +" FROM "+ TABLE_NAME + " WHERE " + COLUMN_YEAR + "=" + year + ";";
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

}
