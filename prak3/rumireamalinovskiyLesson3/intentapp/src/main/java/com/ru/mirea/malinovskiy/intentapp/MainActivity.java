package com.ru.mirea.malinovskiy.intentapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Обработчик нажатия на кнопку
    public void onClickGoToSecondActivity(View view) {
        // Получаем текущее время
        long dateInMillis = System.currentTimeMillis();
        String format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String dateString = sdf.format(new Date(dateInMillis));

        // Создаем намерение для перехода ко второй активности
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("current_time", dateString); // Передаем время
        startActivity(intent); // Запускаем вторую активность
    }
}