package com.i4africa.water_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Settings extends AppCompatActivity {

    private TextView config_out, titleText;
    private CheckBox cbLactalisFactory, cbLactalisWarehouse;
    private boolean lactalisFactory, lactalisWarehouse;
    private ImageView settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initComp();
        titleText.setText("Configuration");

    }

    public void config(View view) {
        Methods meth = new Methods();
        appendConfig_out("Wait for Sync to complete");

        String[] calcCachFiles = meth.getStringArr();

        int[] calcCacheFilesQuestionID = {11, 13, 18, 20, 63, 56, 61, 92, 101, 121, 130, 151, 162, 54, 48};

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

        for (int i = 0; i < calcCacheFilesQuestionID.length; i++) {
            int questionID = calcCacheFilesQuestionID[i];
            String cacheFileName = calcCachFiles[i];
            //TODO - Fetch Latest value for each Question
            //TODO - Set value to cache File
        }

        appendConfig_out("Cache Files created Successfully.");

        if (lactalisFactory) {
            Methods meth2 = new Methods();
            meth2.getQuestions("Lactalis Factory", getApplicationContext(), "LactalisFactoryQuestions");
            appendConfig_out("Lactalis Factory Question Successfully Cached");
        }

        if (lactalisWarehouse) {
            Methods meth3 = new Methods();
            meth3.getQuestions("Lactalis Warehouse", getApplicationContext(), "LactalisWarehouseQuestions");
            appendConfig_out("Lactalis Warehouse Question Successfully Cached");
        }

        appendConfig_out("Sync Complete");

    }

    private void appendConfig_out(String text) {
        config_out.append("\n" + text);
    }

    private final View.OnClickListener checkBox = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            boolean checked = ((CheckBox) v).isChecked();
            switch (v.getId()) {
                case R.id.cb_Lactalis_Factory:
                    lactalisFactory = checked;
                    break;
                case R.id.cb_Lactalis_Warehouse:
                    lactalisWarehouse = checked;
                    break;
            }
        }
    };

    private final View.OnClickListener hiddenViewer = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            touch++;

            if (touch == 4) {
                Toast.makeText(getApplicationContext(), "6 clicks away", Toast.LENGTH_SHORT).show();
            }
            if (touch == 5) {
                Toast.makeText(getApplicationContext(), "5 clicks away", Toast.LENGTH_SHORT).show();
            }
            if (touch == 6) {
                Toast.makeText(getApplicationContext(), "4 clicks away", Toast.LENGTH_SHORT).show();
            }
            if (touch == 7) {
                Toast.makeText(getApplicationContext(), "3 clicks away", Toast.LENGTH_SHORT).show();
            }
            if (touch == 8) {
                Toast.makeText(getApplicationContext(), "2 clicks away", Toast.LENGTH_SHORT).show();
            }
            if (touch == 9) {
                Toast.makeText(getApplicationContext(), "1 clicks away", Toast.LENGTH_SHORT).show();
            }
            if (touch == 10) {
                touch = 0;
                Intent hiddenCache = new Intent(getApplicationContext(), HiddenChecker.class);
                startActivity(hiddenCache);
            }
        }
    };

    public void sync_data(View view) {
        Intent sync = new Intent(getApplicationContext(), Sync.class);
        startActivity(sync);
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

    public void openSettings(View view) {
        Intent settings = new Intent(getApplicationContext(), Settings.class);
        startActivity(settings);
    }

    private void initComp() {
        config_out = findViewById(R.id.lbl_Config_output);
        cbLactalisFactory = findViewById(R.id.cb_Lactalis_Factory);
        cbLactalisWarehouse = findViewById(R.id.cb_Lactalis_Warehouse);
        cbLactalisFactory.setOnClickListener(checkBox);
        cbLactalisWarehouse.setOnClickListener(checkBox);
        titleText = findViewById(R.id.lbl_page_title);
        titleText.setOnClickListener(hiddenViewer);
        settings = findViewById(R.id.icon_settings);
        settings.setVisibility(View.GONE);

    }

    private int touch = 0;


}