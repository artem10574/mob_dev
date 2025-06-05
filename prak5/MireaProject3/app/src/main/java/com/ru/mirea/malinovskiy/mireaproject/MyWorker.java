package com.ru.mirea.malinovskiy.mireaproject;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWorker extends Worker {

    private static final String TAG = "MyWorker";

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        // Пример фоновой задачи
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(1000); // имитация длительной операции
                Log.d(TAG, "Выполняется фоновая задача... " + (i + 1));
            } catch (InterruptedException e) {
                return Result.failure();
            }
        }

        Log.d(TAG, "Фоновая задача завершена.");
        return Result.success();
    }
}