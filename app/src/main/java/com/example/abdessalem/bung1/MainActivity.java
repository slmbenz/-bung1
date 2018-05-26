package com.example.abdessalem.bung1;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private static final String TAG ="MainActivity";
    private SensorManager sensorManger;
    Sensor accelerometer;
    TextView xValue, yValue, zValue;
    ProgressBar xProgressBar, yProgressBar, zProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        Log.d(TAG ,"nSensorChanged : X:"+sensorEvent.values[0]+ "Y: "+sensorEvent.values[1]+" Z: "+sensorEvent.values[2]);
        xValue.setText("xValue: "+sensorEvent.values[0]);
        yValue.setText("yValue: "+sensorEvent.values[1]);
        zValue.setText("zValue: "+sensorEvent.values[2]);

        float x = sensorEvent.values[0]*16.67f;
        float y = sensorEvent.values[1]*16.67f;
        float z = sensorEvent.values[2]*16.67f;
        int percentX = (int) x;
        int percentY = (int) y;
        int percentZ = (int) z;

        xProgressBar.setProgress(percentX);
        yProgressBar.setProgress(percentY);
        zProgressBar.setProgress(percentY);

    }

}
