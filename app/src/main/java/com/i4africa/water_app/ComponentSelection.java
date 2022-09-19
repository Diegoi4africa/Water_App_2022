package com.i4africa.water_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class ComponentSelection extends AppCompatActivity {
    private int periodSelect;
    private int location;
    private final int WAREHOUSE = 2;
    private final int FACTORY = 1;
    private Calendar calendar;
    private ArrayList<String> questionList;
    private LinearLayoutCompat layout_boiler, layout_municipalWater, layout_coolingSystem, layout_uhtCondensor, layout_steriCondensor, layout_uhtIceDamn, layout_steriIceDam,
            layout_sterilizationTower;
    private TextView titleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_component_selection);
        Intent testing = getIntent();
        periodSelect = testing.getIntExtra("inputPeriod", 1);
        location = testing.getIntExtra("location", 1);

        Methods meth = new Methods();
        if (location == 1) {
            Context context = getApplicationContext();
            questionList = meth.getCachedQuestions(context, "LactalisFactoryQuestions");
        }

        if (location == 2) {
            Context context = getApplicationContext();
            questionList = meth.getCachedQuestions(context, "LactalisWarehouseQuestions");
        }
        initComp();


        setLocationVisual(location);

    }

    public void selBoiler(View view) {
        calendar = Calendar.getInstance();
        Log.println(Log.ASSERT, "SelComp", "" + calendar.getTimeInMillis());
        Intent boilerQuestions = new Intent(getApplicationContext(), LactalisBoilerQuestions.class);
        boilerQuestions.putExtra("inputPeriod", periodSelect);
        boilerQuestions.putExtra("questionList", questionList);
        startActivity(boilerQuestions);
    }

    public void selMunicipalWater(View view) {
        if (location == FACTORY) {
            Intent municipalWater = new Intent(getApplicationContext(), lactalisMunicipalWaterQuestions.class);
            municipalWater.putExtra("inputPeriod", periodSelect);
            municipalWater.putExtra("questionList", questionList);
            startActivity(municipalWater);
        }
        if (location == WAREHOUSE) {
            Intent municipalWater = new Intent(getApplicationContext(), LactalisWarehouseMunicpalWaterQuestions.class);
            municipalWater.putExtra("inputPeriod", periodSelect);
            municipalWater.putExtra("questionList", questionList);
            startActivity(municipalWater);
        }
    }

    public void selCoolingSystem(View view) {
        Intent coolingSystem = new Intent(getApplicationContext(), LactalisWarehouseCoolingSystemQuestions.class);
        coolingSystem.putExtra("inputPeriod", periodSelect);
        coolingSystem.putExtra("questionList", questionList);
        startActivity(coolingSystem);
    }

    public void selUHTCondensor(View view) {
        Intent uhtCondensor = new Intent(getApplicationContext(), LactalisCoolingSystemQuestions.class);
        uhtCondensor.putExtra("inputPeriod", periodSelect);
        uhtCondensor.putExtra("questionList", questionList);
        startActivity(uhtCondensor);
    }

    public void selUHTIceDam(View view) {
        Intent uhtIceDam = new Intent(getApplicationContext(), LactalisUHTIceDam.class);
        uhtIceDam.putExtra("inputPeriod", periodSelect);
        uhtIceDam.putExtra("questionList", questionList);
        startActivity(uhtIceDam);
    }

    public void selSteriCondensor(View view) {
        Intent steriCondensor = new Intent(getApplicationContext(), LactalisSteriCondensorQuestions.class);
        steriCondensor.putExtra("inputPeriod", periodSelect);
        steriCondensor.putExtra("questionList", questionList);
        startActivity(steriCondensor);
    }

    public void selSteriIceDam(View view) {
        Intent steriIceDam = new Intent(getApplicationContext(), LactalisSteriIceDam.class);
        steriIceDam.putExtra("inputPeriod", periodSelect);
        steriIceDam.putExtra("questionList", questionList);
        startActivity(steriIceDam);
    }

    public void selSterilizationTower(View view) {
        Intent sterilizationTower = new Intent(getApplicationContext(), LactalisSteriSterilizationTower.class);
        sterilizationTower.putExtra("inputPeriod", periodSelect);
        sterilizationTower.putExtra("questionList", questionList);
        startActivity(sterilizationTower);
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

    public void setLocationVisual(int location) {
        if (location == FACTORY) {
            layout_coolingSystem.setVisibility(View.GONE);
            layout_sterilizationTower.setVisibility(View.VISIBLE);
            layout_municipalWater.setVisibility(View.VISIBLE);
            layout_boiler.setVisibility(View.VISIBLE);
            layout_uhtCondensor.setVisibility(View.VISIBLE);
            layout_uhtIceDamn.setVisibility(View.VISIBLE);
            layout_steriCondensor.setVisibility(View.VISIBLE);
            layout_steriIceDam.setVisibility(View.VISIBLE);
            layout_sterilizationTower.setVisibility(View.VISIBLE);
        }
        if (location == WAREHOUSE) {
            layout_coolingSystem.setVisibility(View.VISIBLE);
            layout_sterilizationTower.setVisibility(View.GONE);
            layout_municipalWater.setVisibility(View.VISIBLE);
            layout_boiler.setVisibility(View.GONE);
            layout_uhtCondensor.setVisibility(View.GONE);
            layout_uhtIceDamn.setVisibility(View.GONE);
            layout_steriCondensor.setVisibility(View.GONE);
            layout_steriIceDam.setVisibility(View.GONE);
            layout_sterilizationTower.setVisibility(View.GONE);

        }
    }

    int touch = 0;

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

    private void initComp() {
        layout_boiler = findViewById(R.id.sel_Boiler);
        layout_municipalWater = findViewById(R.id.sel_municipalWater);
        layout_coolingSystem = findViewById(R.id.sel_CoolingSystem);
        layout_uhtCondensor = findViewById(R.id.sel_UHTCondensor);
        layout_steriCondensor = findViewById(R.id.sel_SteriCondensor);
        layout_uhtIceDamn = findViewById(R.id.sel_UHTIceDam);
        layout_steriIceDam = findViewById(R.id.sel_SteriIceDam);
        layout_sterilizationTower = findViewById(R.id.sel_SterilizationTower);
        titleText = findViewById(R.id.lbl_page_title);
        titleText.setText("Select Components");
        titleText.setOnClickListener(hiddenViewer);
    }
}