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

public class lactalisMunicipalWaterQuestions extends AppCompatActivity {

    private final String FORMID = "Lactalis_Factory_Municipal_Water_Form";

    private EditText que_lactalis_factory_municipal_water_tds, que_lactalis_factory_municipal_water_conductivity, que_lactalis_factory_municipal_water_ph, que_lactalis_factory_municipal_water_hardness,
            que_lactalis_factory_municipal_water_chlorides, que_lactalis_factory_municipal_water_phosphonate, que_lactalis_factory_municipal_water_alkalinity, que_lactalis_factory_municipal_water_meter,
            factoryMunicipalWaterComments;


    private TextInputLayout que_lactalis_factory_municipal_water_tds_cont, que_lactalis_factory_municipal_water_conductivity_cont, que_lactalis_factory_municipal_water_ph_cont, que_lactalis_factory_municipal_water_hardness_cont,
            que_lactalis_factory_municipal_water_chlorides_cont, que_lactalis_factory_municipal_water_phosphonate_cont, que_lactalis_factory_municipal_water_alkalinity_cont, que_lactalis_factory_municipal_water_meter_cont;


    private double que_lactalis_factory_municipal_water_tds_val, que_lactalis_factory_municipal_water_conductivity_val, que_lactalis_factory_municipal_water_ph_val, que_lactalis_factory_municipal_water_hardness_val,
            que_lactalis_factory_municipal_water_chlorides_val, que_lactalis_factory_municipal_water_phosphonate_val, que_lactalis_factory_municipal_water_alkalinity_val, que_lactalis_factory_municipal_water_meter_val,
            calc_lactalis_factory_municipal_water_meter_consumption_val;


    private ArrayList<String> questionList, slqList;

    private CheckBox cb_daily, cb_weekly, cb_monthly;


