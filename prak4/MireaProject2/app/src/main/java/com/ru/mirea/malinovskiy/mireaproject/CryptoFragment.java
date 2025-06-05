package com.ru.mirea.malinovskiy.mireaproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class CryptoFragment extends Fragment {

    private EditText editTextInput;
    private TextView tvResult;
    private Button btnEncrypt;

    public static final String KEY_TEXT = "text";
    public static final String KEY_KEY = "key";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crypto, container, false);

        editTextInput = view.findViewById(R.id.editTextInput);
        tvResult = view.findViewById(R.id.tvResult);
        btnEncrypt = view.findViewById(R.id.btnEncrypt);

        btnEncrypt.setOnClickListener(v -> {
            String input = editTextInput.getText().toString();
            if (!input.isEmpty()) {
                byte[] key = generateKey();
                byte[] encrypted = encryptMsg(input, key);

                Data inputData = new Data.Builder()
                        .putByteArray(KEY_TEXT, encrypted)
                        .putByteArray(KEY_KEY, key)
                        .build();

                OneTimeWorkRequest cryptoWork = new OneTimeWorkRequest.Builder(CryptoWorker.class)
                        .setInputData(inputData)
                        .build();

                WorkManager.getInstance(requireContext())
                        .enqueue(cryptoWork);

                WorkManager.getInstance(requireContext())
                        .getWorkInfoByIdLiveData(cryptoWork.getId())
                        .observe(getViewLifecycleOwner(), workInfo -> {
                            if (workInfo != null && workInfo.getState().isFinished()) {
                                String result = workInfo.getOutputData().getString(CryptoWorker.KEY_OUTPUT);
                                tvResult.setText("Результат: " + result);
                                Toast.makeText(requireContext(), "Дешифровано: " + result, Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        return view;
    }

    private byte[] generateKey() {
        try {
            javax.crypto.KeyGenerator kg = javax.crypto.KeyGenerator.getInstance("AES");
            kg.init(256);
            return kg.generateKey().getEncoded();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] encryptMsg(String message, byte[] key) {
        try {
            javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("AES");
            javax.crypto.spec.SecretKeySpec keySpec = new javax.crypto.spec.SecretKeySpec(key, "AES");
            cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, keySpec);
            return cipher.doFinal(message.getBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
