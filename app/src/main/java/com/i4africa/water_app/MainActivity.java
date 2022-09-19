package com.i4africa.water_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import android.accessibilityservice.GestureDescription;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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

public class MainActivity extends AppCompatActivity {


    private EditText username, password;
    private final String FILENAME = "offlineCache";
    private static final String URL = "jdbc:mysql://dedi75.cpt1.host-h.net/water_app_db";
    private static final String DBNAME = "water_app_db";
    private static final String USER = "wpmsaqgenz_3";
    private static final String PASSWORD = "pw6qpK9MHEMCa5uvV2c8";
    private String usernameInput;
    private boolean activeUser;
    private Statement statement;
    private Connection con;
    private ResultSet resSet;
    private String[] recordArray;
    private String md5Password, passwordDBres, name, surname;
    private String sUsername;
    private String sPassword;
    private Methods meth;
    private int userID;
    private final String FILENAMELOGIN = "usrLoginSession";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        meth = new Methods();
        username = findViewById(R.id.txfUsername);
        password = findViewById(R.id.txfPassword);
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
                Intent siteSelection = new Intent(getApplicationContext(), SiteSelection.class);
                siteSelection.putExtra("userName", split[1]);
                siteSelection.putExtra("name", split[3]);
                siteSelection.putExtra("surname", split[4]);
                startActivity(siteSelection);
                finish();
            }
        }
    }

    public void login(View view) {
        Methods meth = new Methods();
        boolean connected = meth.isNetworkAvailable(getApplicationContext());
        if (connected) {
            if (!username.getText().toString().trim().isEmpty() && !password.getText().toString().trim().isEmpty()) {
                sUsername = username.getText().toString();
                usernameInput = sUsername;
                sPassword = password.getText().toString();
                md5Password = meth.getHashText(sPassword);
                new Login().execute();
            } else {
                if (!username.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter a Username", Toast.LENGTH_LONG).show();
                }
                if (!password.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter a Password", Toast.LENGTH_LONG).show();
                }
            }
        } else {
            Toast.makeText(getApplicationContext(), "Please check network connection", Toast.LENGTH_SHORT).show();
        }
    }

    class Login extends AsyncTask<Void, Void, Void> {
        String record = "", error = "";

        @Override
        protected Void doInBackground(Void... voids) {
            boolean connected = meth.isNetworkAvailable(getApplicationContext());
            if (connected) {
                try {
                    Class.forName("com.mysql.jdbc.Driver").newInstance();
                    con = DriverManager.getConnection(URL, USER, PASSWORD);
                    statement = con.createStatement();
                    resSet = statement.executeQuery("SELECT * FROM User WHERE Username = '" + usernameInput + "';");
                    while (resSet.next()) {
                        recordArray = null;
                        record += resSet.getInt(1) + "#" + resSet.getString(2) + "#" + resSet.getString(3) + "#" + resSet.getString(4) + "#" + resSet.getString(5) + "#" + resSet.getBoolean(6);
                        recordArray = record.split("#");
                        userID = Integer.parseInt(recordArray[0]);
                        name = recordArray[3];
                        surname = recordArray[4];
                        passwordDBres = recordArray[2];
                        System.out.println(passwordDBres);
                        activeUser = Boolean.parseBoolean(recordArray[5]);
                    }
                    statement.close();
                    con.close();
                    resSet.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    error = e.toString();
                }
            } else {
                error = "Device not connected to network";
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (error != "") {
                if (error == "Device not connected to network") {
                    Toast.makeText(getApplicationContext(), "Please check connection.", Toast.LENGTH_LONG).show();
                }
            } else {
                if (passwordDBres.equalsIgnoreCase(md5Password)) {
                    if (activeUser) {
                        meth.writeLoginStorage(userID, sUsername, md5Password, name, surname, getApplicationContext());
                        Intent siteSelection = new Intent(getApplicationContext(), SiteSelection.class);
                        siteSelection.putExtra("userName", sUsername);
                        siteSelection.putExtra("name", name);
                        siteSelection.putExtra("surname", surname);
                        startActivity(siteSelection);
                        createCache();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Please activate your account before logging in", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Incorrect Password", Toast.LENGTH_LONG).show();
                }
                System.out.println(record);
            }
            super.onPostExecute(aVoid);
        }
    }

    public void createCache() {
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

}