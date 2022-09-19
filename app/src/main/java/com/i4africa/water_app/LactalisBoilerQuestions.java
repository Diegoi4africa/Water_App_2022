package com.i4africa.water_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;

public class LactalisBoilerQuestions extends AppCompatActivity {

    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day, hour, min, sec;
    private String date, dateTime;
    private TextView dateView;

    private final String FORMID = "Lactalis_Factory_Boiler_Form";
    //<editor-fold desc="EditText Declaration">
    private EditText que_lactalis_factory_boiler_feed_tds, que_lactalis_factory_boiler_feed_ph, que_lactalis_factory_boiler_feed_hardness, que_lactalis_factory_boiler_feed_chlorides,
            que_lactalis_factory_boiler_feed_alkalinity, que_lactalis_factory_boiler_softener_hardness, que_lactalis_factory_boiler_softener_TDS, que_lactalis_factory_boiler_softener_inlet,
            que_lactalis_factory_boiler_softener_outlet, que_lactalis_factory_boiler_condensate_TDS, que_lactalis_factory_boiler_condensate_ph, que_lactalis_factory_boiler_condensate_hardness,
            que_lactalis_factory_boiler_1_meter, que_lactalis_factory_boiler_1_TDS, que_lactalis_factory_boiler_1_ph, que_lactalis_factory_boiler_1_ohalk,
            que_lactalis_factory_boiler_1_chlorides, que_lactalis_factory_boiler_1_sulphite, que_lactalis_factory_boiler_1_phosphate, que_lactalis_factory_boiler_1_hardness, que_lactalis_factory_boiler_1_stndBy_TDS,
            que_lactalis_factory_boiler_1_stndBy_ph, que_lactalis_factory_boiler_1_stndBy_ohalk, que_lactalis_factory_boiler_1_stndBy_sulphite, que_lactalis_factory_boiler_2_meter,
            que_lactalis_factory_boiler_2_TDS, que_lactalis_factory_boiler_2_ph, que_lactalis_factory_boiler_2_ohalk, que_lactalis_factory_boiler_2_chlorides, que_lactalis_factory_boiler_2_sulphite, que_lactalis_factory_boiler_2_phosphate,
            que_lactalis_factory_boiler_2_hardness, que_lactalis_factory_boiler_2_stndBy_TDS, que_lactalis_factory_boiler_2_stndBy_ph, que_lactalis_factory_boiler_2_stndBy_sulphite, que_lactalis_factory_boiler_2_stndBy_ohalk;
    //</editor-fold>
    //<editor-fold desc="TextInputLayout Declaration">
    private TextInputLayout que_lactalis_factory_boiler_feed_tds_cont, que_lactalis_factory_boiler_feed_ph_cont, que_lactalis_factory_boiler_feed_hardness_cont, que_lactalis_factory_boiler_feed_chlorides_cont,
            que_lactalis_factory_boiler_feed_alkalinity_cont, que_lactalis_factory_boiler_softener_hardness_cont, que_lactalis_factory_boiler_softener_TDS_cont, que_lactalis_factory_boiler_softener_inlet_cont,
            que_lactalis_factory_boiler_softener_outlet_cont, que_lactalis_factory_boiler_condensate_TDS_cont, que_lactalis_factory_boiler_condensate_ph_cont, que_lactalis_factory_boiler_condensate_hardness_cont,
            que_lactalis_factory_boiler_1_meter_cont, que_lactalis_factory_boiler_1_TDS_cont, que_lactalis_factory_boiler_1_ph_cont, que_lactalis_factory_boiler_1_ohalk_cont,
            que_lactalis_factory_boiler_1_chlorides_cont, que_lactalis_factory_boiler_1_sulphite_cont, que_lactalis_factory_boiler_1_phosphate_cont, que_lactalis_factory_boiler_1_hardness_cont, que_lactalis_factory_boiler_1_standBy_TDS_cont,
            que_lactalis_factory_boiler_1_stndBy_ph_cont, que_lactalis_factory_boiler_1_stndBy_ohalk_cont, que_lactalis_factory_boiler_1_stndBy_sulphite_cont, que_lactalis_factory_boiler_2_meter_cont,
            que_lactalis_factory_boiler_2_TDS_cont, que_lactalis_factory_boiler_2_ph_cont, que_lactalis_factory_boiler_2_ohalk_cont, que_lactalis_factory_boiler_2_chlorides_cont, que_lactalis_factory_boiler_2_sulphite_cont, que_lactalis_factory_boiler_2_phosphate_cont,
            que_lactalis_factory_boiler_2_hardness_cont, que_lactalis_factory_boiler_2_stndBy_TDS_cont, que_lactalis_factory_boiler_2_stndBy_ph_cont, que_lactalis_factory_boiler_2_stndBy_sulphite_cont, que_lactalis_factory_boiler_2_stndBy_ohalk_cont, que_lactalis_factory_growth_milk_cooling_tower_water_reading_cont;
    //</editor-fold>
    //<editor-fold desc="Boiler Standby component Declaration">
    private LinearLayout que_lactalis_factory_boiler_1_opstndBy_cont, que_lactalis_factory_boiler_2_opstndBy_cont;
    private boolean que_lactalis_factory_boiler_1_opstndBy = false, que_lactalis_factory_boiler_2_opstndBy = false, wifi, mobi;
    //</editor-fold>

    private double que_lactalis_factory_boiler_feed_tds_val, que_lactalis_factory_boiler_feed_ph_val, que_lactalis_factory_boiler_feed_hardness_val, que_lactalis_factory_boiler_feed_chlorides_val,
            que_lactalis_factory_boiler_feed_alkalinity_val, que_lactalis_factory_boiler_softener_hardness_val, que_lactalis_factory_boiler_softener_TDS_val, que_lactalis_factory_boiler_softener_inlet_val,
            que_lactalis_factory_boiler_softener_outlet_val, que_lactalis_factory_boiler_condensate_TDS_val, que_lactalis_factory_boiler_condensate_ph_val, que_lactalis_factory_boiler_condensate_hardness_val,
            que_lactalis_factory_boiler_1_meter_val, que_lactalis_factory_boiler_1_TDS_val, que_lactalis_factory_boiler_1_ph_val, que_lactalis_factory_boiler_1_ohalk_val,
            que_lactalis_factory_boiler_1_chlorides_val, que_lactalis_factory_boiler_1_sulphite_val, que_lactalis_factory_boiler_1_phosphate_val, que_lactalis_factory_boiler_1_hardness_val, que_lactalis_factory_boiler_1_standBy_TDS_val,
            que_lactalis_factory_boiler_1_stndBy_ph_val, que_lactalis_factory_boiler_1_stndBy_ohalk_val, que_lactalis_factory_boiler_1_stndBy_sulphite_val, que_lactalis_factory_boiler_2_meter_val,
            que_lactalis_factory_boiler_2_TDS_val, que_lactalis_factory_boiler_2_ph_val, que_lactalis_factory_boiler_2_ohalk_val, que_lactalis_factory_boiler_2_chlorides_val, que_lactalis_factory_boiler_2_sulphite_val, que_lactalis_factory_boiler_2_phosphate_val,
            que_lactalis_factory_boiler_2_hardness_val, que_lactalis_factory_boiler_2_stndBy_TDS_val, que_lactalis_factory_boiler_2_stndBy_ph_val, que_lactalis_factory_boiler_2_stndBy_sulphite_val, que_lactalis_factory_boiler_2_stndBy_ohalk_val;

