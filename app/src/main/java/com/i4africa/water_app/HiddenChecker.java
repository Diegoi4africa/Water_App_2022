package com.i4africa.water_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
import java.util.ArrayList;

public class HiddenChecker extends AppCompatActivity {

    private final String FILENAME = "offlineCache";
    EditText name, surname;
    TextView output, arrOutput;
    private ArrayList<String> questionList, slqList;
    private Methods meth;
    ArrayList<String> content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidden_checker);
        meth = new Methods();
        output = findViewById(R.id.lblOutput);
        arrOutput = findViewById(R.id.arrOutput);
        content = new ArrayList<>();
    }

    public void submitToCache(View view) {
        Methods meth = new Methods();
        meth.getQuestions("Lactalis Factory", getApplicationContext(), "LactalisFactoryQuestions");
    }

    public void submitToCache2(View view) {
        Methods meth = new Methods();
        meth.getQuestions("Lactalis Warehouse", getApplicationContext(), "LactalisWarehouseQuestions");
    }

    public void readCache(View view) {
        output.setText("");
        Context context = getApplicationContext();
        String outputText = "";
        questionList = meth.getCachedQuestions(context, "LactalisFactoryQuestions");
        for (int i = 0; i < questionList.size(); i++) {
            outputText += questionList.get(i) + "\n";
        }
        System.out.println(outputText);
        output.setText(outputText);
    }

    public void readCache2(View view) {
        output.setText("");
        Context context = getApplicationContext();
        String outputText = "";
        questionList = meth.getCachedQuestions(context, "LactalisWarehouseQuestions");
        for (int i = 0; i < questionList.size(); i++) {
            outputText += questionList.get(i) + "\n";
        }
        System.out.println(outputText);
        output.setText(outputText);
    }

    public void showCacheFiles(View view) {
        arrOutput.setText("");
        Context context = getApplicationContext();
        String[] files = context.fileList();
        for (int i = 0; i < files.length; i++) {
            String file = files[i];
            arrOutput.append(file + "\n");
        }
    }

    public void clearOutput(View view) {
        output.setText("");
    }

    public void showUserLogs(View view) {

    }

    public void checkConnectivity(View view) {

        boolean connected = meth.isNetworkAvailable(getApplicationContext());
        output.setText("Is the Tablet connected: " + connected);
    }

    public void readTestingCache(View view) {
        Context context = getApplicationContext();
        output.setText("");
        FileInputStream fis = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            fis = context.openFileInput(FILENAME);

            InputStreamReader inputStreamReader =
                    new InputStreamReader(fis, StandardCharsets.UTF_8);

            try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                String line = reader.readLine();
                while (line != null) {
                    stringBuilder.append(line).append('\n');
                    content.add(line);
                    line = reader.readLine();

                }
                Toast.makeText(context, "Successfully Read Testing Cache", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                // Error occurred when opening raw file for reading.
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            String contents = stringBuilder.toString();
            output.setText(contents);
        }
    }

    public void createTestingCache(View view) {
        Context context = getApplicationContext();
        String[] testingArr = {};
        try {
            File.createTempFile(FILENAME, null, context.getCacheDir());
            File cacheFile = new File(context.getCacheDir(), FILENAME);
            FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            for (int i = 0; i < testingArr.length; i++) {
                String text = testingArr[i] + "\n";
                fos.write(text.getBytes());
            }
            Toast.makeText(context, "Successfully created CacheFile", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "ERR creating Cache File, please look at stacktrace", Toast.LENGTH_LONG).show();
        }
    }

    public void appendToCache(View view) {
        //TODO set parameters to pass in context, arrayList<String>, Set CacheFile to specific name.
        Context context = getApplicationContext();
        ArrayList<String> temp = new ArrayList<>();
        output.setText("");
        //TODO This code below reads current cache file to tempArrayList
        //TODO Add additional Error handling to ensure program doesn't crash when file is not found
        FileInputStream fis = null;
        try {
            fis = context.openFileInput(FILENAME);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        InputStreamReader inputStreamReader =
                new InputStreamReader(fis, StandardCharsets.UTF_8);
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line).append('\n');
                temp.add(line);
                line = reader.readLine();

            }
            Toast.makeText(context, "Successfully Read Testing Cache", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            // Error occurred when opening raw file for reading.
        } finally {
            String contents = stringBuilder.toString();
            output.setText(contents);
        }
        //TODO Change this to accept ArrayList to add to cache file from the submitted sqlArray
        for (int i = 4; i <= 10; i++) {
            String val = "Testing Line " + i;
            temp.add(val);
        }
        //TODO Create the temp file as the toBeSynced cache file, this code below writes the cache file
        try {
            File.createTempFile(FILENAME, null, context.getCacheDir());
            File cacheFile = new File(context.getCacheDir(), FILENAME);
            FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            for (int i = 0; i < temp.size(); i++) {
                String text = temp.get(i) + "\n";
                fos.write(text.getBytes());
            }
            Toast.makeText(context, "Successfully created CacheFile", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "ERR creating Cache File, please look at stacktrace", Toast.LENGTH_LONG).show();
        }

    }

    public void clearTestingCache(View view) {
        Context context = getApplicationContext();
        try {
            String[] files = context.fileList();
            context.deleteFile(FILENAME);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Could not delete file", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public void CreateALLCache(View view) {
        String[] calcCachFiles = {
                "LactalisFactoryBoilerSoftenerInletReading", "LactalisFactoryBoilerSoftenerOutletReading",
                "LactalisFactoryBoilerOneMeterReading", "LactalisFactoryBoilerTwoMeterReading",
                "LactalisFactoryMunicipalWaterMeterReading", "LactalisFactoryMunicipalTDSReading",
                "LactalisFactoryMunicipalOrgano_PhosphonateReading", "LactalisFactoryUHTCondenserCommonWaterReading",
                "LactalisFactoryIceDamMeterReading", "LactalisFactorySteriCondenserCommonMeterReading",
                "LactalisFactorySteriIceDamMeterReading", "LactalisWarehouseCondenserOneMeterReading",
                "LactalisWarehouseCondenserTwoMeterReading", "LactalisWarehouseMunicipalOrgano_PhosphonateReading",
                "LactalisWarehouseMunicipalTDSReading"
        };
        Context context = getApplicationContext();
        for (int i = 0; i < calcCachFiles.length; i++) {
            String cachFileName = calcCachFiles[i];
            try {
                File.createTempFile(cachFileName, null, context.getCacheDir());
                File cacheFile = new File(context.getCacheDir(), cachFileName);
                FileOutputStream fos = context.openFileOutput(cachFileName, Context.MODE_PRIVATE);
                String text = "";
                fos.write(text.getBytes());

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(context, "ERR creating Cache File, please look at stacktrace", Toast.LENGTH_LONG).show();
            }
        }
    }
}