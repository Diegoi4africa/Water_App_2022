package com.i4africa.water_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Calendar;

public class LactalisCoolingSystemQuestions extends AppCompatActivity {
    private final String FORMID = "Lactalis_Factory_UHT_Condenser_Form";

    private EditText que_lactalis_factory_UHT_Condenser_1_tds, que_lactalis_factory_UHT_Condenser_1_ph, que_lactalis_factory_UHT_Condenser_1_hardness, que_lactalis_factory_UHT_Condenser_1_chlorides,
            que_lactalis_factory_UHT_Condenser_1_alk, que_lactalis_factory_UHT_Condenser_1_bch, que_lactalis_factory_UHT_Condenser_1_phosphonate,que_lactalis_factory_UHT_Condenser_1_molybate,
            que_lactalis_factory_UHT_Condenser_2_tds, que_lactalis_factory_UHT_Condenser_2_ph, que_lactalis_factory_UHT_Condenser_2_hardness, que_lactalis_factory_UHT_Condenser_2_chlorides,
            que_lactalis_factory_UHT_Condenser_2_alk, que_lactalis_factory_UHT_Condenser_2_bch, que_lactalis_factory_UHT_Condenser_2_phosphonate,que_lactalis_factory_UHT_Condenser_2_molybate,
            que_lactalis_factory_UHT_Condenser_3_tds, que_lactalis_factory_UHT_Condenser_3_hardness, que_lactalis_factory_UHT_Condenser_3_ph, que_lactalis_factory_UHT_Condenser_3_chlorides,
            que_lactalis_factory_UHT_Condenser_3_alk, que_lactalis_factory_UHT_Condenser_3_bch, que_lactalis_factory_UHT_Condenser_3_phosphonate,que_lactalis_factory_UHT_Condenser_3_molybate,
            que_lactalis_factory_UHT_Condenser_Common_water_meter,que_lactalis_factory_VTIS_1_tds, que_lactalis_factory_VTIS_condenser_1_ph, que_lactalis_factory_VTIS_condenser_1_hardness,
            que_lactalis_factory_VTIS_condenser_1_chlorides, que_lactalis_factory_VTIS_condenser_1_alk, que_lactalis_factory_VTIS_condenser_1_bch, que_lactalis_factory_VTIS_condenser_1_organophosphonate,
            que_lactalis_factory_VTIS_2_tds, que_lactalis_factory_VTIS_condenser_2_ph, que_lactalis_factory_VTIS_condenser_2_hardness, que_lactalis_factory_VTIS_condenser_2_chlorides,
            que_lactalis_factory_VTIS_condenser_2_alk, que_lactalis_factory_VTIS_condenser_2_bch, que_lactalis_factory_VTIS_condenser_2_organophosphonate, que_lactalis_factory_VTIS_common_meter,
            que_lactalis_factory_VTIS_steam_condensate_ph, que_lactalis_factory_VTIS_steam_condensate_tds, que_lactalis_factory_VTIS_steam_condensate_hardness, cooling_system_comments,que_lactalis_factory_growth_milk_cooling_tower_tds,
            que_lactalis_factory_growth_milk_cooling_tower_ph, que_lactalis_factory_growth_milk_cooling_tower_hardness, que_lactalis_factory_growth_milk_cooling_tower_chlorides,
            que_lactalis_factory_growth_milk_cooling_tower_alk, que_lactalis_factory_growth_milk_cooling_tower_bch, que_lactalis_factory_growth_milk_cooling_tower_organo_phosphonate, que_lactalis_factory_growth_milk_cooling_tower_water_reading;

    private TextInputLayout que_lactalis_factory_UHT_Condenser_1_tds_cont, que_lactalis_factory_UHT_Condenser_1_ph_cont, que_lactalis_factory_UHT_Condenser_1_hardness_cont, que_lactalis_factory_UHT_Condenser_1_chlorides_cont,
            que_lactalis_factory_UHT_Condenser_1_alk_cont, que_lactalis_factory_UHT_Condenser_1_bch_cont, que_lactalis_factory_UHT_Condenser_1_phosphonate_cont,que_lactalis_factory_UHT_Condenser_1_molybate_cont,
            que_lactalis_factory_UHT_Condenser_2_tds_cont, que_lactalis_factory_UHT_Condenser_2_ph_cont, que_lactalis_factory_UHT_Condenser_2_hardness_cont, que_lactalis_factory_UHT_Condenser_2_chlorides_cont,
            que_lactalis_factory_UHT_Condenser_2_alk_cont, que_lactalis_factory_UHT_Condenser_2_bch_cont, que_lactalis_factory_UHT_Condenser_2_phosphonate_cont,que_lactalis_factory_UHT_Condenser_2_molybate_cont,
            que_lactalis_factory_UHT_Condenser_3_tds_cont, que_lactalis_factory_UHT_Condenser_3_hardness_cont, que_lactalis_factory_UHT_Condenser_3_ph_cont, que_lactalis_factory_UHT_Condenser_3_chlorides_cont,que_lactalis_factory_UHT_Condenser_3_molybate_cont,
            que_lactalis_factory_UHT_Condenser_3_alk_cont, que_lactalis_factory_UHT_Condenser_3_bch_cont, que_lactalis_factory_UHT_Condenser_3_phosphonate_cont, que_lactalis_factory_UHT_Condenser_Common_water_meter_cont, que_lactalis_factory_VTIS_1_tds_cont,
            que_lactalis_factory_VTIS_condenser_1_ph_cont, que_lactalis_factory_VTIS_condenser_1_hardness_cont, que_lactalis_factory_VTIS_condenser_1_chlorides_cont, que_lactalis_factory_VTIS_condenser_1_alk_cont, que_lactalis_factory_VTIS_condenser_1_bch_cont,
            que_lactalis_factory_VTIS_condenser_1_organophosphonate_cont, que_lactalis_factory_VTIS_2_tds_cont, que_lactalis_factory_VTIS_condenser_2_ph_cont, que_lactalis_factory_VTIS_condenser_2_hardness_cont, que_lactalis_factory_VTIS_condenser_2_chlorides_cont,
            que_lactalis_factory_VTIS_condenser_2_alk_cont, que_lactalis_factory_VTIS_condenser_2_bch_cont, que_lactalis_factory_VTIS_condenser_2_organophosphonate_cont, que_lactalis_factory_VTIS_common_meter_cont,
            que_lactalis_factory_VTIS_steam_condensate_ph_cont, que_lactalis_factory_VTIS_steam_condensate_tds_cont, que_lactalis_factory_VTIS_steam_condensate_hardness_cont,que_lactalis_factory_growth_milk_cooling_tower_tds_cont,
            que_lactalis_factory_growth_milk_cooling_tower_ph_cont, que_lactalis_factory_growth_milk_cooling_tower_hardness_cont, que_lactalis_factory_growth_milk_cooling_tower_chlorides_cont, que_lactalis_factory_growth_milk_cooling_tower_alk_cont,
            que_lactalis_factory_growth_milk_cooling_tower_bch_cont, que_lactalis_factory_growth_milk_cooling_tower_organo_phosphonate_cont,que_lactalis_factory_growth_milk_cooling_tower_water_meter_cont ;

