package com.example.simpletest;

import android.app.Activity;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;


public class CompassActivity extends SimpleTestActivity implements SensorEventListener {
	
	public static float m_fRot = 0;
	Matrix m_Matrix;
	ImageView m_ImgView;
	
    private SensorManager mSensorManager;

    float[] inR = new float[16];
    float[] I = new float[16];
    float[] gravity = new float[3];
    float[] geomag = new float[3];
    float[] orientVals = new float[3];

    double azimuth = 0;
    double pitch = 0;
    double roll = 0;
    
    final int INIT_DEGREE = 361;
    float m_fLastDegree = INIT_DEGREE;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compass_page);

		Log.v(DEBUG_TAG, "~~~OnCreate");
		
		m_ImgView = (ImageView) findViewById(R.id.imgCompass);
		m_Matrix = new Matrix();
		m_ImgView.setScaleType(ScaleType.MATRIX);

	}
	
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		Log.v(DEBUG_TAG, "onAccuracyChanged: " + accuracy);
    }

    public void onSensorChanged(SensorEvent event) {
    
        // If the sensor data is unreliable return
        if (event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE)
            return;

        // Gets the value of the sensor that has been changed
        switch (event.sensor.getType()) {  
            case Sensor.TYPE_ACCELEROMETER:
                gravity = event.values.clone();
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                geomag = event.values.clone();
                break;
        }

        // If gravity and geomag have values then find rotation matrix
        if (gravity != null && geomag != null) {

            // checks that the rotation matrix is found
            boolean success = SensorManager.getRotationMatrix(inR, I, gravity, geomag);
            if (success) {
                SensorManager.getOrientation(inR, orientVals);
                azimuth = Math.toDegrees(orientVals[0]);
                pitch = Math.toDegrees(orientVals[1]);
                roll = Math.toDegrees(orientVals[2]);
                
                float flAz = (float)azimuth;
                
                Log.v(DEBUG_TAG, "azimuthNew: " + flAz);
                if(m_fLastDegree == INIT_DEGREE){
                	m_fLastDegree = flAz;
                }
                float deltaDegree = flAz - m_fLastDegree;
                Log.v(DEBUG_TAG, "New degree: " + deltaDegree);
                deltaDegree*=-1;
				m_Matrix.postRotate(deltaDegree, 
									m_ImgView.getDrawable().getBounds().width()/2,
									m_ImgView.getDrawable().getBounds().height()/2);
				m_fLastDegree = flAz;
                m_ImgView.setImageMatrix(m_Matrix);
               
            }
        }
    
    }

	
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		Log.v(DEBUG_TAG, "~~~onDestroy");
		super.onDestroy();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		Log.v(DEBUG_TAG, "~~~onPause");
		super.onPause();
		mSensorManager.unregisterListener(this);

	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onRestart()
	 */
	@Override
	protected void onRestart() {
		Log.v(DEBUG_TAG, "~~~onRestart");
		super.onRestart();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		Log.v(DEBUG_TAG, "~~~onResume");
		super.onResume();
		
		mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

		// Register this class as a listener for the accelerometer sensor
		mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
		                    SensorManager.SENSOR_DELAY_NORMAL);
		// ...and the orientation sensor
		mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
		                    SensorManager.SENSOR_DELAY_NORMAL);
		
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onStart()
	 */
	@Override
	protected void onStart() {
		Log.v(DEBUG_TAG, "~~~onStart");
		super.onStart();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop() {
		Log.v(DEBUG_TAG, "~~~onStop");
		super.onStop();
	}


}

