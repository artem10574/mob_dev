package com.ru.mirea.malinovskiy.lesson5;

import androidx.appcompat.app.AppCompatActivity;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Получаем менеджер датчиков
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        listView = findViewById(R.id.list_view);

        // Получаем список всех датчиков
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

        // Подготавливаем данные для отображения
        String[] sensorNames = new String[sensors.size()];
        for (int i = 0; i < sensors.size(); i++) {
            sensorNames[i] = sensors.get(i).getName();
        }

        // Выводим список в ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, sensorNames);
        listView.setAdapter(adapter);
    }
}