    private double que_lactalis_factory_UHT_Condenser_1_tds_val, que_lactalis_factory_UHT_Condenser_1_ph_val, que_lactalis_factory_UHT_Condenser_1_hardness_val, que_lactalis_factory_UHT_Condenser_1_chlorides_val,
            que_lactalis_factory_UHT_Condenser_1_alk_val, que_lactalis_factory_UHT_Condenser_1_bch_val, que_lactalis_factory_UHT_Condenser_1_phosphonate_val,que_lactalis_factory_UHT_Condenser_1_molybate_val,
            que_lactalis_factory_UHT_Condenser_2_tds_val, que_lactalis_factory_UHT_Condenser_2_ph_val, que_lactalis_factory_UHT_Condenser_2_hardness_val, que_lactalis_factory_UHT_Condenser_2_chlorides_val,
            que_lactalis_factory_UHT_Condenser_2_alk_val, que_lactalis_factory_UHT_Condenser_2_bch_val, que_lactalis_factory_UHT_Condenser_2_phosphonate_val,que_lactalis_factory_UHT_Condenser_2_molybate_val,
            que_lactalis_factory_UHT_Condenser_3_tds_val, que_lactalis_factory_UHT_Condenser_3_hardness_val, que_lactalis_factory_UHT_Condenser_3_ph_val, que_lactalis_factory_UHT_Condenser_3_chlorides_val,
            que_lactalis_factory_UHT_Condenser_3_alk_val, que_lactalis_factory_UHT_Condenser_3_bch_val, que_lactalis_factory_UHT_Condenser_3_phosphonate_val,que_lactalis_factory_UHT_Condenser_3_molybate_val,
            que_lactalis_factory_UHT_Condenser_Common_water_meter_val,que_lactalis_factory_VTIS_1_tds_val, que_lactalis_factory_VTIS_condenser_1_ph_val, que_lactalis_factory_VTIS_condenser_1_hardness_val,
            que_lactalis_factory_VTIS_condenser_1_chlorides_val, que_lactalis_factory_VTIS_condenser_1_alk_val, que_lactalis_factory_VTIS_condenser_1_bch_val, que_lactalis_factory_VTIS_condenser_1_organophosphonate_val,
            que_lactalis_factory_VTIS_2_tds_val, que_lactalis_factory_VTIS_condenser_2_ph_val, que_lactalis_factory_VTIS_condenser_2_hardness_val, que_lactalis_factory_VTIS_condenser_2_chlorides_val,
            que_lactalis_factory_VTIS_condenser_2_alk_val, que_lactalis_factory_VTIS_condenser_2_bch_val, que_lactalis_factory_VTIS_condenser_2_organophosphonate_val, que_lactalis_factory_VTIS_common_meter_val,
            que_lactalis_factory_VTIS_steam_condensate_ph_val, que_lactalis_factory_VTIS_steam_condensate_tds_val, que_lactalis_factory_VTIS_steam_condensate_hardness_val,que_lactalis_factory_growth_milk_cooling_tower_tds_val,
            que_lactalis_factory_growth_milk_cooling_tower_ph_val, que_lactalis_factory_growth_milk_cooling_tower_hardness_val, que_lactalis_factory_growth_milk_cooling_tower_chlorides_val, que_lactalis_factory_growth_milk_cooling_tower_alk_val,
            que_lactalis_factory_growth_milk_cooling_tower_bch_val, que_lactalis_factory_growth_milk_cooling_tower_organo_phosphonate_val, que_lactalis_factory_growth_milk_cooling_tower_water_reading_val;

    private double calc_lactalis_factory_UHT_Condenser_1_phosphonate_less_municipal, calc_lactalis_factory_UHT_Condenser_1_cycles_of_concentration,
            calc_lactalis_factory_UHT_Condenser_2_phosphonate_less_municipal, calc_lactalis_factory_UHT_Condenser_2_cycles_of_concentration,
            calc_lactalis_factory_UHT_Condenser_3_phosphonate_less_municipal, calc_lactalis_factory_UHT_Condenser_3_cycles_of_concentration,
            calc_lactalis_factory_UHT_Condenser_common_water_usage, calc_lactalis_factory_VTIS_water_consumption,calc_lactalis_factory_growth_milk_cooling_tower_water_consumption;

    private ArrayList<String> questionList, slqList;

    private CheckBox cb_daily, cb_weekly, cb_monthly;

