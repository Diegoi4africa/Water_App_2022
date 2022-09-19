package com.i4africa.water_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public class LocationSelect extends AppCompatActivity {
    private final int WAREHOUSE = 2;
    private final int FACTORY = 1;
    private TextView titleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_select);
        initComp();
    }

    private void initComp() {
        titleText = findViewById(R.id.lbl_page_title);
        titleText.setText("Location");
    }

    public void selLactalisWarehouse(View view) {
        boolean exists;
        Context context = getApplicationContext();
        String[] files = context.fileList();

        exists = Arrays.asList(files).contains("LactalisWarehouseQuestions");

        if (!exists) {
            Methods meth = new Methods();
            meth.getQuestions("Lactalis Warehouse", getApplicationContext(), "LactalisWarehouseQuestions");
            System.out.println("File did not exist");
            Log.println(Log.ASSERT, "FML level", "File did not exist");
        } else {
            Log.println(Log.ASSERT, "good level", "File did exist");
        }
        Intent selelctPeriod = new Intent(getApplicationContext(), InputSelection.class);
        selelctPeriod.putExtra("location", WAREHOUSE);
        startActivity(selelctPeriod);
    }

    public void selLactalisFactory(View view) {
        boolean exists;
        Context context = getApplicationContext();
        String[] files = context.fileList();

        exists = Arrays.asList(files).contains("LactalisWarehouseQuestions");

        if (!exists) {
            Methods meth = new Methods();
            meth.getQuestions("Lactalis Factory", getApplicationContext(), "LactalisFactoryQuestions");
            System.out.println("File did not exist");
            Log.println(Log.ASSERT, "FML level", "File did not exist");
        } else {
            Log.println(Log.ASSERT, "good level", "File did exist");
        }


        Intent selelctPeriod = new Intent(getApplicationContext(), InputSelection.class);
        selelctPeriod.putExtra("location", FACTORY);
        startActivity(selelctPeriod);
    }

    public void logout(View view) {
        final String FILENAMELOGIN = "usrLoginSession";
        Context context = getApplicationContext();
        try {
            String[] files = context.fileList();
            for (int i = 0; i < files.length; i++) {
                context.deleteFile(files[i]);
            }

            Toast.makeText(getApplicationContext(), "You have been logged out", Toast.LENGTH_LONG).show();
            Intent login = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(login);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Could not delete file", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public void sync_data(View view) {
        Intent sync = new Intent(getApplicationContext(), Sync.class);
        startActivity(sync);
    }

}