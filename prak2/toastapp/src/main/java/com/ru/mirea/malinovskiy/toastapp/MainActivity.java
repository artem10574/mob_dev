package com.ru.mirea.malinovskiy.toastapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editText; // Поле ввода

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализируем поле ввода
        editText = findViewById(R.id.editText);
    }

    // Обработчик нажатия на кнопку
    public void showToast(View view) {
        // Получаем текст из поля ввода
        String inputText = editText.getText().toString();

        // Подсчитываем количество символов
        int charCount = inputText.length();

        // Формируем сообщение
        String message = "СТУДЕНТ № 18 ГРУППА БИСО-01-20 Количество символов - " + charCount;

        // Создаем и показываем Toast
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }
}