package com.example.expensemanager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import java.io.IOException;
import java.io.InputStream;

public class TravelExpense extends AppCompatActivity implements View.OnClickListener {

    private static final int SELECT_PICTURE = 100;
    private static final String TAG = "StoreImageActivity";

    private Button btnOpenGallery, btnSaveImage, btnLoadImage;
    private TextView tvStatus;
    private AppCompatImageView imgView, imgLoaded;
    private Uri selectedImageUri;

    private DBHelper dbHelper;

    EditText expense_name,expense_amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_expense);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        // Find the views...
        btnOpenGallery = findViewById(R.id.btnSelectImage);
        btnSaveImage = findViewById(R.id.btnSaveImage);
        btnLoadImage = findViewById(R.id.btnLoadImage);
        imgLoaded = findViewById(R.id.loadedImg);
        imgView = findViewById(R.id.imgView);
        tvStatus = findViewById(R.id.tvStatus);
        expense_name = findViewById(R.id.expense_name_travel);
        expense_amount = findViewById(R.id.expense_amount_travel);

        btnOpenGallery.setOnClickListener((View.OnClickListener) this);
        btnSaveImage.setOnClickListener((View.OnClickListener) this);
        btnLoadImage.setOnClickListener(this);

        // Create the Database helper object
        dbHelper = new DBHelper(this);

        //Set action bar title after getAndSetIntentData method
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("Business Travel Expense");
        }

    }

    void showMessage(final String message) {
        tvStatus.post(new Runnable() {
            @Override
            public void run() {
                tvStatus.setText(message);
            }
        });
    }

    // Choose an image from Gallery
    void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    imgView.setImageURI(selectedImageUri);
                }
            }
        }
    }



    @Override
    public void onClick(View v) {
        if (v == btnOpenGallery)
            openImageChooser();

        if (v == btnSaveImage) {
            // Saving to Database...
            if (saveImageInDB()) {
                showMessage("Image Saved in Database...");
            }
        }

        if (v == btnLoadImage)
            loadImageFromDB();
    }

    boolean saveImageInDB() {

        try {
            String name = expense_name.getText().toString();
            String amount = expense_amount.getText().toString();
            dbHelper.open();
            InputStream iStream = getContentResolver().openInputStream(selectedImageUri);
            byte[] inputData = Utils.getBytes(iStream);
            dbHelper.insertImage(inputData,name,amount);
            dbHelper.close();
            return true;
        } catch (IOException ioe) {
            Log.e(TAG, "<saveImageInDB> Error : " + ioe.getLocalizedMessage());
            dbHelper.close();
            return false;
        }

    }

    void loadImageFromDB() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    dbHelper.open();
                    final byte[] bytes = dbHelper.retreiveImageFromDB();
                    dbHelper.close();
                    // Show Image from DB in ImageView
                    imgLoaded.post(new Runnable() {
                        @Override
                        public void run() {
                            imgLoaded.setImageBitmap(Utils.getImage(bytes));
                            showMessage("Image Loaded from Database");
                        }
                    });
                } catch (Exception e) {
                    Log.e(TAG, "<loadImageFromDB> Error : " + e.getLocalizedMessage());
                    dbHelper.close();
                }
            }
        }).start();
    }

}