    private int userID;
    private final String FILENAMELOGIN = "usrLoginSession";
    private Methods meth;

    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day, hour, min, sec;
    private String date, dateTime;
    private TextView dateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lactalis_cooling_system_questions);
        meth = new Methods();
        initComp();
        Intent oldIntent = getIntent();
        recPeriod(oldIntent);
        questionList = new ArrayList<>();
        slqList = new ArrayList<>();
        readQuestion(oldIntent);
        setDate();
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

    public void submitLactalisFactoryCoolingSystem(View view) {
        que_lactalis_factory_UHT_Condenser_1_tds_val = setVal(que_lactalis_factory_UHT_Condenser_1_tds, que_lactalis_factory_UHT_Condenser_1_tds_val);
        que_lactalis_factory_UHT_Condenser_1_ph_val = setVal(que_lactalis_factory_UHT_Condenser_1_ph, que_lactalis_factory_UHT_Condenser_1_ph_val);
        que_lactalis_factory_UHT_Condenser_1_hardness_val = setVal(que_lactalis_factory_UHT_Condenser_1_hardness, que_lactalis_factory_UHT_Condenser_1_hardness_val);
        que_lactalis_factory_UHT_Condenser_1_chlorides_val = setVal(que_lactalis_factory_UHT_Condenser_1_chlorides, que_lactalis_factory_UHT_Condenser_1_chlorides_val);
        que_lactalis_factory_UHT_Condenser_1_alk_val = setVal(que_lactalis_factory_UHT_Condenser_1_alk, que_lactalis_factory_UHT_Condenser_1_alk_val);
        que_lactalis_factory_UHT_Condenser_1_bch_val = setVal(que_lactalis_factory_UHT_Condenser_1_bch, que_lactalis_factory_UHT_Condenser_1_bch_val);
        que_lactalis_factory_UHT_Condenser_1_phosphonate_val = setVal(que_lactalis_factory_UHT_Condenser_1_phosphonate, que_lactalis_factory_UHT_Condenser_1_phosphonate_val);
        que_lactalis_factory_UHT_Condenser_1_molybate_val = setVal(que_lactalis_factory_UHT_Condenser_1_molybate, que_lactalis_factory_UHT_Condenser_1_molybate_val);

        que_lactalis_factory_UHT_Condenser_2_tds_val = setVal(que_lactalis_factory_UHT_Condenser_2_tds, que_lactalis_factory_UHT_Condenser_2_tds_val);
        que_lactalis_factory_UHT_Condenser_2_ph_val = setVal(que_lactalis_factory_UHT_Condenser_2_ph, que_lactalis_factory_UHT_Condenser_2_ph_val);
        que_lactalis_factory_UHT_Condenser_2_hardness_val = setVal(que_lactalis_factory_UHT_Condenser_2_hardness, que_lactalis_factory_UHT_Condenser_2_hardness_val);
        que_lactalis_factory_UHT_Condenser_2_chlorides_val = setVal(que_lactalis_factory_UHT_Condenser_2_chlorides, que_lactalis_factory_UHT_Condenser_2_chlorides_val);
        que_lactalis_factory_UHT_Condenser_2_alk_val = setVal(que_lactalis_factory_UHT_Condenser_2_alk, que_lactalis_factory_UHT_Condenser_2_alk_val);
        que_lactalis_factory_UHT_Condenser_2_bch_val = setVal(que_lactalis_factory_UHT_Condenser_2_bch, que_lactalis_factory_UHT_Condenser_2_bch_val);
        que_lactalis_factory_UHT_Condenser_2_phosphonate_val = setVal(que_lactalis_factory_UHT_Condenser_2_phosphonate, que_lactalis_factory_UHT_Condenser_2_phosphonate_val);
        que_lactalis_factory_UHT_Condenser_2_molybate_val = setVal(que_lactalis_factory_UHT_Condenser_2_molybate, que_lactalis_factory_UHT_Condenser_2_molybate_val);

        que_lactalis_factory_UHT_Condenser_3_tds_val = setVal(que_lactalis_factory_UHT_Condenser_3_tds, que_lactalis_factory_UHT_Condenser_3_tds_val);
        que_lactalis_factory_UHT_Condenser_3_ph_val = setVal(que_lactalis_factory_UHT_Condenser_3_ph, que_lactalis_factory_UHT_Condenser_3_ph_val);
        que_lactalis_factory_UHT_Condenser_3_hardness_val = setVal(que_lactalis_factory_UHT_Condenser_3_hardness, que_lactalis_factory_UHT_Condenser_3_hardness_val);
        que_lactalis_factory_UHT_Condenser_3_chlorides_val = setVal(que_lactalis_factory_UHT_Condenser_3_chlorides, que_lactalis_factory_UHT_Condenser_3_chlorides_val);
        que_lactalis_factory_UHT_Condenser_3_alk_val = setVal(que_lactalis_factory_UHT_Condenser_3_alk, que_lactalis_factory_UHT_Condenser_3_alk_val);
        que_lactalis_factory_UHT_Condenser_3_bch_val = setVal(que_lactalis_factory_UHT_Condenser_3_bch, que_lactalis_factory_UHT_Condenser_3_bch_val);
        que_lactalis_factory_UHT_Condenser_3_phosphonate_val = setVal(que_lactalis_factory_UHT_Condenser_3_phosphonate, que_lactalis_factory_UHT_Condenser_3_phosphonate_val);
        que_lactalis_factory_UHT_Condenser_3_molybate_val = setVal(que_lactalis_factory_UHT_Condenser_3_molybate, que_lactalis_factory_UHT_Condenser_3_molybate_val);

        que_lactalis_factory_UHT_Condenser_Common_water_meter_val = setVal(que_lactalis_factory_UHT_Condenser_Common_water_meter, que_lactalis_factory_UHT_Condenser_Common_water_meter_val);

        que_lactalis_factory_VTIS_1_tds_val = setVal(que_lactalis_factory_VTIS_1_tds, que_lactalis_factory_VTIS_1_tds_val);
        que_lactalis_factory_VTIS_condenser_1_ph_val = setVal(que_lactalis_factory_VTIS_condenser_1_ph, que_lactalis_factory_VTIS_condenser_1_ph_val);
        que_lactalis_factory_VTIS_condenser_1_hardness_val = setVal(que_lactalis_factory_VTIS_condenser_1_hardness, que_lactalis_factory_VTIS_condenser_1_hardness_val);
        que_lactalis_factory_VTIS_condenser_1_chlorides_val = setVal(que_lactalis_factory_VTIS_condenser_1_chlorides, que_lactalis_factory_VTIS_condenser_1_chlorides_val);
        que_lactalis_factory_VTIS_condenser_1_alk_val = setVal(que_lactalis_factory_VTIS_condenser_1_alk, que_lactalis_factory_VTIS_condenser_1_alk_val);
        que_lactalis_factory_VTIS_condenser_1_bch_val = setVal(que_lactalis_factory_VTIS_condenser_1_bch, que_lactalis_factory_VTIS_condenser_1_bch_val);
        que_lactalis_factory_VTIS_condenser_1_organophosphonate_val = setVal(que_lactalis_factory_VTIS_condenser_1_organophosphonate, que_lactalis_factory_VTIS_condenser_1_organophosphonate_val);

        que_lactalis_factory_VTIS_2_tds_val = setVal(que_lactalis_factory_VTIS_2_tds, que_lactalis_factory_VTIS_2_tds_val);
        que_lactalis_factory_VTIS_condenser_2_ph_val = setVal(que_lactalis_factory_VTIS_condenser_2_ph, que_lactalis_factory_VTIS_condenser_2_ph_val);
        que_lactalis_factory_VTIS_condenser_2_hardness_val = setVal(que_lactalis_factory_VTIS_condenser_2_hardness, que_lactalis_factory_VTIS_condenser_2_hardness_val);
        que_lactalis_factory_VTIS_condenser_2_chlorides_val = setVal(que_lactalis_factory_VTIS_condenser_2_chlorides, que_lactalis_factory_VTIS_condenser_2_chlorides_val);
        que_lactalis_factory_VTIS_condenser_2_alk_val = setVal(que_lactalis_factory_VTIS_condenser_2_alk, que_lactalis_factory_VTIS_condenser_2_alk_val);
        que_lactalis_factory_VTIS_condenser_2_bch_val = setVal(que_lactalis_factory_VTIS_condenser_2_bch, que_lactalis_factory_VTIS_condenser_2_bch_val);
        que_lactalis_factory_VTIS_condenser_2_organophosphonate_val = setVal(que_lactalis_factory_VTIS_condenser_2_organophosphonate, que_lactalis_factory_VTIS_condenser_2_organophosphonate_val);

        que_lactalis_factory_VTIS_common_meter_val = setVal(que_lactalis_factory_VTIS_common_meter, que_lactalis_factory_VTIS_common_meter_val);
        que_lactalis_factory_VTIS_steam_condensate_ph_val = setVal(que_lactalis_factory_VTIS_steam_condensate_ph, que_lactalis_factory_VTIS_steam_condensate_ph_val);
        que_lactalis_factory_VTIS_steam_condensate_tds_val = setVal(que_lactalis_factory_VTIS_steam_condensate_tds, que_lactalis_factory_VTIS_steam_condensate_tds_val);
        que_lactalis_factory_VTIS_steam_condensate_hardness_val = setVal(que_lactalis_factory_VTIS_steam_condensate_hardness, que_lactalis_factory_VTIS_steam_condensate_hardness_val);

        que_lactalis_factory_growth_milk_cooling_tower_tds_val = setVal(que_lactalis_factory_growth_milk_cooling_tower_tds, que_lactalis_factory_growth_milk_cooling_tower_tds_val);
        que_lactalis_factory_growth_milk_cooling_tower_ph_val = setVal(que_lactalis_factory_growth_milk_cooling_tower_ph, que_lactalis_factory_growth_milk_cooling_tower_ph_val);
        que_lactalis_factory_growth_milk_cooling_tower_hardness_val = setVal(que_lactalis_factory_growth_milk_cooling_tower_hardness, que_lactalis_factory_growth_milk_cooling_tower_hardness_val);
        que_lactalis_factory_growth_milk_cooling_tower_chlorides_val = setVal(que_lactalis_factory_growth_milk_cooling_tower_chlorides, que_lactalis_factory_growth_milk_cooling_tower_chlorides_val);
        que_lactalis_factory_growth_milk_cooling_tower_alk_val = setVal(que_lactalis_factory_growth_milk_cooling_tower_alk,que_lactalis_factory_growth_milk_cooling_tower_alk_val);
        que_lactalis_factory_growth_milk_cooling_tower_bch_val = setVal(que_lactalis_factory_growth_milk_cooling_tower_bch, que_lactalis_factory_growth_milk_cooling_tower_bch_val);
        que_lactalis_factory_growth_milk_cooling_tower_organo_phosphonate_val = setVal(que_lactalis_factory_growth_milk_cooling_tower_organo_phosphonate, que_lactalis_factory_growth_milk_cooling_tower_organo_phosphonate_val);
        que_lactalis_factory_growth_milk_cooling_tower_water_reading_val = setVal(que_lactalis_factory_growth_milk_cooling_tower_water_reading, que_lactalis_factory_growth_milk_cooling_tower_water_reading_val);


        generatQuestionAnswerSQL(userID, que_lactalis_factory_UHT_Condenser_1_tds_val, que_lactalis_factory_UHT_Condenser_1_tds);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_UHT_Condenser_1_ph_val, que_lactalis_factory_UHT_Condenser_1_ph);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_UHT_Condenser_1_hardness_val, que_lactalis_factory_UHT_Condenser_1_hardness);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_UHT_Condenser_1_chlorides_val, que_lactalis_factory_UHT_Condenser_1_chlorides);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_UHT_Condenser_1_alk_val, que_lactalis_factory_UHT_Condenser_1_alk);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_UHT_Condenser_1_bch_val, que_lactalis_factory_UHT_Condenser_1_bch);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_UHT_Condenser_1_phosphonate_val, que_lactalis_factory_UHT_Condenser_1_phosphonate);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_UHT_Condenser_1_molybate_val, que_lactalis_factory_UHT_Condenser_1_molybate);

        generatQuestionAnswerSQL(userID, que_lactalis_factory_UHT_Condenser_2_tds_val, que_lactalis_factory_UHT_Condenser_2_tds);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_UHT_Condenser_2_ph_val, que_lactalis_factory_UHT_Condenser_2_ph);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_UHT_Condenser_2_hardness_val, que_lactalis_factory_UHT_Condenser_2_hardness);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_UHT_Condenser_2_chlorides_val, que_lactalis_factory_UHT_Condenser_2_chlorides);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_UHT_Condenser_2_alk_val, que_lactalis_factory_UHT_Condenser_2_alk);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_UHT_Condenser_2_bch_val, que_lactalis_factory_UHT_Condenser_2_bch);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_UHT_Condenser_2_phosphonate_val, que_lactalis_factory_UHT_Condenser_2_phosphonate);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_UHT_Condenser_2_molybate_val, que_lactalis_factory_UHT_Condenser_2_molybate);

        generatQuestionAnswerSQL(userID, que_lactalis_factory_UHT_Condenser_3_tds_val, que_lactalis_factory_UHT_Condenser_3_tds);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_UHT_Condenser_3_ph_val, que_lactalis_factory_UHT_Condenser_3_ph);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_UHT_Condenser_3_hardness_val, que_lactalis_factory_UHT_Condenser_3_hardness);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_UHT_Condenser_3_chlorides_val, que_lactalis_factory_UHT_Condenser_3_chlorides);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_UHT_Condenser_3_alk_val, que_lactalis_factory_UHT_Condenser_3_alk);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_UHT_Condenser_3_bch_val, que_lactalis_factory_UHT_Condenser_3_bch);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_UHT_Condenser_3_phosphonate_val, que_lactalis_factory_UHT_Condenser_3_phosphonate);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_UHT_Condenser_3_molybate_val, que_lactalis_factory_UHT_Condenser_3_molybate);
        generatQuestionAnswerSQL(userID,que_lactalis_factory_UHT_Condenser_Common_water_meter_val,que_lactalis_factory_UHT_Condenser_Common_water_meter);

        generatQuestionAnswerSQL(userID, que_lactalis_factory_VTIS_1_tds_val, que_lactalis_factory_VTIS_1_tds);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_VTIS_condenser_1_ph_val, que_lactalis_factory_VTIS_condenser_1_ph);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_VTIS_condenser_1_hardness_val, que_lactalis_factory_VTIS_condenser_1_hardness);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_VTIS_condenser_1_chlorides_val, que_lactalis_factory_VTIS_condenser_1_chlorides);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_VTIS_condenser_1_alk_val, que_lactalis_factory_VTIS_condenser_1_alk);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_VTIS_condenser_1_bch_val, que_lactalis_factory_VTIS_condenser_1_bch);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_VTIS_condenser_1_organophosphonate_val, que_lactalis_factory_VTIS_condenser_1_organophosphonate);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_VTIS_2_tds_val, que_lactalis_factory_VTIS_2_tds);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_VTIS_condenser_2_ph_val, que_lactalis_factory_VTIS_condenser_2_ph);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_VTIS_condenser_2_hardness_val, que_lactalis_factory_VTIS_condenser_2_hardness);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_VTIS_condenser_2_chlorides_val, que_lactalis_factory_VTIS_condenser_2_chlorides);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_VTIS_condenser_2_alk_val, que_lactalis_factory_VTIS_condenser_2_alk);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_VTIS_condenser_2_bch_val, que_lactalis_factory_VTIS_condenser_2_bch);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_VTIS_condenser_2_organophosphonate_val, que_lactalis_factory_VTIS_condenser_2_organophosphonate);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_VTIS_common_meter_val, que_lactalis_factory_VTIS_common_meter);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_VTIS_steam_condensate_ph_val, que_lactalis_factory_VTIS_steam_condensate_ph);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_VTIS_steam_condensate_tds_val, que_lactalis_factory_VTIS_steam_condensate_tds);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_VTIS_steam_condensate_hardness_val, que_lactalis_factory_VTIS_steam_condensate_hardness);

        generatQuestionAnswerSQL(userID, que_lactalis_factory_growth_milk_cooling_tower_tds_val, que_lactalis_factory_growth_milk_cooling_tower_tds);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_growth_milk_cooling_tower_ph_val, que_lactalis_factory_growth_milk_cooling_tower_ph);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_growth_milk_cooling_tower_hardness_val, que_lactalis_factory_growth_milk_cooling_tower_hardness);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_growth_milk_cooling_tower_chlorides_val,que_lactalis_factory_growth_milk_cooling_tower_chlorides);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_growth_milk_cooling_tower_alk_val, que_lactalis_factory_growth_milk_cooling_tower_alk);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_growth_milk_cooling_tower_bch_val, que_lactalis_factory_growth_milk_cooling_tower_bch);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_growth_milk_cooling_tower_organo_phosphonate_val, que_lactalis_factory_growth_milk_cooling_tower_organo_phosphonate);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_growth_milk_cooling_tower_water_reading_val, que_lactalis_factory_growth_milk_cooling_tower_water_reading);


        sendForSync();
        new AsyncSubmitComments().execute();
        finish();
    }

    public double setVal(EditText edit, double var) {
        String val = edit.getText().toString();
        if (!val.isEmpty()) {
            var = Double.parseDouble(val);
        } else {
            edit.setText("");
            var = -1.0;
        }
        System.out.println("Text double val = " + var);
        return var;
    }

    public void generatQuestionAnswerSQL(int userID, double val, EditText edit) {
        String sql = "";
        int questionID = 0;
        String varName = edit.getResources().getResourceName(edit.getId());
        String[] split = varName.split("/");
        System.out.println("Var Name is: " + split[1]);
        for (int x = 0; x < questionList.size(); x++) {
            String[] questionSplit = questionList.get(x).split("#");
            if (questionSplit[2].equalsIgnoreCase(split[1])) {
                questionID = Integer.parseInt(questionSplit[0]);
                System.out.println("Question id is: " + questionID);
                if (val != -1.0) {
                    sql = "INSERT INTO Answer (QuestionID, UserID, Value, CreatedAt) VALUES ('" + questionID + "','" + userID + "','" + val + "','" + dateTime + "');";
                    slqList.add(sql);
                }
            }
        }
        System.out.println(sql);
    }

    public void sendForSync() {
        Context context = getApplicationContext();
        meth.storeData(slqList, context);
    }

    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "Select A Date", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private final DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                    showDate(arg1, arg2 + 1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {

        date = year + "-" + month + "-" + day;
        dateTime = year + "-" + month + "-" + day + " " + hour + ":" + min + ":" + sec;
        dateView.setText("Date: " + date);
    }

    private static final String DEBUG_TAG = "NetworkStatusExample";
    private static final String URL = "jdbc:mysql://dedi75.cpt1.host-h.net/water_app_db";
    private static final String DBNAME = "water_app_db";
    private static final String USER = "wpmsaqgenz_3";
    private static final String PASSWORD = "pw6qpK9MHEMCa5uvV2c8";
    private Connection con;

    public void checkBoxCheck(View view) {
        Toast.makeText(getApplicationContext(), "This one works", Toast.LENGTH_SHORT).show();
    }

    class AsyncSubmitComments extends AsyncTask<Void, Void, Void> {

        String error = "";
        String comment = cooling_system_comments.getText().toString().trim();

        @Override
        protected Void doInBackground(Void... voids) {
            int companyID = 0;
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                try {
                    con = DriverManager.getConnection(URL, USER, PASSWORD);
                    con.setAutoCommit(false);
                    PreparedStatement prepStatment = con.prepareStatement("INSERT INTO Comment(CommentText, UserID, FormID) VALUES (?, ?, (SELECT FormID FROM Form WHERE FromName = ?))");
                    prepStatment.setString(1, comment);
                    prepStatment.setInt(2, userID);
                    prepStatment.setString(3, FORMID);


                    prepStatment.executeUpdate();
                    con.commit();
                    prepStatment.close();
                } catch (Exception e) {
                    con.rollback();
                    error = "There was a fuckup, please try again you noob";
                }
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                error = e.toString();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            if (error != "") {
                Toast.makeText(getApplicationContext(), "Could not Submit data", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Comment Submitted", Toast.LENGTH_LONG).show();
            }
            super.onPostExecute(aVoid);
        }
    }

    public void sync_data(View view) {
        Intent sync = new Intent(getApplicationContext(), Sync.class);
        startActivity(sync);
    }

    private void initComp() {
        que_lactalis_factory_UHT_Condenser_1_tds = findViewById(R.id.que_lactalis_factory_UHT_Condenser_1_tds);
        que_lactalis_factory_UHT_Condenser_1_ph = findViewById(R.id.que_lactalis_factory_UHT_Condenser_1_ph);
        que_lactalis_factory_UHT_Condenser_1_hardness = findViewById(R.id.que_lactalis_factory_UHT_Condenser_1_hardness);
        que_lactalis_factory_UHT_Condenser_1_chlorides = findViewById(R.id.que_lactalis_factory_UHT_Condenser_1_chlorides);
        que_lactalis_factory_UHT_Condenser_1_alk = findViewById(R.id.que_lactalis_factory_UHT_Condenser_1_alk);
        que_lactalis_factory_UHT_Condenser_1_bch = findViewById(R.id.que_lactalis_factory_UHT_Condenser_1_bch);
        que_lactalis_factory_UHT_Condenser_1_phosphonate = findViewById(R.id.que_lactalis_factory_UHT_Condenser_1_phosphonate);
        que_lactalis_factory_UHT_Condenser_1_molybate = findViewById(R.id.que_lactalis_factory_UHT_Condenser_1_molybate);

        que_lactalis_factory_UHT_Condenser_2_tds = findViewById(R.id.que_lactalis_factory_UHT_Condenser_2_tds);
        que_lactalis_factory_UHT_Condenser_2_ph = findViewById(R.id.que_lactalis_factory_UHT_Condenser_2_ph);
        que_lactalis_factory_UHT_Condenser_2_hardness = findViewById(R.id.que_lactalis_factory_UHT_Condenser_2_hardness);
        que_lactalis_factory_UHT_Condenser_2_chlorides = findViewById(R.id.que_lactalis_factory_UHT_Condenser_2_chlorides);
        que_lactalis_factory_UHT_Condenser_2_alk = findViewById(R.id.que_lactalis_factory_UHT_Condenser_2_alk);
        que_lactalis_factory_UHT_Condenser_2_bch = findViewById(R.id.que_lactalis_factory_UHT_Condenser_2_bch);
        que_lactalis_factory_UHT_Condenser_2_phosphonate = findViewById(R.id.que_lactalis_factory_UHT_Condenser_2_phosphonate);
        que_lactalis_factory_UHT_Condenser_2_molybate = findViewById(R.id.que_lactalis_factory_UHT_Condenser_2_molybate);

        cb_daily = findViewById(R.id.cb_lactalis_cooling_system_daily);
        cb_weekly = findViewById(R.id.cb_lactalis_cooling_system_weekly);
        cb_monthly = findViewById(R.id.cb_lactalis_cooling_system_monthly);

        cb_daily.setOnClickListener(checkboxCheck);
        cb_weekly.setOnClickListener(checkboxCheck);
        cb_monthly.setOnClickListener(checkboxCheck);

        que_lactalis_factory_UHT_Condenser_3_tds = findViewById(R.id.que_lactalis_factory_UHT_Condenser_3_tds);
        que_lactalis_factory_UHT_Condenser_3_ph = findViewById(R.id.que_lactalis_factory_UHT_Condenser_3_ph);
        que_lactalis_factory_UHT_Condenser_3_hardness = findViewById(R.id.que_lactalis_factory_UHT_Condenser_3_hardness);
        que_lactalis_factory_UHT_Condenser_3_chlorides = findViewById(R.id.que_lactalis_factory_UHT_Condenser_3_chlorides);
        que_lactalis_factory_UHT_Condenser_3_alk = findViewById(R.id.que_lactalis_factory_UHT_Condenser_3_alk);
        que_lactalis_factory_UHT_Condenser_3_bch = findViewById(R.id.que_lactalis_factory_UHT_Condenser_3_bch);
        que_lactalis_factory_UHT_Condenser_3_phosphonate = findViewById(R.id.que_lactalis_factory_UHT_Condenser_3_phosphonate);
        que_lactalis_factory_UHT_Condenser_3_molybate = findViewById(R.id.que_lactalis_factory_UHT_Condenser_3_molybate);

        que_lactalis_factory_VTIS_1_tds = findViewById(R.id.que_lactalis_factory_VTIS_1_tds);
        que_lactalis_factory_VTIS_condenser_1_ph = findViewById(R.id.que_lactalis_factory_VTIS_condenser_1_ph);
        que_lactalis_factory_VTIS_condenser_1_hardness = findViewById(R.id.que_lactalis_factory_VTIS_condenser_1_hardness);
        que_lactalis_factory_VTIS_condenser_1_chlorides = findViewById(R.id.que_lactalis_factory_VTIS_condenser_1_chlorides);
        que_lactalis_factory_VTIS_condenser_1_alk = findViewById(R.id.que_lactalis_factory_VTIS_condenser_1_alk);
        que_lactalis_factory_VTIS_condenser_1_bch = findViewById(R.id.que_lactalis_factory_VTIS_condenser_1_bch);
        que_lactalis_factory_VTIS_condenser_1_organophosphonate = findViewById(R.id.que_lactalis_factory_VTIS_condenser_1_organophosphonate);

        que_lactalis_factory_VTIS_2_tds = findViewById(R.id.que_lactalis_factory_VTIS_2_tds);
        que_lactalis_factory_VTIS_condenser_2_ph = findViewById(R.id.que_lactalis_factory_VTIS_condenser_2_ph);
        que_lactalis_factory_VTIS_condenser_2_hardness = findViewById(R.id.que_lactalis_factory_VTIS_condenser_2_hardness);
        que_lactalis_factory_VTIS_condenser_2_chlorides = findViewById(R.id.que_lactalis_factory_VTIS_condenser_2_chlorides);
        que_lactalis_factory_VTIS_condenser_2_alk = findViewById(R.id.que_lactalis_factory_VTIS_condenser_2_alk);
        que_lactalis_factory_VTIS_condenser_2_bch = findViewById(R.id.que_lactalis_factory_VTIS_condenser_2_bch);
        que_lactalis_factory_VTIS_condenser_2_organophosphonate = findViewById(R.id.que_lactalis_factory_VTIS_condenser_2_organophosphonate);

        que_lactalis_factory_VTIS_common_meter = findViewById(R.id.que_lactalis_factory_VTIS_common_meter);
        que_lactalis_factory_VTIS_steam_condensate_ph = findViewById(R.id.que_lactalis_factory_VTIS_steam_condensate_ph);
        que_lactalis_factory_VTIS_steam_condensate_tds = findViewById(R.id.que_lactalis_factory_VTIS_steam_condensate_tds);
        que_lactalis_factory_VTIS_steam_condensate_hardness = findViewById(R.id.que_lactalis_factory_VTIS_steam_condensate_hardness);

        que_lactalis_factory_growth_milk_cooling_tower_ph = findViewById(R.id.que_lactalis_factory_growth_milk_cooling_tower_ph);
        que_lactalis_factory_growth_milk_cooling_tower_tds = findViewById(R.id.que_lactalis_factory_growth_milk_cooling_tower_tds);
        que_lactalis_factory_growth_milk_cooling_tower_hardness = findViewById(R.id.que_lactalis_factory_growth_milk_cooling_tower_hardness);
        que_lactalis_factory_growth_milk_cooling_tower_chlorides = findViewById(R.id.que_lactalis_factory_growth_milk_cooling_tower_chlorides);
        que_lactalis_factory_growth_milk_cooling_tower_alk = findViewById(R.id.que_lactalis_factory_growth_milk_cooling_tower_alk);
        que_lactalis_factory_growth_milk_cooling_tower_bch = findViewById(R.id.que_lactalis_factory_growth_milk_cooling_tower_bch);
        que_lactalis_factory_growth_milk_cooling_tower_organo_phosphonate = findViewById(R.id.que_lactalis_factory_growth_milk_cooling_tower_organo_phosphonate);
        que_lactalis_factory_growth_milk_cooling_tower_water_reading = findViewById(R.id.que_lactalis_factory_growth_milk_cooling_tower_water_reading);

        que_lactalis_factory_UHT_Condenser_1_tds_cont = findViewById(R.id.que_lactalis_factory_UHT_Condenser_1_tds_cont);
        que_lactalis_factory_UHT_Condenser_1_ph_cont = findViewById(R.id.que_lactalis_factory_UHT_Condenser_1_ph_cont);
        que_lactalis_factory_UHT_Condenser_1_hardness_cont = findViewById(R.id.que_lactalis_factory_UHT_Condenser_1_hardness_cont);
        que_lactalis_factory_UHT_Condenser_1_chlorides_cont = findViewById(R.id.que_lactalis_factory_UHT_Condenser_1_chlorides_cont);
        que_lactalis_factory_UHT_Condenser_1_alk_cont = findViewById(R.id.que_lactalis_factory_UHT_Condenser_1_alk_cont);
        que_lactalis_factory_UHT_Condenser_1_bch_cont = findViewById(R.id.que_lactalis_factory_UHT_Condenser_1_bch_cont);
        que_lactalis_factory_UHT_Condenser_1_phosphonate_cont = findViewById(R.id.que_lactalis_factory_UHT_Condenser_1_phosphonate_cont);
        que_lactalis_factory_UHT_Condenser_1_molybate_cont = findViewById(R.id.que_lactalis_factory_UHT_Condenser_1_molybate_cont);

        que_lactalis_factory_UHT_Condenser_2_tds_cont = findViewById(R.id.que_lactalis_factory_UHT_Condenser_2_tds_cont);
        que_lactalis_factory_UHT_Condenser_2_ph_cont = findViewById(R.id.que_lactalis_factory_UHT_Condenser_2_ph_cont);
        que_lactalis_factory_UHT_Condenser_2_hardness_cont = findViewById(R.id.que_lactalis_factory_UHT_Condenser_2_hardness_cont);
        que_lactalis_factory_UHT_Condenser_2_chlorides_cont = findViewById(R.id.que_lactalis_factory_UHT_Condenser_2_chlorides_cont);
        que_lactalis_factory_UHT_Condenser_2_alk_cont = findViewById(R.id.que_lactalis_factory_UHT_Condenser_2_alk_cont);
        que_lactalis_factory_UHT_Condenser_2_bch_cont = findViewById(R.id.que_lactalis_factory_UHT_Condenser_2_bch_cont);
        que_lactalis_factory_UHT_Condenser_2_phosphonate_cont = findViewById(R.id.que_lactalis_factory_UHT_Condenser_2_phosphonate_cont);
        que_lactalis_factory_UHT_Condenser_2_molybate_cont = findViewById(R.id.que_lactalis_factory_UHT_Condenser_2_molybate_cont);

        que_lactalis_factory_UHT_Condenser_3_tds_cont = findViewById(R.id.que_lactalis_factory_UHT_Condenser_3_tds_cont);
        que_lactalis_factory_UHT_Condenser_3_ph_cont = findViewById(R.id.que_lactalis_factory_UHT_Condenser_3_ph_cont);
        que_lactalis_factory_UHT_Condenser_3_hardness_cont = findViewById(R.id.que_lactalis_factory_UHT_Condenser_3_hardness_cont);
        que_lactalis_factory_UHT_Condenser_3_chlorides_cont = findViewById(R.id.que_lactalis_factory_UHT_Condenser_3_chlorides_cont);
        que_lactalis_factory_UHT_Condenser_3_alk_cont = findViewById(R.id.que_lactalis_factory_UHT_Condenser_3_alk_cont);
        que_lactalis_factory_UHT_Condenser_3_bch_cont = findViewById(R.id.que_lactalis_factory_UHT_Condenser_3_bch_cont);
        que_lactalis_factory_UHT_Condenser_3_phosphonate_cont = findViewById(R.id.que_lactalis_factory_UHT_Condenser_3_phosphonate_cont);
        que_lactalis_factory_UHT_Condenser_3_molybate_cont = findViewById(R.id.que_lactalis_factory_UHT_Condenser_3_molybate_cont);

        que_lactalis_factory_VTIS_1_tds_cont = findViewById(R.id.que_lactalis_factory_VTIS_1_tds_cont);
        que_lactalis_factory_VTIS_condenser_1_ph_cont = findViewById(R.id.que_lactalis_factory_VTIS_condenser_1_ph_cont);
        que_lactalis_factory_VTIS_condenser_1_hardness_cont = findViewById(R.id.que_lactalis_factory_VTIS_condenser_1_hardness_cont);
        que_lactalis_factory_VTIS_condenser_1_chlorides_cont = findViewById(R.id.que_lactalis_factory_VTIS_condenser_1_chlorides_cont);
        que_lactalis_factory_VTIS_condenser_1_alk_cont = findViewById(R.id.que_lactalis_factory_VTIS_condenser_1_alk_cont);
        que_lactalis_factory_VTIS_condenser_1_bch_cont = findViewById(R.id.que_lactalis_factory_VTIS_condenser_1_bch_cont);
        que_lactalis_factory_VTIS_condenser_1_organophosphonate_cont = findViewById(R.id.que_lactalis_factory_VTIS_condenser_1_organophosphonate_cont);

        que_lactalis_factory_VTIS_2_tds_cont = findViewById(R.id.que_lactalis_factory_VTIS_tds_2_cont);
        que_lactalis_factory_VTIS_condenser_2_ph_cont = findViewById(R.id.que_lactalis_factory_VTIS_condenser_2_ph_cont);
        que_lactalis_factory_VTIS_condenser_2_hardness_cont = findViewById(R.id.que_lactalis_factory_VTIS_condenser_2_hardness_cont);
        que_lactalis_factory_VTIS_condenser_2_chlorides_cont = findViewById(R.id.que_lactalis_factory_VTIS_condenser_2_chlorides_cont);
        que_lactalis_factory_VTIS_condenser_2_alk_cont = findViewById(R.id.que_lactalis_factory_VTIS_condenser_2_alk_cont);
        que_lactalis_factory_VTIS_condenser_2_bch_cont = findViewById(R.id.que_lactalis_factory_VTIS_condenser_2_bch_cont);
        que_lactalis_factory_VTIS_condenser_2_organophosphonate_cont = findViewById(R.id.que_lactalis_factory_VTIS_condenser_2_organophosphonate_cont);

        que_lactalis_factory_VTIS_steam_condensate_ph_cont = findViewById(R.id.que_lactalis_factory_VTIS_steam_condensate_ph_cont);
        que_lactalis_factory_VTIS_steam_condensate_tds_cont = findViewById(R.id.que_lactalis_factory_VTIS_steam_condensate_tds_cont);
        que_lactalis_factory_VTIS_steam_condensate_hardness_cont = findViewById(R.id.que_lactalis_factory_VTIS_steam_condensate_hardness_cont);

        que_lactalis_factory_VTIS_common_meter_cont = findViewById(R.id.que_lactalis_factory_VTIS_common_meter_cont);

        que_lactalis_factory_growth_milk_cooling_tower_tds_cont = findViewById(R.id.que_lactalis_factory_growth_milk_cooling_tower_tds_cont);
        que_lactalis_factory_growth_milk_cooling_tower_ph_cont = findViewById(R.id.que_lactalis_factory_growth_milk_cooling_tower_ph_cont);
        que_lactalis_factory_growth_milk_cooling_tower_hardness_cont = findViewById(R.id.que_lactalis_factory_growth_milk_cooling_tower_hardness_cont);
        que_lactalis_factory_growth_milk_cooling_tower_chlorides_cont = findViewById(R.id.que_lactalis_factory_growth_milk_cooling_tower_chlorides_cont);
        que_lactalis_factory_growth_milk_cooling_tower_alk_cont = findViewById(R.id.que_lactalis_factory_growth_milk_cooling_tower_alk_cont);
        que_lactalis_factory_growth_milk_cooling_tower_bch_cont = findViewById(R.id.que_lactalis_factory_growth_milk_cooling_tower_bch_cont);
        que_lactalis_factory_growth_milk_cooling_tower_organo_phosphonate_cont = findViewById(R.id.que_lactalis_factory_growth_milk_cooling_tower_organo_phosphonate_cont);
        que_lactalis_factory_growth_milk_cooling_tower_water_meter_cont = findViewById(R.id.que_lactalis_factory_growth_milk_cooling_tower_water_meter_cont);

        que_lactalis_factory_UHT_Condenser_Common_water_meter = findViewById(R.id.que_lactalis_factory_UHT_Condenser_Common_water_meter);
        que_lactalis_factory_UHT_Condenser_Common_water_meter_cont = findViewById(R.id.que_lactalis_factory_UHT_Condenser_Common_water_meter_cont);

        cooling_system_comments = findViewById(R.id.txa_lactalis_factory_UHT_Condensor_comments);
        dateView = findViewById(R.id.lbl_date_selection);
    }

    private final View.OnClickListener checkboxCheck = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            boolean checked = ((CheckBox) v).isChecked();
            System.out.println(checked + "-" + ((CheckBox) v).isChecked() + "-" + v.getId());
            switch (v.getId()) {
                case R.id.cb_lactalis_cooling_system_daily:
                    if (checked == true) {
                        dailyRecordingBoilerShow();
                        weeklyRecordingsBoilerHide();
                        monthlyRecordingBoilerHide();
                        cb_weekly.setChecked(false);
                        cb_monthly.setChecked(false);
                    } else {
                        dailyRecordingBoilerHide();
                        weeklyRecordingsBoilerHide();
                        monthlyRecordingBoilerHide();
                    }
                    break;
                case R.id.cb_lactalis_cooling_system_weekly:
                    if (checked) {
                        dailyRecordingBoilerShow();
                        weeklyRecordingBoilerShow();
                        monthlyRecordingBoilerHide();
                        cb_daily.setChecked(false);
                        cb_monthly.setChecked(false);
                    } else {
                        dailyRecordingBoilerHide();
                        weeklyRecordingsBoilerHide();
                        monthlyRecordingBoilerHide();
                    }
                    break;
                case R.id.cb_lactalis_cooling_system_monthly:
                    if (checked) {
                        dailyRecordingBoilerShow();
                        weeklyRecordingBoilerShow();
                        monthlyRecordingBoilerShow();
                        cb_daily.setChecked(false);
                        cb_weekly.setChecked(false);
                    } else {
                        dailyRecordingBoilerHide();
                        weeklyRecordingsBoilerHide();
                        monthlyRecordingBoilerHide();
                    }
            }
        }
    };

    public void dailyRecordingBoilerShow() {
        que_lactalis_factory_UHT_Condenser_Common_water_meter_cont.setVisibility(View.VISIBLE);

        que_lactalis_factory_VTIS_common_meter_cont.setVisibility(View.VISIBLE);

        que_lactalis_factory_growth_milk_cooling_tower_water_meter_cont.setVisibility(View.VISIBLE);
    }

    public void weeklyRecordingBoilerShow() {
        que_lactalis_factory_UHT_Condenser_1_tds_cont.setVisibility(View.VISIBLE);
        que_lactalis_factory_UHT_Condenser_1_ph_cont.setVisibility(View.VISIBLE);
        que_lactalis_factory_UHT_Condenser_1_hardness_cont.setVisibility(View.VISIBLE);
        que_lactalis_factory_UHT_Condenser_1_chlorides_cont.setVisibility(View.VISIBLE);
        que_lactalis_factory_UHT_Condenser_1_alk_cont.setVisibility(View.VISIBLE);
        que_lactalis_factory_UHT_Condenser_1_bch_cont.setVisibility(View.VISIBLE);
        que_lactalis_factory_UHT_Condenser_1_phosphonate_cont.setVisibility(View.VISIBLE);
        que_lactalis_factory_UHT_Condenser_1_molybate_cont.setVisibility(View.VISIBLE);
        que_lactalis_factory_UHT_Condenser_2_tds_cont.setVisibility(View.VISIBLE);
        que_lactalis_factory_UHT_Condenser_2_ph_cont.setVisibility(View.VISIBLE);
        que_lactalis_factory_UHT_Condenser_2_hardness_cont.setVisibility(View.VISIBLE);
        que_lactalis_factory_UHT_Condenser_2_chlorides_cont.setVisibility(View.VISIBLE);
        que_lactalis_factory_UHT_Condenser_2_alk_cont.setVisibility(View.VISIBLE);
        que_lactalis_factory_UHT_Condenser_2_bch_cont.setVisibility(View.VISIBLE);
        que_lactalis_factory_UHT_Condenser_2_phosphonate_cont.setVisibility(View.VISIBLE);
        que_lactalis_factory_UHT_Condenser_2_molybate_cont.setVisibility(View.VISIBLE);

        que_lactalis_factory_VTIS_1_tds_cont.setVisibility(View.VISIBLE);
        que_lactalis_factory_VTIS_condenser_1_ph_cont.setVisibility(View.VISIBLE);
        que_lactalis_factory_VTIS_condenser_1_hardness_cont.setVisibility(View.VISIBLE);
        que_lactalis_factory_VTIS_condenser_1_chlorides_cont.setVisibility(View.VISIBLE);
        que_lactalis_factory_VTIS_condenser_1_alk_cont.setVisibility(View.VISIBLE);
        que_lactalis_factory_VTIS_condenser_1_bch_cont.setVisibility(View.VISIBLE);
        que_lactalis_factory_VTIS_condenser_1_organophosphonate_cont.setVisibility(View.VISIBLE);
        que_lactalis_factory_VTIS_2_tds_cont.setVisibility(View.VISIBLE);
        que_lactalis_factory_VTIS_condenser_2_ph_cont.setVisibility(View.VISIBLE);
        que_lactalis_factory_VTIS_condenser_2_hardness_cont.setVisibility(View.VISIBLE);
        que_lactalis_factory_VTIS_condenser_2_chlorides_cont.setVisibility(View.VISIBLE);
        que_lactalis_factory_VTIS_condenser_2_alk_cont.setVisibility(View.VISIBLE);
        que_lactalis_factory_VTIS_condenser_2_bch_cont.setVisibility(View.VISIBLE);
        que_lactalis_factory_VTIS_condenser_2_organophosphonate_cont.setVisibility(View.VISIBLE);
        que_lactalis_factory_VTIS_steam_condensate_ph_cont.setVisibility(View.VISIBLE);
        que_lactalis_factory_VTIS_steam_condensate_tds_cont.setVisibility(View.VISIBLE);
        que_lactalis_factory_VTIS_steam_condensate_hardness_cont.setVisibility(View.VISIBLE);

        que_lactalis_factory_UHT_Condenser_3_tds_cont.setVisibility(View.VISIBLE);
        que_lactalis_factory_UHT_Condenser_3_hardness_cont.setVisibility(View.VISIBLE);
        que_lactalis_factory_UHT_Condenser_3_ph_cont.setVisibility(View.VISIBLE);
        que_lactalis_factory_UHT_Condenser_3_chlorides_cont.setVisibility(View.VISIBLE);
        que_lactalis_factory_UHT_Condenser_3_molybate_cont.setVisibility(View.VISIBLE);
        que_lactalis_factory_UHT_Condenser_3_alk_cont.setVisibility(View.VISIBLE);
        que_lactalis_factory_UHT_Condenser_3_alk_cont.setVisibility(View.VISIBLE);
        que_lactalis_factory_UHT_Condenser_3_bch_cont.setVisibility(View.VISIBLE);
        que_lactalis_factory_UHT_Condenser_3_phosphonate_cont.setVisibility(View.VISIBLE);

        que_lactalis_factory_growth_milk_cooling_tower_tds_cont.setVisibility(View.VISIBLE);
        que_lactalis_factory_growth_milk_cooling_tower_ph_cont.setVisibility(View.VISIBLE);
        que_lactalis_factory_growth_milk_cooling_tower_hardness_cont.setVisibility(View.VISIBLE);
        que_lactalis_factory_growth_milk_cooling_tower_chlorides_cont.setVisibility(View.VISIBLE);
        que_lactalis_factory_growth_milk_cooling_tower_alk_cont.setVisibility(View.VISIBLE);
        que_lactalis_factory_growth_milk_cooling_tower_bch_cont.setVisibility(View.VISIBLE);
        que_lactalis_factory_growth_milk_cooling_tower_organo_phosphonate_cont.setVisibility(View.VISIBLE);
    }

    public void monthlyRecordingBoilerShow() {

    }

    public void dailyRecordingBoilerHide() {
        que_lactalis_factory_UHT_Condenser_Common_water_meter_cont.setVisibility(View.GONE);
        que_lactalis_factory_VTIS_common_meter_cont.setVisibility(View.GONE);
        que_lactalis_factory_growth_milk_cooling_tower_water_meter_cont.setVisibility(View.GONE);

    }

    public void weeklyRecordingsBoilerHide() {
        que_lactalis_factory_UHT_Condenser_1_tds_cont.setVisibility(View.GONE);
        que_lactalis_factory_UHT_Condenser_1_ph_cont.setVisibility(View.GONE);
        que_lactalis_factory_UHT_Condenser_1_hardness_cont.setVisibility(View.GONE);
        que_lactalis_factory_UHT_Condenser_1_chlorides_cont.setVisibility(View.GONE);
        que_lactalis_factory_UHT_Condenser_1_alk_cont.setVisibility(View.GONE);
        que_lactalis_factory_UHT_Condenser_1_bch_cont.setVisibility(View.GONE);
        que_lactalis_factory_UHT_Condenser_1_phosphonate_cont.setVisibility(View.GONE);
        que_lactalis_factory_UHT_Condenser_1_molybate_cont.setVisibility(View.GONE);
        que_lactalis_factory_UHT_Condenser_2_tds_cont.setVisibility(View.GONE);
        que_lactalis_factory_UHT_Condenser_2_ph_cont.setVisibility(View.GONE);
        que_lactalis_factory_UHT_Condenser_2_hardness_cont.setVisibility(View.GONE);
        que_lactalis_factory_UHT_Condenser_2_chlorides_cont.setVisibility(View.GONE);
        que_lactalis_factory_UHT_Condenser_2_alk_cont.setVisibility(View.GONE);
        que_lactalis_factory_UHT_Condenser_2_bch_cont.setVisibility(View.GONE);
        que_lactalis_factory_UHT_Condenser_2_phosphonate_cont.setVisibility(View.GONE);
        que_lactalis_factory_UHT_Condenser_2_molybate_cont.setVisibility(View.GONE);

        que_lactalis_factory_UHT_Condenser_3_tds_cont.setVisibility(View.GONE);
        que_lactalis_factory_UHT_Condenser_3_hardness_cont.setVisibility(View.GONE);
        que_lactalis_factory_UHT_Condenser_3_ph_cont.setVisibility(View.GONE);
        que_lactalis_factory_UHT_Condenser_3_chlorides_cont.setVisibility(View.GONE);
        que_lactalis_factory_UHT_Condenser_3_molybate_cont.setVisibility(View.GONE);
        que_lactalis_factory_UHT_Condenser_3_alk_cont.setVisibility(View.GONE);
        que_lactalis_factory_UHT_Condenser_3_alk_cont.setVisibility(View.GONE);
        que_lactalis_factory_UHT_Condenser_3_bch_cont.setVisibility(View.GONE);
        que_lactalis_factory_UHT_Condenser_3_phosphonate_cont.setVisibility(View.GONE);

        que_lactalis_factory_VTIS_1_tds_cont.setVisibility(View.GONE);
        que_lactalis_factory_VTIS_condenser_1_ph_cont.setVisibility(View.GONE);
        que_lactalis_factory_VTIS_condenser_1_hardness_cont.setVisibility(View.GONE);
        que_lactalis_factory_VTIS_condenser_1_chlorides_cont.setVisibility(View.GONE);
        que_lactalis_factory_VTIS_condenser_1_alk_cont.setVisibility(View.GONE);
        que_lactalis_factory_VTIS_condenser_1_bch_cont.setVisibility(View.GONE);
        que_lactalis_factory_VTIS_condenser_1_organophosphonate_cont.setVisibility(View.GONE);
        que_lactalis_factory_VTIS_2_tds_cont.setVisibility(View.GONE);
        que_lactalis_factory_VTIS_condenser_2_ph_cont.setVisibility(View.GONE);
        que_lactalis_factory_VTIS_condenser_2_hardness_cont.setVisibility(View.GONE);
        que_lactalis_factory_VTIS_condenser_2_chlorides_cont.setVisibility(View.GONE);
        que_lactalis_factory_VTIS_condenser_2_alk_cont.setVisibility(View.GONE);
        que_lactalis_factory_VTIS_condenser_2_bch_cont.setVisibility(View.GONE);
        que_lactalis_factory_VTIS_condenser_2_organophosphonate_cont.setVisibility(View.GONE);
        que_lactalis_factory_VTIS_steam_condensate_ph_cont.setVisibility(View.GONE);
        que_lactalis_factory_VTIS_steam_condensate_tds_cont.setVisibility(View.GONE);
        que_lactalis_factory_VTIS_steam_condensate_hardness_cont.setVisibility(View.GONE);
        que_lactalis_factory_growth_milk_cooling_tower_tds_cont.setVisibility(View.GONE);
        que_lactalis_factory_growth_milk_cooling_tower_ph_cont.setVisibility(View.GONE);
        que_lactalis_factory_growth_milk_cooling_tower_hardness_cont.setVisibility(View.GONE);
        que_lactalis_factory_growth_milk_cooling_tower_chlorides_cont.setVisibility(View.GONE);
        que_lactalis_factory_growth_milk_cooling_tower_alk_cont.setVisibility(View.GONE);
        que_lactalis_factory_growth_milk_cooling_tower_bch_cont.setVisibility(View.GONE);
        que_lactalis_factory_growth_milk_cooling_tower_organo_phosphonate_cont.setVisibility(View.GONE);
    }

    public void monthlyRecordingBoilerHide() {

    }

    private void recPeriod(Intent oldIntent) {
        int period;
        try {
            period = oldIntent.getIntExtra("inputPeriod", 4);
        } catch (Exception e) {
            period = 4;
        }
        switch (period) {
            case 1:
                cb_daily.setChecked(true);
                cb_weekly.setChecked(false);
                cb_monthly.setChecked(false);
                dailyRecordingBoilerShow();
                monthlyRecordingBoilerHide();
                weeklyRecordingsBoilerHide();
                break;
            case 2:
                cb_daily.setChecked(false);
                cb_weekly.setChecked(true);
                cb_monthly.setChecked(false);
                weeklyRecordingBoilerShow();
                dailyRecordingBoilerShow();
                monthlyRecordingBoilerHide();
                break;
            case 3:
                cb_daily.setChecked(false);
                cb_weekly.setChecked(false);
                cb_monthly.setChecked(true);
                monthlyRecordingBoilerShow();
                dailyRecordingBoilerShow();
                weeklyRecordingBoilerShow();
                break;
            default:
                cb_daily.setChecked(true);
                cb_weekly.setChecked(true);
                cb_monthly.setChecked(true);
                dailyRecordingBoilerShow();
                weeklyRecordingBoilerShow();
                monthlyRecordingBoilerShow();
                break;
        }
    }


    private void readQuestion(Intent oldIntent) {
        FileInputStream fis = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            fis = getApplicationContext().openFileInput(FILENAMELOGIN);
            InputStreamReader inputStreamReader =
                    new InputStreamReader(fis, StandardCharsets.UTF_8);

            try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                String line = reader.readLine();
                while (line != null) {
                    stringBuilder.append(line).append('\n');
                    line = reader.readLine();
                }
            } catch (IOException e) {
                // Error occurred when opening raw file for reading.
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            String contents = stringBuilder.toString();
            if (!contents.isEmpty()) {
                String[] split = contents.split("#");
                userID = Integer.parseInt(split[0]);
                System.out.println("User ID is: " + userID + " ");
            }
        }
        questionList = oldIntent.getStringArrayListExtra("questionList");
    }

    private void setDate() {
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        min = calendar.get(Calendar.MINUTE);
        sec = calendar.get(Calendar.SECOND);
        showDate(year, month + 1, day);
    }
}