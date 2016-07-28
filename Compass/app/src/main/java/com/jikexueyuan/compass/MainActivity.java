package com.jikexueyuan.compass;

import android.animation.ObjectAnimator;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private ImageView mIv_compass;
    float[] accelerometerValues = new float[3];
    float[] magneticFieldValues = new float[3];
    private Sensor aSensor;
    private Sensor mSensor;
    private TextView mTv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mIv_compass = (ImageView) findViewById(R.id.iv_compass);
        mTv_title = (TextView) findViewById(R.id.tv_title);

    }

    @Override
    protected void onResume() {
        super.onResume();
        aSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        if (aSensor == null) {
            Toast.makeText(this, "无法取得加速传感器数据，请检查手机是否支持", Toast.LENGTH_SHORT).show();
            finish();
        }

        if (mSensor == null) {
            Toast.makeText(this, "无法取得磁场传感器数据，请检查手机是否支持", Toast.LENGTH_SHORT).show();
            finish();
        }

        mSensorManager.registerListener(this, aSensor, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        //更新显示数据
        calculateOrientation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this, mSensor);
        mSensorManager.unregisterListener(this, aSensor);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            magneticFieldValues = event.values;
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            accelerometerValues = event.values;
        //更新显示数据
        calculateOrientation();

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void calculateOrientation() {
        float[] values = new float[3];
        float[] R = new float[9];
        SensorManager.getRotationMatrix(R, null, accelerometerValues, magneticFieldValues);
        SensorManager.getOrientation(R, values);

        // 将数据转换为度
        values[0] = (float) Math.toDegrees(values[0]);
        ObjectAnimator.ofFloat(mIv_compass, "rotation", -values[0]).setDuration(0).start();

        if (values[0] >= -5 && values[0] < 5) {
            mTv_title.setText(com.jikexueyuan.compass.R.string.north);
        } else if (values[0] >= 5 && values[0] < 85) {
            mTv_title.setText(com.jikexueyuan.compass.R.string.northeast);
        } else if (values[0] >= 85 && values[0] <= 95) {
            mTv_title.setText(com.jikexueyuan.compass.R.string.east);
        } else if (values[0] >= 95 && values[0] < 175) {
            mTv_title.setText(com.jikexueyuan.compass.R.string.southeast);
        } else if ((values[0] >= 175 && values[0] <= 180) || (values[0]) >= -180 && values[0] < -175) {
            mTv_title.setText(com.jikexueyuan.compass.R.string.south);
        } else if (values[0] >= -175 && values[0] < -95) {
            mTv_title.setText(com.jikexueyuan.compass.R.string.southwest);
        } else if (values[0] >= -95 && values[0] < -85) {
            mTv_title.setText(com.jikexueyuan.compass.R.string.west);
        } else if (values[0] >= -85 && values[0] < -5) {
            mTv_title.setText(com.jikexueyuan.compass.R.string.northwest);
        }
    }
}
