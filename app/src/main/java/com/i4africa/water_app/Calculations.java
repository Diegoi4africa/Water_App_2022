package com.i4africa.water_app;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Calculations {

    /**
     * @param val      the value that needs to be saved
     * @param filename the name of the file that the value needs to be saved to
     * @param context  pass the application context to give location for file to be saved
     **/
    public void saveForConsumptionReading(double val, String filename, Context context) {
        try {
            File.createTempFile(filename, null, context.getCacheDir());
            File cacheFile = new File(context.getCacheDir(), filename);
            FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
            String text = val + "";
            fos.write(text.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "ERR creating Cache File, please contact administrator. Error code E-C1", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * @param filename pass the filename of the historical value
     * @param context  pass the application context to access the temp files
     * @return the double value
     */

    public double readHistoricalValue(String filename, Context context) {
        double value = 0.0;
        FileInputStream fis = null;
        try {
            fis = context.openFileInput(filename);
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
                line = reader.readLine();
            }
        } catch (IOException e) {
            // Error occurred when opening raw file for reading.
        } finally {
            String contents = stringBuilder.toString();
            value = Double.parseDouble(contents);
        }

        return value;
    }

    /**
     * @param context                  pass application context
     * @param boiler1Meter             File name for boilerMeter1 historical value
     * @param boiler2Meter             File name for boilerMeter2 historical value
     * @param boilerSoftenerOutlet     File name for Boiler Softener Outlet historical value
     * @param currBoiler1              value for current boiler 1 meter reading
     * @param currBoiler2              value for current boiler 2 meter reading
     * @param currBoilerSoftenerOutlet value for current boiler softener outlet reading
     * @return Returns % number off the estimated condensate return
     */

    public double estCondensateReturn(Context context, String boiler1Meter, String boiler2Meter, String boilerSoftenerOutlet, double currBoiler1, double currBoiler2, double currBoilerSoftenerOutlet) {
        double returnPercentile = 0.0;

        double oldBoiler1 = 1;
        double oldBoiler2 = 1;
        double oldBoilerSoftenerOutlet = 1;
        double boiler1Consumption = 1;
        double boiler2Consumption = 1;
        double boilerSoftenerConsumption = 1;

        try {
            oldBoiler1 = readHistoricalValue(boiler1Meter, context);
            oldBoiler2 = readHistoricalValue(boiler2Meter, context);
            oldBoilerSoftenerOutlet = readHistoricalValue(boilerSoftenerOutlet, context);
            boiler1Consumption = currBoiler1 - oldBoiler1;
            boiler2Consumption = currBoiler2 - oldBoiler2;
            boilerSoftenerConsumption = currBoilerSoftenerOutlet - oldBoilerSoftenerOutlet;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnPercentile = ((((boiler1Consumption + boiler2Consumption) - boilerSoftenerConsumption) / (boiler1Consumption + boiler2Consumption)) * 100);
        }

        return returnPercentile;
    }


    /**
     * @param compTDS
     * @param context
     * @return
     */
    public double cyclesOfConcentrationTDS(double compTDS, Context context) {
        double cyclesOFConsentrationTDS = 0.0;
        double municipalTDS = readHistoricalValue("MunicipalTDSCurr", context);
        cyclesOFConsentrationTDS = compTDS - municipalTDS;
        return cyclesOFConsentrationTDS;
    }

    /**
     * @param compOP
     * @param context
     * @return
     */
    public double compLessOrganoPhosphonate(double compOP, Context context) {
        double compLessMunicipal = 0.0;
        double municipalOrganoPhosphonate = readHistoricalValue("MunicipalOrganoPhosphonate", context);
        compLessMunicipal = compOP - municipalOrganoPhosphonate;
        return compLessMunicipal;
    }

    /**
     * @param currValue
     * @param fileName
     * @param context
     * @return
     */
    public double consumptionCalc(double currValue, String fileName, Context context) {
        double consumption = 0.0;
        double histVal = readHistoricalValue(fileName, context);
        consumption = currValue - histVal;
        return consumption;
    }

}

