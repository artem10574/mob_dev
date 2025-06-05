package com.ru.mirea.malinovskiy.workmanager;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.concurrent.TimeUnit;

public class UploadWorker extends Worker {

    static final String TAG = "UploadWorker";

    public UploadWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "doWork: начали");

        try {
            // Имитация длительной операции (например, загрузка файла)
            TimeUnit.SECONDS.sleep(10);

            Log.d(TAG, "doWork: успешно завершено");
            return Result.success();
        } catch (Exception e) {
            Log.e(TAG, "Ошибка выполнения", e);
            return Result.failure();
        }
    }
}
