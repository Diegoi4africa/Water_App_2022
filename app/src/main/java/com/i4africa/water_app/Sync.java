package com.i4africa.water_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.sql.PooledConnection;

public class Sync extends AppCompatActivity {

    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat;
    private String Date;
    private Button btnSyncData , btncheckConnection;
    private TextView syncInfo, titleText , lastSync;
    private final String sqlCacheFileName = "offlineCache";
    private boolean syncInProgress = false;
    private ImageView sync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync);
        initComp();
    }

    private void initComp() {
        calendar = Calendar.getInstance();
        syncInfo = findViewById(R.id.sync_output);
        titleText = findViewById(R.id.lbl_page_title);
        titleText.setText("Sync Data");
        lastSync = findViewById(R.id.last_sync);
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date = simpleDateFormat.format(calendar.getTime());
        sync = findViewById(R.id.icon_sync);
        sync.setVisibility(View.GONE);
        btnSyncData = findViewById(R.id.btnSyncData);
        btnSyncData.setOnClickListener(syncData);
        btncheckConnection = findViewById(R.id.btncheckConnection);
        btncheckConnection.setOnClickListener(checkConnection);
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
            finish();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Could not delete file", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    //sync data on button click
    private final View.OnClickListener syncData = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            lastSync.setText("Last Sync : " + Date) ;
            if (syncInProgress == false) {
                syncInfo.append("\nPlease wait for sync to complete. ");
                Methods meth = new Methods();
                Context context = getApplicationContext();
                boolean connected = meth.isNetworkAvailable(context);

                if (connected) {
                    ArrayList<String> temp = new ArrayList<>();
                    FileInputStream fis = null;
                    try {
                        fis = context.openFileInput(sqlCacheFileName);
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
                    } catch (IOException e) {
                        // Error occurred when opening raw file for reading.
                    } finally {
                        String contents = stringBuilder.toString();
                    }

                    new SubmitToDB().execute(temp);
                } else {
                    syncInfo.setText("Please ensure that network is connected");
                }
            } else {
                syncInfo.append("\nCurrently Syncing Data, please wait.");
            }
        }
    };


    //check connection on button click
    private final View.OnClickListener checkConnection = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Methods meth = new Methods();
            Context context = getApplicationContext();
            boolean connected = meth.isNetworkAvailable(context);
            if (connected) {
                syncInfo.setText("Network is connected it is afe to sync");
            } else {
                syncInfo.setText("Network is not connected please don't sync data");
            }
        }
    };

    private static final String DEBUG_TAG = "NetworkStatusExample";
    private static final String URL = "jdbc:mysql://dedi75.cpt1.host-h.net/water_app_db";
    private static final String DBNAME = "water_app_db";
    private static final String USER = "wpmsaqgenz_3";
    private static final String PASSWORD = "pw6qpK9MHEMCa5uvV2c8";
    private Connection con;
    private Statement statement;
    private ResultSet resSet;

    class SubmitToDB extends AsyncTask<ArrayList<String>, String, Void> {
        String record = "", error = "";
        Statement state;

        @Override
        protected Void doInBackground(ArrayList<String>... arrayLists) {
            syncInProgress = true;
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                try {

                    for (int i = 0; i < arrayLists[0].size(); i++) {
                        con = DriverManager.getConnection(URL, USER, PASSWORD);
                        con.setAutoCommit(false);
                        state = con.createStatement();
                        state.executeUpdate(arrayLists[0].get(i));
                        con.commit();

                    }
                    con.close();


                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
                error = e.toString();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void string) {
            Context context = getApplicationContext();
            if (error != "") {
                syncInfo.append("\nCould not Submit data to DB, an error occured, please contact i4Africa\nOffice: +27 41 450 4307");
                syncInProgress = false;
            } else {
                syncInfo.append("\nSync Completed Successfully.");
                syncInProgress = false;
                try {
                    File.createTempFile(sqlCacheFileName, null, context.getCacheDir());
                    File cacheFile = new File(context.getCacheDir(), sqlCacheFileName);
                    FileOutputStream fos = context.openFileOutput(sqlCacheFileName, Context.MODE_PRIVATE);
                    String text = "";
                    fos.write(text.getBytes());
                } catch (Exception e) {

                }
            }
            super.onPostExecute(string);
        }
    }
}