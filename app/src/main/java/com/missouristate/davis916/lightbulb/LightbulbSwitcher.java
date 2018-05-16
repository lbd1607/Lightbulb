package com.missouristate.davis916.lightbulb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;

/**
 * Laura Davis CIS 262-902
 * 1 May 2018
 *
 * This class is tied to an intent creation in the MainActivity class.
 * The starting layout for this app is lightbulb_on. When the shake
 * event occurs, the activity is passed via intent to this class, which
 * changes the layout to the lightbulb_off layout.
 */

public class LightbulbSwitcher extends Activity{

    //Declare variables
    private SensorManager mSensorManager;
    private Sensor mSensorAccelerometer;
    private ShakeDetector mShakeDetector;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lightbulb_off);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake() {
                Intent intent = new Intent(LightbulbSwitcher.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }//end onCreate()

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mShakeDetector, mSensorAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }//end onResume()

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(mShakeDetector, mSensorAccelerometer);
    }//end onPause()

    @Override
    protected void onStop(){
        super.onStop();
        finish();
    }

}//end LightbulbSwitcher class
