package com.i4africa.water_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class Scanner extends AppCompatActivity {

    SurfaceView surfaceView;
    TextView txtBarcodeValue;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    Button btnAction;
    private String intentData = "";
    boolean isEmail = false;
    int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        initViews();
    }

    private void initViews() {
        txtBarcodeValue = findViewById(R.id.txtBarcodeValue);
        surfaceView = findViewById(R.id.surfaceView);
        btnAction = findViewById(R.id.btnAction);


        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (intentData.length() > 0) {

                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(intentData)));

                }


            }
        });
    }

    private void initialiseDetectorsAndSources() {

        Toast.makeText(getApplicationContext(), "Barcode scanner started", Toast.LENGTH_SHORT).show();
        count = 0;

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(Scanner.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(Scanner.this, new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

                Toast.makeText(getApplicationContext(), "To prevent memory leaks barcode scanner has been stopped", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {


                    txtBarcodeValue.post(new Runnable() {

                        @Override
                        public void run() {

                            if (barcodes.valueAt(0).email != null) {
                                txtBarcodeValue.removeCallbacks(null);
                                intentData = barcodes.valueAt(0).email.address;
                                txtBarcodeValue.setText(intentData);
                                isEmail = true;
                                btnAction.setText("ADD CONTENT TO THE MAIL");
                            } else {
                                isEmail = false;
                                intentData = barcodes.valueAt(0).displayValue;
                                txtBarcodeValue.setText(intentData);
                                Log.println(Log.ASSERT, "ScannerClassAssertLog", "Count value: " + count);

                                if (count == 0) {
                                    switch (intentData) {
                                        case "http://water-app.wpmsa.co.za/lactalis/factory/boiler":
                                            Log.println(Log.ASSERT, "ScannerClassAssertLog", "Lactalis Factory Boiler Form");
                                            count++;
                                            startActivity(new Intent(getApplicationContext(), LactalisBoilerQuestions.class));
                                            break;
                                        case "http://water-app.wpmsa.co.za/lactalis/factory/municipal-water":
                                            Log.println(Log.ASSERT, "ScannerClassAssertLog", "Lactalis Factory Municipal Water Form");
                                            count++;
                                            startActivity(new Intent(getApplicationContext(), lactalisMunicipalWaterQuestions.class));
                                            break;
                                        case "http://water-app.wpmsa.co.za/lactalis/factory/uht-condenser":
                                            Log.println(Log.ASSERT, "ScannerClassAssertLog", "Lactalis Factory UHT Condenser Form");
                                            count++;
                                            startActivity(new Intent(getApplicationContext(), LactalisCoolingSystemQuestions.class));
                                            break;
                                        case "http://water-app.wpmsa.co.za/lactalis/factory/uht-ice-dam":
                                            Log.println(Log.ASSERT, "ScannerClassAssertLog", "Lactalis Factory UHT Ice Dam Form");
                                            count++;
                                            startActivity(new Intent(getApplicationContext(), LactalisUHTIceDam.class));
                                            break;
                                        case "http://water-app.wpmsa.co.za/lactalis/factory/steri-condenser":
                                            Log.println(Log.ASSERT, "ScannerClassAssertLog", "Lactalis Factory Steri Condenser Form");
                                            count++;
                                            startActivity(new Intent(getApplicationContext(), LactalisSteriCondensorQuestions.class));
                                            break;
                                        case "http://water-app.wpmsa.co.za/lactalis/factory/steri-ice-dam":
                                            Log.println(Log.ASSERT, "ScannerClassAssertLog", "Lactalis Factory Steri Ice Dam Form");
                                            count++;
                                            startActivity(new Intent(getApplicationContext(), LactalisSteriIceDam.class));
                                            break;
                                        case "http://water-app.wpmsa.co.za/lactalis/factory/sterilization-tower":
                                            Log.println(Log.ASSERT, "ScannerClassAssertLog", "Lactalis Factory Sterilization Tower Form");
                                            count++;
                                            startActivity(new Intent(getApplicationContext(), LactalisSteriSterilizationTower.class));
                                            break;
                                        case "http://water-app.wpmsa.co.za/lactalis/warehouse/municipal-water":
                                            Log.println(Log.ASSERT, "ScannerClassAssertLog", "Lactalis Warehouse Municipal Water Form");
                                            count++;
                                            startActivity(new Intent(getApplicationContext(), LactalisWarehouseMunicpalWaterQuestions.class));
                                            break;
                                        case "http://water-app.wpmsa.co.za/lactalis/warehouse/cooling-system":
                                            Log.println(Log.ASSERT, "ScannerClassAssertLog", "Lactalis Warehouse Cooling System Form");
                                            count++;
                                            startActivity(new Intent(getApplicationContext(), LactalisWarehouseCoolingSystemQuestions.class));
                                            break;
                                        default:
                                            btnAction.setText("Launch URL");
                                    }
                                }

                            }
                        }
                    });

                }
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        cameraSource.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initialiseDetectorsAndSources();


    }
}