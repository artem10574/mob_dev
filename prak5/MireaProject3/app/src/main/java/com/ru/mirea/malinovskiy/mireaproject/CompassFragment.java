package com.ru.mirea.malinovskiy.mireaproject;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CompassFragment extends Fragment implements SensorEventListener {

    // Константы для направлений
    private static final String[] CARDINAL_DIRECTIONS = {
            "Север", "Северо-восток", "Восток", "Юго-восток",
            "Юг", "Юго-запад", "Запад", "Северо-запад"
    };
    private static final float[] DIRECTION_ANGLES = {
            0f, 45f, 90f, 135f, 180f, 225f, 270f, 315f
    };

    // Сенсоры и менеджер
    private SensorManager sensorSystemService;
    private Sensor accelerationSensor;
    private Sensor magneticFieldSensor;

    // Данные сенсоров
    private final float[] lastAccelerationData = new float[3];
    private final float[] lastMagneticData = new float[3];
    private boolean isAccelerationDataReady = false;
    private boolean isMagneticDataReady = false;

    // Матрицы для расчетов
    private final float[] deviceRotationMatrix = new float[9];
    private final float[] orientationAngles = new float[3];

    // UI элементы
    private TextView compassDirectionDisplay;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View compassView = inflater.inflate(R.layout.fragment_compass, container, false);
        compassDirectionDisplay = compassView.findViewById(R.id.direction_text);
        return compassView;
    }

    @Override
    public void onResume() {
        super.onResume();
        initializeSensors();
    }

    private void initializeSensors() {
        sensorSystemService = (SensorManager) requireContext().getSystemService(Context.SENSOR_SERVICE);
        if (sensorSystemService != null) {
            accelerationSensor = sensorSystemService.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            magneticFieldSensor = sensorSystemService.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

            if (accelerationSensor != null && magneticFieldSensor != null) {
                sensorSystemService.registerListener(this, accelerationSensor,
                        SensorManager.SENSOR_DELAY_UI);
                sensorSystemService.registerListener(this, magneticFieldSensor,
                        SensorManager.SENSOR_DELAY_UI);
            } else {
                compassDirectionDisplay.setText("Датчики не доступны");
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (sensorSystemService != null) {
            sensorSystemService.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        switch (sensorEvent.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                System.arraycopy(sensorEvent.values, 0, lastAccelerationData, 0, sensorEvent.values.length);
                isAccelerationDataReady = true;
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                System.arraycopy(sensorEvent.values, 0, lastMagneticData, 0, sensorEvent.values.length);
                isMagneticDataReady = true;
                break;
        }

        if (isAccelerationDataReady && isMagneticDataReady) {
            updateCompassDirection();
        }
    }

    private void updateCompassDirection() {
        if (SensorManager.getRotationMatrix(deviceRotationMatrix, null,
                lastAccelerationData, lastMagneticData)) {
            SensorManager.getOrientation(deviceRotationMatrix, orientationAngles);

            float azimuthRadians = orientationAngles[0];
            float azimuthDegrees = (float) Math.toDegrees(azimuthRadians);
            azimuthDegrees = (azimuthDegrees + 360) % 360; // Нормализация угла

            String direction = determineCardinalDirection(azimuthDegrees);
            compassDirectionDisplay.setText(String.format(
                    "Направление: %s\n(%.1f°)", direction, azimuthDegrees));
        }
    }

    private String determineCardinalDirection(float degrees) {
        int index = 0;
        float minDifference = Float.MAX_VALUE;

        for (int i = 0; i < DIRECTION_ANGLES.length; i++) {
            float difference = Math.abs(degrees - DIRECTION_ANGLES[i]);
            if (difference < minDifference) {
                minDifference = difference;
                index = i;
            }
        }
        return CARDINAL_DIRECTIONS[index];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}