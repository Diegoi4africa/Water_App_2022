package com.i4africa.water_app;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Methods {
    private final String FILENAMELOGIN = "usrLoginSession";
    private Context context;
    private static final String DEBUG_TAG = "NetworkStatusExample";
    private static final String URL = "jdbc:mysql://dedi75.cpt1.host-h.net/water_app_db";
    private static final String DBNAME = "water_app_db";
    private static final String USER = "wpmsaqgenz_3";
    private static final String PASSWORD = "pw6qpK9MHEMCa5uvV2c8";
    private Connection con;
    private Statement statement;
    private ResultSet resSet;
    private final ArrayList<String> compQuestions = new ArrayList<>();
    private final String FILENAME = "Testing";
    private String LocationString;

    private String cacheFileName;

    public String getHashText(String text) {
        try {
            String plaintext = text;
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(plaintext.getBytes());
            byte[] digest = m.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            String hashtext = bigInt.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException ex) {
            return "error";
        }
    }

    public String[] getStringArr() {
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
        return calcCachFiles;
    }

    public void logoutUser(Context context) {
        try {
            String[] files = context.fileList();
            for (int i = 0; i < files.length; i++) {
                context.deleteFile(files[i]);
            }

            Toast.makeText(context, "You have been logged out", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(context, "Could not delete file", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    public void writeLoginStorage(int userID, String username, String password, String name, String surname, Context context) {
        String fileContents = userID + "#" + username + "#" + password + "#" + name + "#" + surname;
        try (FileOutputStream fos = context.openFileOutput(FILENAMELOGIN, Context.MODE_PRIVATE)) {
            fos.write(fileContents.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    /**
     * @param context      the current app context
     * @param locationName name of the site according to DB
     * @param fileName     pass the file name of the cache file to be read or created
     **/
    public void getQuestions(String locationName, Context context, String fileName) {
        this.LocationString = locationName;
        this.context = context;
        this.cacheFileName = fileName;
        File file = new File(context.getCacheDir(), fileName);
        if (!file.exists()) {
            new Async().execute();
        }
    }

    /**
     * @param context the current app context
     * @param sql     pass the sql commands in the form of an ArrayList
     **/
    public void createSQLCache(Context context, ArrayList<String> sql) {
        String sqlCacheFileName = "offlineCache";
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
        for (int i = 0; i < sql.size(); i++) {
            String val = sql.get(i);
            System.out.println(val);
            temp.add(val);
        }
        try {
            File.createTempFile(sqlCacheFileName, null, context.getCacheDir());
            File cacheFile = new File(context.getCacheDir(), sqlCacheFileName);
            FileOutputStream fos = context.openFileOutput(sqlCacheFileName, Context.MODE_PRIVATE);
            for (int i = 0; i < temp.size(); i++) {
                String text = temp.get(i) + "\n";
                fos.write(text.getBytes());
            }
            Toast.makeText(context, "Successfully Saved Submitted Data, Please be sure to Sync when you have network connection", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "ERR creating Cache File, please look at stacktrace", Toast.LENGTH_LONG).show();
        }
    }

    public ArrayList<String> getCachedQuestions(Context context, String fileName) {
        ArrayList<String> questions = new ArrayList<>();

        FileInputStream fis = null;
        try {
            fis = context.openFileInput(fileName);
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
                questions.add(line);
                line = reader.readLine();

            }
            Toast.makeText(context, "Successfully Read all questions", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            // Error occurred when opening raw file for reading.
        } finally {
            String contents = stringBuilder.toString();
        }

        return questions;
    }

    public void writeQuestionStorage(int QuestionID, String Question, String QuestionVarName, double ExpectedMin, double ExpectedMax, String ComponentName, String LocationName, Context context, String fileName) {
        String fileContents = QuestionID + "#" + Question + "#" + QuestionVarName + "#" + ExpectedMin + "#" + ExpectedMax + "#" + ComponentName + "#" + LocationName + "/n";
        try (FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE)) {
            fos.write(fileContents.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    class Async extends AsyncTask<Void, Void, Void> {
        String record = "", error = "";

        @Override
        protected Void doInBackground(Void... voids) {
            int companyID = 0;
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                con = DriverManager.getConnection(URL, USER, PASSWORD);
                statement = con.createStatement();
                resSet = statement.executeQuery("SELECT Question.QuestionID, Question.Question, Question.QuestionVarName, Question.ExpectedMin, Question.ExpectedMax, Component.ComponentName, Location.LocationName FROM (((Question INNER JOIN Component ON Question.ComponentID = Component.ComponentID) INNER JOIN Location ON Component.LocationID = Location.LocationID) INNER JOIN Site ON Location.SiteID = Site.SiteID) WHERE Location.LocationName = '" + LocationString + "'");
                while (resSet.next()) {
                    compQuestions.add(resSet.getInt("QuestionID") + "#" + resSet.getString("Question") + "#" + resSet.getString("QuestionVarName") + "#" + resSet.getDouble("ExpectedMin") + "#" + resSet.getDouble("ExpectedMax") + "#" + resSet.getString("ComponentName") + "#" + resSet.getString("LocationName") + "\n");
                }
                statement.close();
                con.close();
                resSet.close();
            } catch (Exception e) {
                e.printStackTrace();
                error = e.toString();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                File.createTempFile(cacheFileName, null, context.getCacheDir());
                File cacheFile = new File(context.getCacheDir(), cacheFileName);
                FileOutputStream fos = context.openFileOutput(cacheFileName, Context.MODE_PRIVATE);
                for (int i = 0; i < compQuestions.size(); i++) {
                    String text = compQuestions.get(i);
                    fos.write(compQuestions.get(i).getBytes());
                }
                Toast.makeText(context, "Successfully created CacheFile", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(context, "ERR creating Cache File, please look at stacktrace", Toast.LENGTH_LONG).show();
            }

            super.onPostExecute(aVoid);
        }
    }

    public void storeData(ArrayList<String> arg, Context context) {
        boolean connected = isNetworkAvailable(context);
        this.context = context;
        /*if (connected) {
            String sqlCacheFileName = "offlineCache";
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

            for (int i = 0; i < temp.size(); i++) {
                String tempSQL = temp.get(i);
                arg.add(tempSQL);
            }

            new SubmitToDB().execute(arg);

            try {
                File.createTempFile(sqlCacheFileName, null, context.getCacheDir());
                File cacheFile = new File(context.getCacheDir(), sqlCacheFileName);
                FileOutputStream fos = context.openFileOutput(sqlCacheFileName, Context.MODE_PRIVATE);
                String text = "";
                fos.write(text.getBytes());
            } catch (Exception e) {

            }

        } else {*/
            createSQLCache(context, arg);
        //}
    }


    class SubmitToDB extends AsyncTask<ArrayList<String>, String, Void> {
        String record = "", error = "";
        Statement state;

        @Override
        protected Void doInBackground(ArrayList<String>... arrayLists) {

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
            if (error != "") {
                Toast.makeText(context, "Could not submit Data to Database, please ensure you have an Active network Connection", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Data Submitted Successfully", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(string);
        }
    }


}
