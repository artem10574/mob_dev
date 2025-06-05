package com.ru.mirea.malinovskiy.data_thread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final Runnable runn1 = () -> binding.tvInfo.setText("runn1");
        final Runnable runn2 = () -> binding.tvInfo.setText("runn2");
        final Runnable runn3 = () -> binding.tvInfo.setText("runn3");

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                runOnUiThread(runn1);

                TimeUnit.SECONDS.sleep(1);

                binding.tvInfo.postDelayed(runn3, 2000);
                binding.tvInfo.post(runn2);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}