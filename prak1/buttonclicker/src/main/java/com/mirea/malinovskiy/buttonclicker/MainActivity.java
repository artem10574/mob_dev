package com.mirea.malinovskiy.buttonclicker;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Глобальные переменные
    private TextView tvOut;
    private Button btnWhoAmI, btnItIsNotMe;
    private CheckBox checkBox; // Добавляем CheckBox

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация объектов
        tvOut = findViewById(R.id.tvOut);
        btnWhoAmI = findViewById(R.id.btnWhoAmI);
        btnItIsNotMe = findViewById(R.id.btnItIsNotMe);
        checkBox = findViewById(R.id.checkBox); // Инициализация CheckBox

        // Обработчик для кнопки "WhoAmI"
        btnWhoAmI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvOut.setText("Мой номер по списку № 18 (по журналу)");
                checkBox.setChecked(true); // Включаем CheckBox
            }
        });

        // Обработчик для кнопки "ItIsNotMe"
        btnItIsNotMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvOut.setText("Это не я сделал");
                checkBox.setChecked(false); // Выключаем CheckBox
            }
        });
    }
}