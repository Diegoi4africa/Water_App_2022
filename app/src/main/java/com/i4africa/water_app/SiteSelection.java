package com.i4africa.water_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class SiteSelection extends AppCompatActivity {
    private String name, surname, username;
    private final String FILENAMELOGIN = "usrLoginSession";
    String filename = "myfile";
    String fileContents = "testing";
    private TextView pageTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_selection);
        Intent oldIntent = getIntent();
        name = oldIntent.getStringExtra("name");
        surname = oldIntent.getStringExtra("surname");
        username = oldIntent.getStringExtra("userName");
        pageTitle = findViewById(R.id.lbl_page_title);
        pageTitle.setText("Clients");
    }

    public void selectSiteCoega(View view) {


    }

    public void selectSiteCocaCola(View view) {

    }

    public void selectSiteOrion(View view) {

    }

    public void selectSiteLactalis(View view) {
        Intent siteSelection = new Intent(getApplicationContext(), LocationSelect.class);
        startActivity(siteSelection);
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

    public void openSettings(View view) {
        Intent settings = new Intent(getApplicationContext(), Settings.class);
        startActivity(settings);
    }
}