    private int userID;
    private Methods meth;
    private final String FILENAMELOGIN = "usrLoginSession";
    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day, hour, min, sec;
    private String date, dateTime;
    private TextView dateView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lactalis_municipal_water_questions);
        meth = new Methods();
        initComp();
        Intent oldIntent = getIntent();
        recPeriod(oldIntent);
        readQuestion(oldIntent);
        slqList = new ArrayList<>();
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

    public void submitLactalisFactoryMunicipalWater(View view) {
        que_lactalis_factory_municipal_water_tds_val = setVal(que_lactalis_factory_municipal_water_tds, que_lactalis_factory_municipal_water_tds_val);
        que_lactalis_factory_municipal_water_conductivity_val = setVal(que_lactalis_factory_municipal_water_conductivity, que_lactalis_factory_municipal_water_conductivity_val);
        que_lactalis_factory_municipal_water_ph_val = setVal(que_lactalis_factory_municipal_water_ph, que_lactalis_factory_municipal_water_ph_val);
        que_lactalis_factory_municipal_water_hardness_val = setVal(que_lactalis_factory_municipal_water_hardness, que_lactalis_factory_municipal_water_hardness_val);
        que_lactalis_factory_municipal_water_chlorides_val = setVal(que_lactalis_factory_municipal_water_chlorides, que_lactalis_factory_municipal_water_chlorides_val);
        que_lactalis_factory_municipal_water_phosphonate_val = setVal(que_lactalis_factory_municipal_water_phosphonate, que_lactalis_factory_municipal_water_phosphonate_val);
        que_lactalis_factory_municipal_water_alkalinity_val = setVal(que_lactalis_factory_municipal_water_alkalinity, que_lactalis_factory_municipal_water_alkalinity_val);
        que_lactalis_factory_municipal_water_meter_val = setVal(que_lactalis_factory_municipal_water_meter, que_lactalis_factory_municipal_water_meter_val);

        generatQuestionAnswerSQL(userID, que_lactalis_factory_municipal_water_tds_val, que_lactalis_factory_municipal_water_tds);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_municipal_water_conductivity_val, que_lactalis_factory_municipal_water_conductivity);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_municipal_water_ph_val, que_lactalis_factory_municipal_water_ph);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_municipal_water_hardness_val, que_lactalis_factory_municipal_water_hardness);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_municipal_water_chlorides_val, que_lactalis_factory_municipal_water_chlorides);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_municipal_water_phosphonate_val, que_lactalis_factory_municipal_water_phosphonate);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_municipal_water_alkalinity_val, que_lactalis_factory_municipal_water_alkalinity);
        generatQuestionAnswerSQL(userID, que_lactalis_factory_municipal_water_meter_val, que_lactalis_factory_municipal_water_meter);

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
        String comment = factoryMunicipalWaterComments.getText().toString().trim();

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
        que_lactalis_factory_municipal_water_tds = findViewById(R.id.que_lactalis_factory_municipal_water_tds);
        que_lactalis_factory_municipal_water_conductivity = findViewById(R.id.que_lactalis_factory_municipal_water_conductivity);
        que_lactalis_factory_municipal_water_ph = findViewById(R.id.que_lactalis_factory_municipal_water_ph);
        que_lactalis_factory_municipal_water_hardness = findViewById(R.id.que_lactalis_factory_municipal_water_hardness);
        que_lactalis_factory_municipal_water_chlorides = findViewById(R.id.que_lactalis_factory_municipal_water_chlorides);
        que_lactalis_factory_municipal_water_phosphonate = findViewById(R.id.que_lactalis_factory_municipal_water_phosphonate);
        que_lactalis_factory_municipal_water_alkalinity = findViewById(R.id.que_lactalis_factory_municipal_water_alkalinity);
        que_lactalis_factory_municipal_water_meter = findViewById(R.id.que_lactalis_factory_municipal_water_meter);
        factoryMunicipalWaterComments = findViewById(R.id.txa_lactalis_factory_municipal_water_comments);

        cb_daily = findViewById(R.id.cb_lactalis_factory_municipal_water_daily);
        cb_weekly = findViewById(R.id.cb_lactalis_factory_municipal_water_weekly);
        cb_monthly = findViewById(R.id.cb_lactalis_factory_municipal_water_monthly);

        cb_daily.setOnClickListener(checkboxCheck);
        cb_weekly.setOnClickListener(checkboxCheck);
        cb_monthly.setOnClickListener(checkboxCheck);

        que_lactalis_factory_municipal_water_tds_cont = findViewById(R.id.que_lactalis_factory_municipal_water_tds_cont);
        que_lactalis_factory_municipal_water_conductivity_cont = findViewById(R.id.que_lactalis_factory_municipal_water_conductivity_cont);
        que_lactalis_factory_municipal_water_ph_cont = findViewById(R.id.que_lactalis_factory_municipal_water_ph_cont);
        que_lactalis_factory_municipal_water_hardness_cont = findViewById(R.id.que_lactalis_factory_municipal_water_hardness_cont);
        que_lactalis_factory_municipal_water_chlorides_cont = findViewById(R.id.que_lactalis_factory_municipal_water_chlorides_cont);
        que_lactalis_factory_municipal_water_phosphonate_cont = findViewById(R.id.que_lactalis_factory_municipal_water_phosphonate_cont);
        que_lactalis_factory_municipal_water_alkalinity_cont = findViewById(R.id.que_lactalis_factory_municipal_water_alkalinity_cont);
        que_lactalis_factory_municipal_water_meter_cont = findViewById(R.id.que_lactalis_factory_municipal_water_meter_cont);
    }

    private final View.OnClickListener checkboxCheck = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            boolean checked = ((CheckBox) v).isChecked();
            System.out.println(checked + "-" + ((CheckBox) v).isChecked() + "-" + v.getId());
            switch (v.getId()) {
                case R.id.cb_lactalis_factory_municipal_water_daily:
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
                case R.id.cb_lactalis_factory_municipal_water_weekly:
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
                case R.id.cb_lactalis_factory_municipal_water_monthly:
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

    }
    public void weeklyRecordingBoilerShow() {
        que_lactalis_factory_municipal_water_tds_cont.setVisibility(View.VISIBLE);
        que_lactalis_factory_municipal_water_conductivity_cont.setVisibility(View.VISIBLE);
        que_lactalis_factory_municipal_water_ph_cont.setVisibility(View.VISIBLE);
        que_lactalis_factory_municipal_water_hardness_cont.setVisibility(View.VISIBLE);
        que_lactalis_factory_municipal_water_chlorides_cont.setVisibility(View.VISIBLE);
        que_lactalis_factory_municipal_water_phosphonate_cont.setVisibility(View.VISIBLE);
        que_lactalis_factory_municipal_water_alkalinity_cont.setVisibility(View.VISIBLE);
        que_lactalis_factory_municipal_water_meter_cont.setVisibility(View.VISIBLE);
    }
    public void monthlyRecordingBoilerShow() {

    }
    public void dailyRecordingBoilerHide() {

    }
    public void weeklyRecordingsBoilerHide() {
        que_lactalis_factory_municipal_water_tds_cont.setVisibility(View.GONE);
        que_lactalis_factory_municipal_water_conductivity_cont.setVisibility(View.GONE);
        que_lactalis_factory_municipal_water_ph_cont.setVisibility(View.GONE);
        que_lactalis_factory_municipal_water_hardness_cont.setVisibility(View.GONE);
        que_lactalis_factory_municipal_water_chlorides_cont.setVisibility(View.GONE);
        que_lactalis_factory_municipal_water_phosphonate_cont.setVisibility(View.GONE);
        que_lactalis_factory_municipal_water_alkalinity_cont.setVisibility(View.GONE);
        que_lactalis_factory_municipal_water_meter_cont.setVisibility(View.GONE);
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
        dateView = findViewById(R.id.lbl_date_selection);
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