    private TextView lbl_lactalis_factory_boiler_feed, lbl_lactalis_factory_boiler_softener, lbl_lactalis_factory_boiler_Condensate, lbl_lactalis_factory_boiler_1, lbl_lactalis_factory_boiler_1_stndby,
            lbl_lactalis_factory_boiler_2, lbl_lactalis_factory_boiler_2_stndby;

    private View view_lactalis_factory_boiler_feed_line, view_lactalis_factory_boiler_softener_line, view_lactalis_factory_boiler_condensate_line, view_lactalis_factory_boiler_1_line, view_lactalis_factory_boiler_1_stndby_line,
            view_lactalis_factory_boiler_2_line, view_lactalis_factory_boiler_2_stndby_line;

    private CheckBox cb_daily, cb_weekly, cb_monthly;

    private int userID;

    private double calc_lactalis_factory_boiler_softener_inlet_consumption, calc_lactalis_factory_boiler_softener_outlet_consumption, calc_lactalis_factory_boiler_1_consumption, calc_lactalis_factory_boiler_2_consumption,
            calc_lactalis_factory_boiler_condensate_return_percentage, calc_lactalis_factory_boiler_1_cycles_of_concentration_tds, calc_lactalis_factory_boiler_1_cycles_of_concentration_chlorides,
            calc_lactalis_factory_boiler_2_cycles_of_concentration_tds, calc_lactalis_factory_boiler_2_cycles_of_concentration_chlorides;
    private Methods meth;
    private final String FILENAMELOGIN = "usrLoginSession";
    private ArrayList<String> questionList, slqList;
    private EditText boiler_comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lactalis_boiler_questions);
        calendar = Calendar.getInstance();
        Log.println(Log.ASSERT, "BoilerForm", "onCreate time: " + calendar.getTimeInMillis());
        meth = new Methods();
        initComp();
        Intent oldIntent = getIntent();
        recPeriod(oldIntent);
        questionList = new ArrayList<>();
        slqList = new ArrayList<>();
        readQuestion(oldIntent);
        setDate();

    }

    @Override
    protected void onStart() {
        super.onStart();
        Calendar cal = Calendar.getInstance();
        Log.println(Log.ASSERT, "BoilerForm", "Start time: " + cal.getTimeInMillis());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Calendar cal = Calendar.getInstance();
        Log.println(Log.ASSERT, "BoilerForm", "Resume time: " + cal.getTimeInMillis());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.println(Log.ASSERT, "BoilerForm", "Restart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.println(Log.ASSERT, "BoilerForm", "Pause");
    }


    public void submitLactalisBoilerQuestions(View view) {
        que_lactalis_factory_boiler_feed_tds_val = setVal(que_lactalis_factory_boiler_feed_tds, que_lactalis_factory_boiler_feed_tds_val);
        que_lactalis_factory_boiler_feed_ph_val = setVal(que_lactalis_factory_boiler_feed_ph, que_lactalis_factory_boiler_feed_ph_val);
        que_lactalis_factory_boiler_feed_hardness_val = setVal(que_lactalis_factory_boiler_feed_hardness, que_lactalis_factory_boiler_feed_hardness_val);
        que_lactalis_factory_boiler_feed_chlorides_val = setVal(que_lactalis_factory_boiler_feed_chlorides, que_lactalis_factory_boiler_feed_chlorides_val);
        que_lactalis_factory_boiler_feed_alkalinity_val = setVal(que_lactalis_factory_boiler_feed_alkalinity, que_lactalis_factory_boiler_feed_alkalinity_val);
        que_lactalis_factory_boiler_softener_hardness_val = setVal(que_lactalis_factory_boiler_softener_hardness, que_lactalis_factory_boiler_softener_hardness_val);
        que_lactalis_factory_boiler_softener_TDS_val = setVal(que_lactalis_factory_boiler_softener_TDS, que_lactalis_factory_boiler_softener_TDS_val);
        que_lactalis_factory_boiler_softener_inlet_val = setVal(que_lactalis_factory_boiler_softener_inlet, que_lactalis_factory_boiler_softener_inlet_val);
        que_lactalis_factory_boiler_softener_outlet_val = setVal(que_lactalis_factory_boiler_softener_outlet, que_lactalis_factory_boiler_softener_outlet_val);
        que_lactalis_factory_boiler_condensate_TDS_val = setVal(que_lactalis_factory_boiler_condensate_TDS, que_lactalis_factory_boiler_condensate_TDS_val);
        que_lactalis_factory_boiler_condensate_ph_val = setVal(que_lactalis_factory_boiler_condensate_ph, que_lactalis_factory_boiler_condensate_ph_val);
        que_lactalis_factory_boiler_condensate_hardness_val = setVal(que_lactalis_factory_boiler_condensate_hardness, que_lactalis_factory_boiler_condensate_hardness_val);
        que_lactalis_factory_boiler_1_meter_val = setVal(que_lactalis_factory_boiler_1_meter, que_lactalis_factory_boiler_1_meter_val);
        que_lactalis_factory_boiler_1_TDS_val = setVal(que_lactalis_factory_boiler_1_TDS, que_lactalis_factory_boiler_1_TDS_val);
        que_lactalis_factory_boiler_1_ph_val = setVal(que_lactalis_factory_boiler_1_ph, que_lactalis_factory_boiler_1_ph_val);
        que_lactalis_factory_boiler_1_ohalk_val = setVal(que_lactalis_factory_boiler_1_ohalk, que_lactalis_factory_boiler_1_ohalk_val);
        que_lactalis_factory_boiler_1_chlorides_val = setVal(que_lactalis_factory_boiler_1_chlorides, que_lactalis_factory_boiler_1_chlorides_val);
        que_lactalis_factory_boiler_1_sulphite_val = setVal(que_lactalis_factory_boiler_1_sulphite, que_lactalis_factory_boiler_1_sulphite_val);
        que_lactalis_factory_boiler_1_phosphate_val = setVal(que_lactalis_factory_boiler_1_phosphate, que_lactalis_factory_boiler_1_phosphate_val);
        que_lactalis_factory_boiler_1_hardness_val = setVal(que_lactalis_factory_boiler_1_hardness, que_lactalis_factory_boiler_1_hardness_val);
        que_lactalis_factory_boiler_1_standBy_TDS_val = setVal(que_lactalis_factory_boiler_1_stndBy_TDS, que_lactalis_factory_boiler_1_standBy_TDS_val);
        que_lactalis_factory_boiler_1_stndBy_ph_val = setVal(que_lactalis_factory_boiler_1_stndBy_ph, que_lactalis_factory_boiler_1_stndBy_ph_val);
        que_lactalis_factory_boiler_1_stndBy_ohalk_val = setVal(que_lactalis_factory_boiler_1_stndBy_ohalk, que_lactalis_factory_boiler_1_stndBy_ohalk_val);
        que_lactalis_factory_boiler_1_stndBy_sulphite_val = setVal(que_lactalis_factory_boiler_1_stndBy_sulphite, que_lactalis_factory_boiler_1_stndBy_sulphite_val);
        que_lactalis_factory_boiler_2_meter_val = setVal(que_lactalis_factory_boiler_2_meter, que_lactalis_factory_boiler_2_meter_val);
        que_lactalis_factory_boiler_2_TDS_val = setVal(que_lactalis_factory_boiler_2_TDS, que_lactalis_factory_boiler_2_TDS_val);
        que_lactalis_factory_boiler_2_ph_val = setVal(que_lactalis_factory_boiler_2_ph, que_lactalis_factory_boiler_2_ph_val);
        que_lactalis_factory_boiler_2_ohalk_val = setVal(que_lactalis_factory_boiler_2_ohalk, que_lactalis_factory_boiler_2_ohalk_val);
        que_lactalis_factory_boiler_2_chlorides_val = setVal(que_lactalis_factory_boiler_2_chlorides, que_lactalis_factory_boiler_2_chlorides_val);
        que_lactalis_factory_boiler_2_sulphite_val = setVal(que_lactalis_factory_boiler_2_sulphite, que_lactalis_factory_boiler_2_sulphite_val);
        que_lactalis_factory_boiler_2_phosphate_val = setVal(que_lactalis_factory_boiler_2_phosphate, que_lactalis_factory_boiler_2_phosphate_val);
        que_lactalis_factory_boiler_2_hardness_val = setVal(que_lactalis_factory_boiler_2_hardness, que_lactalis_factory_boiler_2_hardness_val);
        que_lactalis_factory_boiler_2_stndBy_TDS_val = setVal(que_lactalis_factory_boiler_2_stndBy_TDS, que_lactalis_factory_boiler_2_stndBy_TDS_val);
        que_lactalis_factory_boiler_2_stndBy_ph_val = setVal(que_lactalis_factory_boiler_2_stndBy_ph, que_lactalis_factory_boiler_2_stndBy_ph_val);
        que_lactalis_factory_boiler_2_stndBy_sulphite_val = setVal(que_lactalis_factory_boiler_2_stndBy_sulphite, que_lactalis_factory_boiler_2_stndBy_sulphite_val);
        que_lactalis_factory_boiler_2_stndBy_ohalk_val = setVal(que_lactalis_factory_boiler_2_stndBy_ohalk, que_lactalis_factory_boiler_2_stndBy_ohalk_val);

        Context context = getApplicationContext();
        Calculations calc = new Calculations();
        calc_lactalis_factory_boiler_condensate_return_percentage = calc.estCondensateReturn(context, "LactalisFactoryBoilerOneMeterReading", "LactalisFactoryBoilerTwoMeterReading", "LactalisFactoryBoilerSoftenerOutletReading", que_lactalis_factory_boiler_1_meter_val, que_lactalis_factory_boiler_2_meter_val, que_lactalis_factory_boiler_softener_outlet_val);
        calc.saveForConsumptionReading(que_lactalis_factory_boiler_1_meter_val, "LactalisFactoryBoilerOneMeterReading", context);
        calc.saveForConsumptionReading(que_lactalis_factory_boiler_2_meter_val, "LactalisFactoryBoilerTwoMeterReading", context);
        //wifi = meth.isConnectedWifi(getApplicationContext());
        //mobi = meth.isConnectedMobile(getApplicationContext());

        generatQuestionAnswerSQL(userID, que_lactalis_factory_boiler_feed_tds_val, que_lactalis_factory_boiler_feed_tds);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_boiler_feed_ph_val, que_lactalis_factory_boiler_feed_ph);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_boiler_feed_hardness_val, que_lactalis_factory_boiler_feed_hardness);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_boiler_feed_chlorides_val, que_lactalis_factory_boiler_feed_chlorides);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_boiler_feed_alkalinity_val, que_lactalis_factory_boiler_feed_alkalinity);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_boiler_softener_hardness_val, que_lactalis_factory_boiler_softener_hardness);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_boiler_softener_TDS_val, que_lactalis_factory_boiler_softener_TDS);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_boiler_softener_inlet_val, que_lactalis_factory_boiler_softener_inlet);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_boiler_softener_outlet_val, que_lactalis_factory_boiler_softener_outlet);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_boiler_condensate_TDS_val, que_lactalis_factory_boiler_condensate_TDS);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_boiler_condensate_ph_val, que_lactalis_factory_boiler_condensate_ph);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_boiler_condensate_hardness_val, que_lactalis_factory_boiler_condensate_hardness);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_boiler_1_meter_val, que_lactalis_factory_boiler_1_meter);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_boiler_1_TDS_val, que_lactalis_factory_boiler_1_TDS);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_boiler_1_ph_val, que_lactalis_factory_boiler_1_ph);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_boiler_1_ohalk_val, que_lactalis_factory_boiler_1_ohalk);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_boiler_1_chlorides_val, que_lactalis_factory_boiler_1_chlorides);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_boiler_1_sulphite_val, que_lactalis_factory_boiler_1_sulphite);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_boiler_1_phosphate_val, que_lactalis_factory_boiler_1_phosphate);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_boiler_1_hardness_val, que_lactalis_factory_boiler_1_hardness);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_boiler_1_standBy_TDS_val, que_lactalis_factory_boiler_1_stndBy_TDS);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_boiler_1_stndBy_ph_val, que_lactalis_factory_boiler_1_stndBy_ph);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_boiler_1_stndBy_ohalk_val, que_lactalis_factory_boiler_1_stndBy_ohalk);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_boiler_1_stndBy_sulphite_val, que_lactalis_factory_boiler_1_stndBy_sulphite);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_boiler_2_meter_val, que_lactalis_factory_boiler_2_meter);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_boiler_2_TDS_val, que_lactalis_factory_boiler_2_TDS);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_boiler_2_ph_val, que_lactalis_factory_boiler_2_ph);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_boiler_2_ohalk_val, que_lactalis_factory_boiler_2_ohalk);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_boiler_2_chlorides_val, que_lactalis_factory_boiler_2_chlorides);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_boiler_2_sulphite_val, que_lactalis_factory_boiler_2_sulphite);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_boiler_2_phosphate_val, que_lactalis_factory_boiler_2_phosphate);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_boiler_2_hardness_val, que_lactalis_factory_boiler_2_hardness);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_boiler_2_stndBy_TDS_val, que_lactalis_factory_boiler_2_stndBy_TDS);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_boiler_2_stndBy_ph_val, que_lactalis_factory_boiler_2_stndBy_ph);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_boiler_2_stndBy_sulphite_val, que_lactalis_factory_boiler_2_stndBy_sulphite);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_boiler_2_stndBy_ohalk_val, que_lactalis_factory_boiler_2_stndBy_ohalk);

        sendForSync();
        new AsyncSubmitComments().execute();
        finish();


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

    public void checkBoxCheck(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        System.out.println(checked + "-" + ((CheckBox) view).isChecked() + "-" + view.getId());
        switch (view.getId()) {
            case R.id.cb_Lactalis_Boiler_daily:
                if (checked == true) {
                    dailyRecordingBoilerShow();
                    weeklyRecordingsBoilerHide();
                    monthlyRecordingBoilerHide();
                    cb_weekly.setChecked(false);
                    cb_monthly.setChecked(false);
                } else {
                    dailyRecordingBoilerHide();
                }
                break;
            case R.id.cb_Lactalis_Boiler_weekly:
                if (checked) {
                    dailyRecordingBoilerShow();
                    weeklyRecordingBoilerShow();
                    cb_daily.setChecked(false);
                    cb_monthly.setChecked(false);
                } else {
                    dailyRecordingBoilerHide();
                    weeklyRecordingsBoilerHide();
                }
                break;
            case R.id.cb_Lactalis_Boiler_monthly:
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

    private final View.OnClickListener checkboxCheck = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            boolean checked = ((CheckBox) v).isChecked();
            System.out.println(checked + "-" + ((CheckBox) v).isChecked() + "-" + v.getId());
            switch (v.getId()) {
                case R.id.cb_Lactalis_Boiler_daily:
                    if (checked == true) {
                        dailyRecordingBoilerShow();
                        cb_weekly.setChecked(false);
                        cb_monthly.setChecked(false);
                    } else {
                        dailyRecordingBoilerHide();
                        weeklyRecordingsBoilerHide();
                        monthlyRecordingBoilerHide();
                    }
                    break;
                case R.id.cb_Lactalis_Boiler_weekly:
                    if (checked) {
                        dailyRecordingBoilerShow();
                        weeklyRecordingBoilerShow();
                        cb_daily.setChecked(false);
                        cb_monthly.setChecked(false);
                    } else {
                        dailyRecordingBoilerHide();
                        weeklyRecordingsBoilerHide();
                        monthlyRecordingBoilerHide();
                    }
                    break;
                case R.id.cb_Lactalis_Boiler_monthly:
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

    public void radioButtonCheck(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.rdbn_lactalis_factory_boiler_1_stndBy_yes:
                if (checked) {
                    que_lactalis_factory_boiler_1_opstndBy = true;
                    monthlyRecordingBoilerShow();
                }
                break;
            case R.id.rdbn_lactalis_factory_boiler_1_stndBy_no:
                if (checked) {
                    que_lactalis_factory_boiler_1_opstndBy = false;
                    monthlyRecordingBoilerShow();
                }
                break;
            case R.id.rdbn_lactalis_factory_boiler_2_stndBy_yes:
                if (checked) {
                    que_lactalis_factory_boiler_2_opstndBy = true;
                    monthlyRecordingBoilerShow();
                }
                break;
            case R.id.rdbn_lactalis_factory_boiler_2_stndBy_no:
                if (checked) {
                    que_lactalis_factory_boiler_2_opstndBy = false;
                    monthlyRecordingBoilerShow();
                }
                break;
        }
    }

    public void dailyRecordingBoilerShow() {
        que_lactalis_factory_boiler_feed_hardness_cont.setVisibility(View.VISIBLE); //Day
        que_lactalis_factory_boiler_softener_hardness_cont.setVisibility(View.VISIBLE); //Day
        que_lactalis_factory_boiler_condensate_hardness_cont.setVisibility(View.VISIBLE); //Day
        que_lactalis_factory_boiler_softener_inlet_cont.setVisibility(View.VISIBLE); //Day
        que_lactalis_factory_boiler_softener_outlet_cont.setVisibility(View.VISIBLE); //Day
        que_lactalis_factory_boiler_1_meter_cont.setVisibility(View.VISIBLE); //Day
        que_lactalis_factory_boiler_2_meter_cont.setVisibility(View.VISIBLE); //Day
        que_lactalis_factory_boiler_1_opstndBy_cont.setVisibility(View.VISIBLE); //Day
        que_lactalis_factory_boiler_2_opstndBy_cont.setVisibility(View.VISIBLE); //Day

        lbl_lactalis_factory_boiler_feed.setVisibility(View.VISIBLE);
        view_lactalis_factory_boiler_feed_line.setVisibility(View.VISIBLE);
        lbl_lactalis_factory_boiler_softener.setVisibility(View.VISIBLE);
        view_lactalis_factory_boiler_softener_line.setVisibility(View.VISIBLE);
        lbl_lactalis_factory_boiler_Condensate.setVisibility(View.VISIBLE);
        view_lactalis_factory_boiler_condensate_line.setVisibility(View.VISIBLE);
        lbl_lactalis_factory_boiler_1.setVisibility(View.VISIBLE);
        view_lactalis_factory_boiler_1_line.setVisibility(View.VISIBLE);
        lbl_lactalis_factory_boiler_2.setVisibility(View.VISIBLE);


    }

    public void weeklyRecordingBoilerShow() {
        //<editor-fold desc="Boiler Feed & Condensate Qs">
        que_lactalis_factory_boiler_feed_tds_cont.setVisibility(View.VISIBLE); //weekly
        que_lactalis_factory_boiler_feed_ph_cont.setVisibility(View.VISIBLE); //weekly
        que_lactalis_factory_boiler_feed_chlorides_cont.setVisibility(View.VISIBLE); //weekly
        que_lactalis_factory_boiler_feed_alkalinity_cont.setVisibility(View.VISIBLE); //weekly
        que_lactalis_factory_boiler_softener_TDS_cont.setVisibility(View.VISIBLE); //weekly
        que_lactalis_factory_boiler_condensate_TDS_cont.setVisibility(View.VISIBLE); //weekly
        que_lactalis_factory_boiler_condensate_ph_cont.setVisibility(View.VISIBLE); //weekly
        //</editor-fold>
        //<editor-fold desc="Boiler 1 Weekly Qs">
        if (!que_lactalis_factory_boiler_1_opstndBy) {
            que_lactalis_factory_boiler_1_TDS_cont.setVisibility(View.VISIBLE); //weekly
            que_lactalis_factory_boiler_1_ph_cont.setVisibility(View.VISIBLE); //weekly
            que_lactalis_factory_boiler_1_ohalk_cont.setVisibility(View.VISIBLE); //weekly
            que_lactalis_factory_boiler_1_chlorides_cont.setVisibility(View.VISIBLE); //weekly
            que_lactalis_factory_boiler_1_sulphite_cont.setVisibility(View.VISIBLE); //weekly
            que_lactalis_factory_boiler_1_phosphate_cont.setVisibility(View.VISIBLE); //weekly
            que_lactalis_factory_boiler_1_hardness_cont.setVisibility(View.VISIBLE); //weekly
        }
        //</editor-fold>
        //<editor-fold desc="Boiler 2 Weekly Qs">
        if (!que_lactalis_factory_boiler_2_opstndBy) {
            que_lactalis_factory_boiler_2_TDS_cont.setVisibility(View.VISIBLE); //weekly
            que_lactalis_factory_boiler_2_ph_cont.setVisibility(View.VISIBLE); //weekly
            que_lactalis_factory_boiler_2_ohalk_cont.setVisibility(View.VISIBLE); //weekly
            que_lactalis_factory_boiler_2_chlorides_cont.setVisibility(View.VISIBLE); //weekly
            que_lactalis_factory_boiler_2_sulphite_cont.setVisibility(View.VISIBLE); //weekly
            que_lactalis_factory_boiler_2_phosphate_cont.setVisibility(View.VISIBLE); //weekly
            que_lactalis_factory_boiler_2_hardness_cont.setVisibility(View.VISIBLE); // weekly
        }
        //</editor-fold>
    }

    public void monthlyRecordingBoilerShow() {
        //<editor-fold desc="Boiler 1 StandBy Cond Qs">
        que_lactalis_factory_boiler_1_opstndBy_cont.setVisibility(View.VISIBLE); //monthly

        if (que_lactalis_factory_boiler_1_opstndBy) {
            que_lactalis_factory_boiler_1_standBy_TDS_cont.setVisibility(View.VISIBLE); //monthly
            que_lactalis_factory_boiler_1_stndBy_ph_cont.setVisibility(View.VISIBLE);
            que_lactalis_factory_boiler_1_stndBy_sulphite_cont.setVisibility(View.VISIBLE);
            que_lactalis_factory_boiler_1_stndBy_ohalk_cont.setVisibility(View.VISIBLE);
            que_lactalis_factory_boiler_1_TDS_cont.setVisibility(View.GONE);
            que_lactalis_factory_boiler_1_ph_cont.setVisibility(View.GONE);
            que_lactalis_factory_boiler_1_ohalk_cont.setVisibility(View.GONE);
            que_lactalis_factory_boiler_1_chlorides_cont.setVisibility(View.GONE);
            que_lactalis_factory_boiler_1_sulphite_cont.setVisibility(View.GONE);
            que_lactalis_factory_boiler_1_phosphate_cont.setVisibility(View.GONE);
            que_lactalis_factory_boiler_1_hardness_cont.setVisibility(View.GONE);

        }
        else
        {
            que_lactalis_factory_boiler_1_standBy_TDS_cont.setVisibility(View.GONE); //monthly
            que_lactalis_factory_boiler_1_stndBy_ph_cont.setVisibility(View.GONE);
            que_lactalis_factory_boiler_1_stndBy_sulphite_cont.setVisibility(View.GONE);
            que_lactalis_factory_boiler_1_stndBy_ohalk_cont.setVisibility(View.GONE);
        }
        //</editor-fold>
        //<editor-fold desc="Boiler 2 StandBy Cond Qs">
        que_lactalis_factory_boiler_2_opstndBy_cont.setVisibility(View.VISIBLE); //monthly
        if (que_lactalis_factory_boiler_2_opstndBy) {
            que_lactalis_factory_boiler_2_stndBy_TDS_cont.setVisibility(View.VISIBLE);
            que_lactalis_factory_boiler_2_stndBy_ph_cont.setVisibility(View.VISIBLE);
            que_lactalis_factory_boiler_2_stndBy_sulphite_cont.setVisibility(View.VISIBLE);
            que_lactalis_factory_boiler_2_stndBy_ohalk_cont.setVisibility(View.VISIBLE);
            que_lactalis_factory_boiler_2_TDS_cont.setVisibility(View.GONE);
            que_lactalis_factory_boiler_2_ph_cont.setVisibility(View.GONE);
            que_lactalis_factory_boiler_2_ohalk_cont.setVisibility(View.GONE);
            que_lactalis_factory_boiler_2_chlorides_cont.setVisibility(View.GONE);
            que_lactalis_factory_boiler_2_sulphite_cont.setVisibility(View.GONE);
            que_lactalis_factory_boiler_2_phosphate_cont.setVisibility(View.GONE);
            que_lactalis_factory_boiler_2_hardness_cont.setVisibility(View.GONE);
        }
        else
        {
            que_lactalis_factory_boiler_2_stndBy_TDS_cont.setVisibility(View.GONE);
            que_lactalis_factory_boiler_2_stndBy_ph_cont.setVisibility(View.GONE);
            que_lactalis_factory_boiler_2_stndBy_sulphite_cont.setVisibility(View.GONE);
            que_lactalis_factory_boiler_2_stndBy_ohalk_cont.setVisibility(View.GONE);
        }
        //</editor-fold>

    }

    public void dailyRecordingBoilerHide() {
        que_lactalis_factory_boiler_feed_hardness_cont.setVisibility(View.GONE);
        que_lactalis_factory_boiler_softener_hardness_cont.setVisibility(View.GONE);
        que_lactalis_factory_boiler_condensate_hardness_cont.setVisibility(View.GONE);
        que_lactalis_factory_boiler_softener_inlet_cont.setVisibility(View.GONE);
        que_lactalis_factory_boiler_softener_outlet_cont.setVisibility(View.GONE);
        que_lactalis_factory_boiler_1_meter_cont.setVisibility(View.GONE);
        que_lactalis_factory_boiler_2_meter_cont.setVisibility(View.GONE);
        que_lactalis_factory_boiler_1_opstndBy_cont.setVisibility(View.GONE);
        que_lactalis_factory_boiler_2_opstndBy_cont.setVisibility(View.GONE);
    }

    public void weeklyRecordingsBoilerHide() {
        //<editor-fold desc="Boiler Feed & Condensate Qs">
        que_lactalis_factory_boiler_feed_tds_cont.setVisibility(View.GONE);
        que_lactalis_factory_boiler_feed_ph_cont.setVisibility(View.GONE);
        que_lactalis_factory_boiler_feed_chlorides_cont.setVisibility(View.GONE);
        que_lactalis_factory_boiler_feed_alkalinity_cont.setVisibility(View.GONE);
        que_lactalis_factory_boiler_softener_TDS_cont.setVisibility(View.GONE);
        que_lactalis_factory_boiler_condensate_TDS_cont.setVisibility(View.GONE);
        que_lactalis_factory_boiler_condensate_ph_cont.setVisibility(View.GONE);
        //</editor-fold>
        //<editor-fold desc="Boiler 1 Weekly Qs">
        if (!que_lactalis_factory_boiler_1_opstndBy) {
            que_lactalis_factory_boiler_1_TDS_cont.setVisibility(View.GONE);
            que_lactalis_factory_boiler_1_ph_cont.setVisibility(View.GONE);
            que_lactalis_factory_boiler_1_ohalk_cont.setVisibility(View.GONE);
            que_lactalis_factory_boiler_1_chlorides_cont.setVisibility(View.GONE);
            que_lactalis_factory_boiler_1_sulphite_cont.setVisibility(View.GONE);
            que_lactalis_factory_boiler_1_phosphate_cont.setVisibility(View.GONE);
            que_lactalis_factory_boiler_1_hardness_cont.setVisibility(View.GONE);
        }
        //</editor-fold>
        //<editor-fold desc="Boiler 2 Weekly Qs">
        if (!que_lactalis_factory_boiler_2_opstndBy) {
            que_lactalis_factory_boiler_2_TDS_cont.setVisibility(View.GONE);
            que_lactalis_factory_boiler_2_ph_cont.setVisibility(View.GONE);
            que_lactalis_factory_boiler_2_ohalk_cont.setVisibility(View.GONE);
            que_lactalis_factory_boiler_2_chlorides_cont.setVisibility(View.GONE);
            que_lactalis_factory_boiler_2_sulphite_cont.setVisibility(View.GONE);
            que_lactalis_factory_boiler_2_phosphate_cont.setVisibility(View.GONE);
            que_lactalis_factory_boiler_2_hardness_cont.setVisibility(View.GONE);
        }
        //</editor-fold>
    }

    public void monthlyRecordingBoilerHide() {
        //<editor-fold desc="Boiler 1 StandBy Cond Qs">
        que_lactalis_factory_boiler_1_opstndBy_cont.setVisibility(View.GONE); //monthly

        if (que_lactalis_factory_boiler_1_opstndBy) {
            que_lactalis_factory_boiler_1_standBy_TDS_cont.setVisibility(View.GONE);
            que_lactalis_factory_boiler_1_stndBy_ph_cont.setVisibility(View.GONE);
            que_lactalis_factory_boiler_1_stndBy_sulphite_cont.setVisibility(View.GONE);
            que_lactalis_factory_boiler_1_stndBy_ohalk_cont.setVisibility(View.GONE);
        }
        //</editor-fold>
        //<editor-fold desc="Boiler 2 StandBy Cond Qs">
        que_lactalis_factory_boiler_2_opstndBy_cont.setVisibility(View.GONE); //monthly

        if (que_lactalis_factory_boiler_2_opstndBy) {
            que_lactalis_factory_boiler_2_stndBy_TDS_cont.setVisibility(View.GONE);
            que_lactalis_factory_boiler_2_stndBy_ph_cont.setVisibility(View.GONE);
            que_lactalis_factory_boiler_2_stndBy_sulphite_cont.setVisibility(View.GONE);
            que_lactalis_factory_boiler_2_stndBy_ohalk_cont.setVisibility(View.GONE);
        }
        //</editor-fold>
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
        new AsyncSubmitComments().execute();
    }

    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "Select A Date",
                Toast.LENGTH_SHORT)
                .show();
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

    class AsyncSubmitComments extends AsyncTask<Void, Void, Void> {

        String error = "";
        String comment = boiler_comments.getText().toString().trim();

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
                    Log.println(Log.ASSERT, "BoilerForm", prepStatment.toString());
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
                Toast.makeText(getApplicationContext(), "Could not Submit Comment, please ensure you have an Internet Connection", Toast.LENGTH_LONG).show();
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
        //<editor-fold desc="Container Declaration">
        que_lactalis_factory_boiler_feed_tds_cont = findViewById(R.id.que_lactalis_factory_boiler_feed_tds_cont);
        que_lactalis_factory_boiler_feed_ph_cont = findViewById(R.id.que_lactalis_factory_boiler_feed_ph_cont);
        que_lactalis_factory_boiler_feed_hardness_cont = findViewById(R.id.que_lactalis_factory_boiler_feed_hardness_cont);
        que_lactalis_factory_boiler_feed_chlorides_cont = findViewById(R.id.que_lactalis_factory_boiler_feed_chlorides_cont);
        que_lactalis_factory_boiler_feed_alkalinity_cont = findViewById(R.id.que_lactalis_factory_boiler_feed_alkalinity_cont);
        que_lactalis_factory_boiler_softener_hardness_cont = findViewById(R.id.que_lactalis_factory_boiler_softener_hardness_cont);
        que_lactalis_factory_boiler_softener_TDS_cont = findViewById(R.id.que_lactalis_factory_boiler_softener_TDS_cont);
        que_lactalis_factory_boiler_softener_inlet_cont = findViewById(R.id.que_lactalis_factory_boiler_softener_inlet_cont);
        que_lactalis_factory_boiler_softener_outlet_cont = findViewById(R.id.que_lactalis_factory_boiler_softener_outlet_cont);
        que_lactalis_factory_boiler_condensate_TDS_cont = findViewById(R.id.que_lactalis_factory_boiler_condensate_TDS_cont);
        que_lactalis_factory_boiler_condensate_ph_cont = findViewById(R.id.que_lactalis_factory_boiler_condensate_ph_cont);
        que_lactalis_factory_boiler_condensate_hardness_cont = findViewById(R.id.que_lactalis_factory_boiler_condensate_hardness_cont);
        que_lactalis_factory_boiler_1_meter_cont = findViewById(R.id.que_lactalis_factory_boiler_1_meter_cont);
        que_lactalis_factory_boiler_1_TDS_cont = findViewById(R.id.que_lactalis_factory_boiler_1_TDS_cont);
        que_lactalis_factory_boiler_1_ph_cont = findViewById(R.id.que_lactalis_factory_boiler_1_ph_cont);
        que_lactalis_factory_boiler_1_ohalk_cont = findViewById(R.id.que_lactalis_factory_boiler_1_ohalk_cont);
        que_lactalis_factory_boiler_1_chlorides_cont = findViewById(R.id.que_lactalis_factory_boiler_1_chlorides_cont);
        que_lactalis_factory_boiler_1_sulphite_cont = findViewById(R.id.que_lactalis_factory_boiler_1_sulphite_cont);
        que_lactalis_factory_boiler_1_phosphate_cont = findViewById(R.id.que_lactalis_factory_boiler_1_phosphate_cont);
        que_lactalis_factory_boiler_1_hardness_cont = findViewById(R.id.que_lactalis_factory_boiler_1_hardness_cont);
        que_lactalis_factory_boiler_1_standBy_TDS_cont = findViewById(R.id.que_lactalis_factory_boiler_1_standBy_TDS_cont);
        que_lactalis_factory_boiler_1_stndBy_ph_cont = findViewById(R.id.que_lactalis_factory_boiler_1_stndBy_ph_cont);
        que_lactalis_factory_boiler_1_stndBy_ohalk_cont = findViewById(R.id.que_lactalis_factory_boiler_1_stndBy_ohalk_cont);
        que_lactalis_factory_boiler_1_stndBy_sulphite_cont = findViewById(R.id.que_lactalis_factory_boiler_1_stndBy_sulphite_cont);
        que_lactalis_factory_boiler_2_meter_cont = findViewById(R.id.que_lactalis_factory_boiler_2_meter_cont);
        que_lactalis_factory_boiler_2_TDS_cont = findViewById(R.id.que_lactalis_factory_boiler_2_TDS_cont);
        que_lactalis_factory_boiler_2_ph_cont = findViewById(R.id.que_lactalis_factory_boiler_2_ph_cont);
        que_lactalis_factory_boiler_2_ohalk_cont = findViewById(R.id.que_lactalis_factory_boiler_2_ohalk_cont);
        que_lactalis_factory_boiler_2_chlorides_cont = findViewById(R.id.que_lactalis_factory_boiler_2_chlorides_cont);
        que_lactalis_factory_boiler_2_sulphite_cont = findViewById(R.id.que_lactalis_factory_boiler_2_sulphite_cont);
        que_lactalis_factory_boiler_2_phosphate_cont = findViewById(R.id.que_lactalis_factory_boiler_2_phosphate_cont);
        que_lactalis_factory_boiler_2_hardness_cont = findViewById(R.id.que_lactalis_factory_boiler_2_hardness_cont);
        que_lactalis_factory_boiler_2_stndBy_TDS_cont = findViewById(R.id.que_lactalis_factory_boiler_2_stndBy_TDS_cont);
        que_lactalis_factory_boiler_2_stndBy_ph_cont = findViewById(R.id.que_lactalis_factory_boiler_2_stndBy_ph_cont);
        que_lactalis_factory_boiler_2_stndBy_sulphite_cont = findViewById(R.id.que_lactalis_factory_boiler_2_stndBy_sulphite_cont);
        que_lactalis_factory_boiler_2_stndBy_ohalk_cont = findViewById(R.id.que_lactalis_factory_boiler_2_stndBy_ohalk_cont);
        //</editor-fold>
        //<editor-fold desc="Boiler Feed, -Softener, -Condensate Declaration">
        que_lactalis_factory_boiler_feed_tds = findViewById(R.id.que_lactalis_factory_boiler_feed_tds);
        que_lactalis_factory_boiler_feed_ph = findViewById(R.id.que_lactalis_factory_boiler_feed_ph);
        que_lactalis_factory_boiler_feed_hardness = findViewById(R.id.que_lactalis_factory_boiler_feed_hardness);
        que_lactalis_factory_boiler_feed_chlorides = findViewById(R.id.que_lactalis_factory_boiler_feed_chlorides);
        que_lactalis_factory_boiler_feed_alkalinity = findViewById(R.id.que_lactalis_factory_boiler_feed_alkalinity);
        que_lactalis_factory_boiler_softener_hardness = findViewById(R.id.que_lactalis_factory_boiler_softener_hardness);
        que_lactalis_factory_boiler_softener_TDS = findViewById(R.id.que_lactalis_factory_boiler_softener_TDS);
        que_lactalis_factory_boiler_softener_inlet = findViewById(R.id.que_lactalis_factory_boiler_softener_inlet);
        que_lactalis_factory_boiler_softener_outlet = findViewById(R.id.que_lactalis_factory_boiler_softener_outlet);
        que_lactalis_factory_boiler_condensate_TDS = findViewById(R.id.que_lactalis_factory_boiler_condensate_TDS);
        que_lactalis_factory_boiler_condensate_ph = findViewById(R.id.que_lactalis_factory_boiler_condensate_ph);
        que_lactalis_factory_boiler_condensate_hardness = findViewById(R.id.que_lactalis_factory_boiler_condensate_hardness);
        //</editor-fold>
        //<editor-fold desc="Boiler 1 Questions">
        que_lactalis_factory_boiler_1_meter = findViewById(R.id.que_lactalis_factory_boiler_1_meter);
        que_lactalis_factory_boiler_1_TDS = findViewById(R.id.que_lactalis_factory_boiler_1_TDS);
        que_lactalis_factory_boiler_1_ph = findViewById(R.id.que_lactalis_factory_boiler_1_ph);
        que_lactalis_factory_boiler_1_ohalk = findViewById(R.id.que_lactalis_factory_boiler_1_ohalk);
        que_lactalis_factory_boiler_1_chlorides = findViewById(R.id.que_lactalis_factory_boiler_1_chlorides);
        que_lactalis_factory_boiler_1_sulphite = findViewById(R.id.que_lactalis_factory_boiler_1_sulphite);
        que_lactalis_factory_boiler_1_phosphate = findViewById(R.id.que_lactalis_factory_boiler_1_phosphate);
        que_lactalis_factory_boiler_1_hardness = findViewById(R.id.que_lactalis_factory_boiler_1_hardness);
        que_lactalis_factory_boiler_1_stndBy_TDS = findViewById(R.id.que_lactalis_factory_boiler_1_stndBy_TDS);
        que_lactalis_factory_boiler_1_stndBy_ph = findViewById(R.id.que_lactalis_factory_boiler_1_stndBy_ph);
        que_lactalis_factory_boiler_1_stndBy_ohalk = findViewById(R.id.que_lactalis_factory_boiler_1_stndBy_ohalk);
        que_lactalis_factory_boiler_1_stndBy_sulphite = findViewById(R.id.que_lactalis_factory_boiler_1_stndBy_sulphite);
        //</editor-fold>
        //<editor-fold desc="Boiler 2 Questions">
        que_lactalis_factory_boiler_2_meter = findViewById(R.id.que_lactalis_factory_boiler_2_meter);
        que_lactalis_factory_boiler_2_TDS = findViewById(R.id.que_lactalis_factory_boiler_2_TDS);
        que_lactalis_factory_boiler_2_ph = findViewById(R.id.que_lactalis_factory_boiler_2_ph);
        que_lactalis_factory_boiler_2_ohalk = findViewById(R.id.que_lactalis_factory_boiler_2_ohalk);
        que_lactalis_factory_boiler_2_chlorides = findViewById(R.id.que_lactalis_factory_boiler_2_chlorides);
        que_lactalis_factory_boiler_2_sulphite = findViewById(R.id.que_lactalis_factory_boiler_2_sulphite);
        que_lactalis_factory_boiler_2_phosphate = findViewById(R.id.que_lactalis_factory_boiler_2_phosphate);
        que_lactalis_factory_boiler_2_hardness = findViewById(R.id.que_lactalis_factory_boiler_2_hardness);
        que_lactalis_factory_boiler_2_stndBy_TDS = findViewById(R.id.que_lactalis_factory_boiler_2_stndBy_TDS);
        que_lactalis_factory_boiler_2_stndBy_ph = findViewById(R.id.que_lactalis_factory_boiler_2_stndBy_ph);
        que_lactalis_factory_boiler_2_stndBy_sulphite = findViewById(R.id.que_lactalis_factory_boiler_2_stndBy_sulphite);
        que_lactalis_factory_boiler_2_stndBy_ohalk = findViewById(R.id.que_lactalis_factory_boiler_2_stndBy_ohalk);
        //</editor-fold>
        //<editor-fold desc="Boiler Standby Container Declaration">
        que_lactalis_factory_boiler_1_opstndBy_cont = findViewById(R.id.que_lactalis_factory_boiler_1_opstndBy_cont);
        que_lactalis_factory_boiler_2_opstndBy_cont = findViewById(R.id.que_lactalis_factory_boiler_2_opstndBy_cont);
        //</editor-fold>
        //<editor-fold desc="Checkbox Declaration">
        cb_daily = findViewById(R.id.cb_Lactalis_Boiler_daily);
        cb_weekly = findViewById(R.id.cb_Lactalis_Boiler_weekly);
        cb_monthly = findViewById(R.id.cb_Lactalis_Boiler_monthly);

        cb_daily.setOnClickListener(checkboxCheck);
        cb_weekly.setOnClickListener(checkboxCheck);
        cb_monthly.setOnClickListener(checkboxCheck);
        //</editor-fold>
        //<editor-fold desc="TextView Declaration">
        lbl_lactalis_factory_boiler_feed = findViewById(R.id.lbl_lactalis_factory_boiler_feed);
        lbl_lactalis_factory_boiler_softener = findViewById(R.id.lbl_lactalis_factory_boiler_softener);
        lbl_lactalis_factory_boiler_Condensate = findViewById(R.id.lbl_lactalis_factory_boiler_Condensate);
        lbl_lactalis_factory_boiler_1 = findViewById(R.id.lbl_lactalis_factory_boiler_1);
        lbl_lactalis_factory_boiler_1_stndby = findViewById(R.id.lbl_lactalis_factory_boiler_1_stndby);
        lbl_lactalis_factory_boiler_2 = findViewById(R.id.lbl_lactalis_factory_boiler_2);
        lbl_lactalis_factory_boiler_2_stndby = findViewById(R.id.lbl_lactalis_factory_boiler_2_stndby);
        //</editor-fold>
        //<editor-fold desc="View Declaration">
        view_lactalis_factory_boiler_feed_line = findViewById(R.id.view_lactalis_factory_boiler_feed_line);
        view_lactalis_factory_boiler_softener_line = findViewById(R.id.view_lactalis_factory_boiler_softener_line);
        view_lactalis_factory_boiler_condensate_line = findViewById(R.id.view_lactalis_factory_boiler_condensate_line);
        view_lactalis_factory_boiler_1_line = findViewById(R.id.view_lactalis_factory_boiler_1_line);
        view_lactalis_factory_boiler_1_stndby_line = findViewById(R.id.view_lactalis_factory_boiler_1_stndby_line);
        view_lactalis_factory_boiler_2_line = findViewById(R.id.view_lactalis_factory_boiler_2_line);
        view_lactalis_factory_boiler_2_stndby_line = findViewById(R.id.view_lactalis_factory_boiler_2_stndby_line);
        //</editor-fold>
        boiler_comments = findViewById(R.id.txa_lactalis_factory_boiler_comments);
        dateView = findViewById(R.id.lbl_date_selection);
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