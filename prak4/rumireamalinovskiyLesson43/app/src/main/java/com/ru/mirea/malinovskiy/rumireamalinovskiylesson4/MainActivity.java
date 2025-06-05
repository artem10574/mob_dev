package com.ru.mirea.malinovskiy.rumireamalinovskiylesson4;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.ru.mirea.malinovskiy.rumireamalinovskiylesson4.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnPlay.setOnClickListener(v -> {
            Log.d("MainActivity", "Кнопка 'Воспроизвести' нажата");
            binding.tvTitle.setText("Now Playing...");
        });

        binding.btnStop.setOnClickListener(v -> {
            Log.d("MainActivity", "Кнопка 'Остановить' нажата");
            binding.tvTitle.setText("Пауза");
        });
    }
}