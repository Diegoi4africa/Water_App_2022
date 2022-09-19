package com.i4africa.water_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class InputSelection extends AppCompatActivity {
    final int DAILY = 1;
    final int WEEKLY = 2;
    final int MONTHLY = 3;
    private TextView titleText;
    private int location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_selection);
        initComp();
    }

    private void initComp() {
        Intent get = getIntent();
        location = get.getIntExtra("location", 1);
        titleText = findViewById(R.id.lbl_page_title);
        titleText.setText("Recording Period");
    }

    public void selDailyInput(View view) {
        Intent compSelect = new Intent(getApplicationContext(), ComponentSelection.class);
        compSelect.putExtra("inputPeriod", DAILY);
        compSelect.putExtra("location", location);
        startActivity(compSelect);
    }

    public void selWeeklyInput(View view) {
        Intent compSelect = new Intent(getApplicationContext(), ComponentSelection.class);
        compSelect.putExtra("inputPeriod", WEEKLY);
        compSelect.putExtra("location", location);
        startActivity(compSelect);
    }

    public void selMonthlyInput(View view) {
        Intent compSelect = new Intent(getApplicationContext(), ComponentSelection.class);
        compSelect.putExtra("inputPeriod", MONTHLY);
        compSelect.putExtra("location", location);
        startActivity(compSelect);
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