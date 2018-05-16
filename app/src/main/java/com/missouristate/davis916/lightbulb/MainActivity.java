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
 * This app uses the shake detector and accelerometer to turn a lightbulb
 * off when the user shakes their device. This is accomplished by tying the
 * shake event to an intent, which passes the activity to the LightbulbSwitcher
 * class and changes the layout to lightbulb_off (the starting layout is
 * lightbulb_on).
 */

public class MainActivity extends Activity {

    //Declare variables
    private SensorManager mSensorManager;
    private Sensor mSensorAccelerometer;
    private ShakeDetector mShakeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lightbulb_on);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake() {
                Intent intent = new Intent(MainActivity.this, LightbulbSwitcher.class);
                startActivity(intent);
            }
        });
    }//end onCreate

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

}//end MainActivity
