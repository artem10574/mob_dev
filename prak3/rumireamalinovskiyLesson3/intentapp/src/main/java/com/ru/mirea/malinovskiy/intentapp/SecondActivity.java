package com.ru.mirea.malinovskiy.intentapp;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView textViewResult = findViewById(R.id.textView_result);

        String currentTime = getIntent().getStringExtra("current_time");

        int studentNumber = 18;
        int square = studentNumber * studentNumber;
        String resultText = "квадрат значения моего номера по списку в группе = " + square +
                ", а текущее время " + currentTime;

        textViewResult.setText(resultText);
    }
}