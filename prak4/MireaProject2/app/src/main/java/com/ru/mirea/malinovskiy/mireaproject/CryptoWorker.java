package com.ru.mirea.malinovskiy.mireaproject;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class CryptoWorker extends Worker {

    public static final String KEY_OUTPUT = "decrypted_text";
    private static final String TAG = "CryptoWorker";

    public CryptoWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        byte[] encryptedText = getInputData().getByteArray("text");
        byte[] keyBytes = getInputData().getByteArray("key");

        if (encryptedText == null || keyBytes == null) {
            return Result.failure();
        }

        try {
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] result = cipher.doFinal(encryptedText);

            String decrypted = new String(result);
            Log.d(TAG, "Расшифрованный текст: " + decrypted);

            Data output = new Data.Builder()
                    .putString(KEY_OUTPUT, decrypted)
                    .build();

            return Result.success(output);

        } catch (Exception e) {
            Log.e(TAG, "Ошибка расшифровки", e);
            return Result.failure();
        }
    }
}