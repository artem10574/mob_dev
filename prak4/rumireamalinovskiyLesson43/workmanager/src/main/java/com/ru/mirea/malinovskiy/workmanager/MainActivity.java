package com.ru.mirea.malinovskiy.workmanager;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Условия для выполнения задачи
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED) // Требуется Wi-Fi (не мобильный интернет)
                .setRequiresCharging(true) // Требуется, чтобы устройство было на зарядке
                .build();

        // Создание однократной задачи
        OneTimeWorkRequest uploadWorkRequest = new OneTimeWorkRequest.Builder(UploadWorker.class)
                .setConstraints(constraints)
                .build();

        // Запуск задачи
        WorkManager.getInstance(this).enqueue(uploadWorkRequest);
    }
}