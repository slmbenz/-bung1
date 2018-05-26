package com.example.abdessalem.bung1;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private static final String TAG = "MainActivity";
    private SensorManager sensorManger;
    Sensor accelerometer;
    TextView xValue, yValue, zValue;
    ProgressBar xProgressBar, yProgressBar, zProgressBar;

    private static final String DIR_NAME = "EMA_Aufgabe2";
    private static final String CSV_NAME = "output.csv";
    private String dirPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        try {
            dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separatorChar + DIR_NAME;
            File dir = new File(dirPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File csv = new File(dir, CSV_NAME);
            String firstEntry = "TIME,X,Y,Z,\n";
            FileOutputStream fos = new FileOutputStream(csv);
            fos.write(firstEntry.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        xValue = (TextView) findViewById(R.id.xValue);
        yValue = (TextView) findViewById(R.id.yValue);
        zValue = (TextView) findViewById(R.id.zValue);

        xProgressBar = (ProgressBar) findViewById(R.id.xProgressBar);
        yProgressBar = (ProgressBar) findViewById(R.id.yProgressBar);
        zProgressBar = (ProgressBar) findViewById(R.id.zProgressBar);

        Log.d(TAG, "onCreate: Initializing Sensor Activity");
        sensorManger = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManger.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManger.registerListener(MainActivity.this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        Log.d(TAG, "onCreate: Registered accelerometer Listener");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Log.d(TAG, "nSensorChanged : X:" + sensorEvent.values[0] + "Y: " + sensorEvent.values[1] + " Z: " + sensorEvent.values[2]);
        xValue.setText("xValue: " + sensorEvent.values[0]);
        yValue.setText("yValue: " + sensorEvent.values[1]);
        zValue.setText("zValue: " + sensorEvent.values[2]);

        float x = sensorEvent.values[0] * 16.67f;
        float y = sensorEvent.values[1] * 16.67f;
        float z = sensorEvent.values[2] * 16.67f;
        int percentX = (int) x;
        int percentY = (int) y;
        int percentZ = (int) z;

        xProgressBar.setProgress(percentX);
        yProgressBar.setProgress(percentY);
        zProgressBar.setProgress(percentZ);

        // write values in csv
        try {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("[HH:mm:ss]");
            String strTime = sdf.format(calendar.getTime());
            String entry = strTime + "," + sensorEvent.values[0] + "," + sensorEvent.values[1] + "," + sensorEvent.values[2] + ",\n";
            File sdCard = Environment.getExternalStorageDirectory();
            File dir = new File(dirPath);
            File file = new File(dir, CSV_NAME);
            FileOutputStream fos = new FileOutputStream(file, true);
            fos.write(entry.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
