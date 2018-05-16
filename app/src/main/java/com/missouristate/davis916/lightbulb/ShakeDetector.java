package com.missouristate.davis916.lightbulb;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by: James Bateman @URL
 * https://github.com/jrbateman/ShakeExperiment
 *
 * Laura Davis CIS 262-902
 * 29 April 2018
 *
 * This class handles the shake sensor event and sets some preliminary
 * values for the shake listener and accelerometer, which determines what
 * qualifies as a shake event in this app.
 *
 */

public class ShakeDetector implements SensorEventListener{
    private long mTimeOfLastShake;

    private static final float SHAKE_THRESHOLD = 25f;
    private static final int SHAKE_TIME_LAPSE = 200;   //IN MILLISECONDS 500

    // OnShakeListener THAT WILL BE NOTIFIED WHEN A SHAKE IS DETECTED
    private OnShakeListener mShakeListener;

    // CONSTRUCTOR SETS THE SHAKE LISTENER
    public ShakeDetector(OnShakeListener shakeListener) {
        mShakeListener = shakeListener;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            //TASK 1: COLLECT SENSOR VALUES ON ALL THREE AXIS
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            //TASK 2: CONVERT EACH ACCELEROMETER MEASUREMENT INTO
            //  A G-FORCE MEASUREMENT BY NEUTRALIZING GRAVITY.
            float gForceX = x - SensorManager.GRAVITY_EARTH;
            float gForceY = y - SensorManager.GRAVITY_EARTH;
            float gForceZ = z - SensorManager.GRAVITY_EARTH;

            //TASK 3: COMPUTE  G-FORCE AS A DIRECTIONLESS MEASUREMENT
            // NOTE: G-FORCE WILL BE APPROXIMATELY 1 WHEN
            //       THERE IS NO SHAKING MOVEMENT.
            double vector = Math.pow(gForceX, 2.0) + Math.pow(gForceY, 2.0) + Math.pow(gForceZ, 2.0);
            float gForce = (float) Math.sqrt(vector);

            //TASK 4: DETERMINE IF THE G-FORCE IS ENOUGH TO REGISTER AS A SHAKE

            if (gForce > SHAKE_THRESHOLD) {
                //IGNORE CONTINUOUS SHAKES - CHECK THAT 500 MILLISECONDS HAVE LAPSED
                final long now = System.currentTimeMillis();
                if (mTimeOfLastShake + SHAKE_TIME_LAPSE > now) {
                    return;
                }
                mTimeOfLastShake = now;

                //THE LISTENER REGISTERED A SHAKE
                mShakeListener.onShake();
            }
        }

    }//end onSensorChanged()

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    public interface OnShakeListener {
        public void onShake();
    }

}//end ShakeDetector class
